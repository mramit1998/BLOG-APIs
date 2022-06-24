package com.aTut.blog.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aTut.blog.Payload.UserDto;
import com.aTut.blog.entities.User;
import com.aTut.blog.exceptions.ResourceNotFoundException;
import com.aTut.blog.repositories.UserRepository;
import com.aTut.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public UserDto createUser(UserDto userDto)  {
		User user  = this.dtotoUser(userDto);
		
		User SavedUser = this.userRepo.save(user);
		
		return this.usertoDto(SavedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", " userId", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User UpdatedUser= 	this.userRepo.save(user);
		return this.usertoDto(UpdatedUser);
	}

	@Override
	public UserDto getUser(Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", " userId", userId));
		
		return this.usertoDto(user);
	}


	@Override
	public List<UserDto> getAllUsers() {
		List<User> users =  this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user ->	this.usertoDto(user)).collect(Collectors.toList());
		return userDtos;
	}
	

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", " userId", userId));
		
		this.userRepo.delete(user);

	}
	
	private User dtotoUser(UserDto userDto)
	{
		User user =this.modelmapper.map(userDto, User.class);
		
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}

	private UserDto usertoDto(User user)
	{
		UserDto userDto = this.modelmapper.map(user, UserDto.class);

		
		return userDto;
	}
}
