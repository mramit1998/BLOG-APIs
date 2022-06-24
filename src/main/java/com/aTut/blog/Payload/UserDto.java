package com.aTut.blog.Payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

		private int id;
		
		@NotEmpty
		@Size(min =3, message = "Name should be of minimum 3 characters!!" )
		private String name;
		
		@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid!!")
		private String email; 
		
		@NotEmpty
		@Size(min = 8 , max = 15 , message = "Password Should be between 8 to 15 characters !!")
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^^&*_])[a-zA-Z0-9!@#$%^&*]{8,15}$" ,
		message = "- at least 8 characters\r\n"
				+ "- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number\r\n"
				+ "- Can contain special characters")
		private String password;
		
		@NotNull
		@Size(max = 100 , message ="Maximum length is 100 characters!!")
		private String about;
		
		
}
