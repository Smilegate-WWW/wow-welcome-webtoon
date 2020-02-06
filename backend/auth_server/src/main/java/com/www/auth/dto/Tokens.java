package com.www.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tokens {
	private String AccessToken;
	private String RefreshToken;
}
