/**
 * @ (#) ProfileServiceImpl.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.services.impls;

import feign.FeignException;
import hoang.nam.securityproject.dtos.identites.Credential;
import hoang.nam.securityproject.dtos.identites.TokenExchangeParam;
import hoang.nam.securityproject.dtos.identites.UserCreationParam;
import hoang.nam.securityproject.dtos.requests.RegistrationRequest;
import hoang.nam.securityproject.dtos.responses.ProfileResponse;
import hoang.nam.securityproject.exceptions.AppException;
import hoang.nam.securityproject.exceptions.ErrorCode;
import hoang.nam.securityproject.exceptions.ErrorNormalizer;
import hoang.nam.securityproject.mappers.ProfileMapper;
import hoang.nam.securityproject.repositories.IdentityClient;
import hoang.nam.securityproject.repositories.ProfileRepository;
import hoang.nam.securityproject.services.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    IdentityClient identityClient;
    ErrorNormalizer errorNormalizer;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    @Override
    public ProfileResponse getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // sub trong accesstoken tuong ung voi user_id
        var profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return profileMapper.toProfileResponse(profile);
    }

    @Override
    public List<ProfileResponse> getAllProfiles() {
        var profiles = profileRepository.findAll();
        return profiles.stream().map(profileMapper::toProfileResponse).toList();
    }

    @Override
    public ProfileResponse register(RegistrationRequest request) {
        try {
//        Step 1: Create account into keycloak
            /**
             *  1.1: Exchange Client Token
             */
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());
            /**
             *  1.2: Get access-token client to create user in Keycloak
             */
            String accessToken = "Bearer " + token.getAccessToken();
            log.info("Access Token: {}", token.getAccessToken());
//        Step 2: create user in keycloak
            var creatingResponse = identityClient.createUser(accessToken, UserCreationParam.builder()
                    .username(request.getUsername())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .enabled(true)
                    .emailVerified(false)
                    .credentials(List.of(Credential.builder()
                            .type("password")
                            .value(request.getPassword())
                            .temporary(false)
                            .build()))
                    .build());
//        Step 3: get userId of keycloak account from header of ResponseEntity createUser returned
            String userId = extractUserId(creatingResponse);
            log.info("User ID: {}", userId);
//        Step 3: set useId for user to save database postgresql
            var profile = profileMapper.toProfile(request);
            profile.setUserId(userId);
            profile = profileRepository.save(profile);

            log.info("Profile: {}", profile);
            log.info("Profile Response: {}", profileMapper.toProfileResponse(profile));

            return profileMapper.toProfileResponse(profile);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }


    private String extractUserId(ResponseEntity<?> response) {
        log.info("Header: {}", response);
        String location = response.getHeaders().get("location").get(0);
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }
}
