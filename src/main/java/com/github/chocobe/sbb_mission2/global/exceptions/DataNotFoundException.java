package com.github.chocobe.sbb_mission2.global.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.NOT_FOUND,
        reason = "데이터를 찾지 못하였습니다."
)
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }

}
