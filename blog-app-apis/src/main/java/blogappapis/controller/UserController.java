package blogappapis.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogappapis.payload.ApiRes;
import blogappapis.payload.UserDto;
import blogappapis.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService uerService;

	//Post -- Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.uerService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	
	//Put -- Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
		UserDto updateUser = this.uerService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);
	}
	
	
	//Delete -- Delete User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiRes> deleteUser(@PathVariable("userId")Integer uid){
		this.uerService.deleteUser(uid);
		return new ResponseEntity<ApiRes>(new ApiRes("User Deleted Successfully" , true),HttpStatus.OK);
	}
	
	//Get -- Get User 
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.uerService.getAllUser());
	}
	
	//Get -- Get Single User 
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(this.uerService.getUserById(userId));
	}
	
}

