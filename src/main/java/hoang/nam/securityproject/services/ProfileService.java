/**
 * @ (#) ProfileService.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.services;

import hoang.nam.securityproject.dtos.requests.RegistrationRequest;
import hoang.nam.securityproject.dtos.responses.ProfileResponse;

import java.util.List;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */
public interface ProfileService {
    public ProfileResponse register(RegistrationRequest request);

    public List<ProfileResponse> getAllProfiles();

    public ProfileResponse getMyProfile();
}
