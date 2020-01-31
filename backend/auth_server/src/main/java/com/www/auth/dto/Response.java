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
	
	private int code;
	private String msg;
	private T data;
	
	public void setData(T response) {
		this.data = response;
	}
	public void setCode(int code) {
		this.code =code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
