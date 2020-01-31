package com.www.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ErrorResponse : when error occurs, response body contains this.
 * @author bjiso
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse<T> extends Response<T> {

	private String code;
	private String messgae;
}
