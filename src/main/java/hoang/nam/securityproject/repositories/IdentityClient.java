/**
 * @ (#) IdentityClient.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.repositories;

import feign.QueryMap;
import hoang.nam.securityproject.dtos.identites.TokenExchangeParam;
import hoang.nam.securityproject.dtos.identites.TokenExchangeResponse;
import hoang.nam.securityproject.dtos.identites.UserCreationParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */
@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/hoangnam/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(value = "/admin/realms/hoangnam/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(@RequestHeader("authorization") String token,
            @RequestBody UserCreationParam param);
}
