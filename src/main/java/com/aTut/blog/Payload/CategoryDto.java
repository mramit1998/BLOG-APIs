package com.aTut.blog.Payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min =3, message = "Category Title should be of minimum 3 characters!!" )
	private String categoryTitle;
	
	@NotNull
	@Size(max = 100 , message ="Maximum length is 100 characters!!")
	private String categoryDescription;
}
