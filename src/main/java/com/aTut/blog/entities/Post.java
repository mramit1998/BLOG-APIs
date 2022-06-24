package com.aTut.blog.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(length = 100 , nullable = false)
	private String title;
	
	@Column(length = 1000, nullable = false )
	private String content;
	
	private String imageName;
	
	@Column(nullable = false)
	private Date added_date;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category  category;
	
	@ManyToOne
	private User user;
}
