/**
 * @ (#) UserCreationParam.java 1.0 28-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.dtos.identites;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 28-Dec-24
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationParam {
    String username;
    boolean enabled;
    String email;
    boolean emailVerified;
    String firstName;
    String lastName;
    List<Credential> credentials;
}
