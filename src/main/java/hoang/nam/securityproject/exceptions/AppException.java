/**
 * @ (#) AppException.java 1.0 28-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 28-Dec-24
 * @version: 1.0
 */
@Getter
@Setter
public class AppException extends RuntimeException {
    ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
