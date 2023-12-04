package com.aTut.blog.Payload;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse implements Serializable {


	private static final long serialVersionUID = -8091879091924046844L;
	private  String jwttoken;

	
}
