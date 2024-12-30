/**
 * @ (#) ProfileMapper.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.mappers;

import hoang.nam.securityproject.dtos.requests.RegistrationRequest;
import hoang.nam.securityproject.dtos.responses.ProfileResponse;
import hoang.nam.securityproject.models.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);

    @Mapping(source = "userId", target = "userId")
        // Thêm mapping cho userId
    ProfileResponse toProfileResponse(Profile profile);
}
