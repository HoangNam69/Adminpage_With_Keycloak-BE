/**
 * @ (#) TokenExchangeResponse.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.dtos.identites;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyCloakErrorResponse {
    String errorMessage;
}
