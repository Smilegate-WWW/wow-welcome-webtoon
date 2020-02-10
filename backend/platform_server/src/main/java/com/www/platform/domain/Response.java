package com.www.platform.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
	
	private int code;
	private String msg;
	private T data;
}
