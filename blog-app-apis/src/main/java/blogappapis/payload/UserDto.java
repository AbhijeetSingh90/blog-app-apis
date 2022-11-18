package blogappapis.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	private int id;
	
	@NotEmpty()
	@Size(min=4,message="Username must be Minimum of 4 character !!!")
	private String name;
	
	@Email(message="email address is not valid !!!")
	private String email;
	
	@NotEmpty
	@Size(min=4,max=10,message="Password must be Minimum of 4 character and max 10 !!!")
	private String password;
	
	@NotEmpty
	@Size(min=4,max=100,message="About must be Minimum of 4 character and max 100 !!!")
	private String about;

}
