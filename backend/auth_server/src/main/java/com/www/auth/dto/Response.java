package com.www.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * response standardization
 * @author bjiso
 * @param <T>
 */

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
	
	private T response;
	
	public void setResponse(T response) {
		this.response = response;
	}

}
