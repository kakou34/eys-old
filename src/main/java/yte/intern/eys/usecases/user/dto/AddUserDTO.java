package yte.intern.eys.usecases.user.dto;

import lombok.Getter;
import lombok.Setter;
import yte.intern.eys.validation.TcKimlikNo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AddUserDTO {

	@NotBlank
	@Size(min = 5, message = "Username must be at least 5 characters long")
	private String username;
	@NotBlank
	@Size(min = 8, message = "Password must be at leasr 8 characters long")
	private String password;
	@NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
	@TcKimlikNo
	private String turkishID;
	@Email
	@NotBlank
	private String email;

	private List<String> authorities;

}
