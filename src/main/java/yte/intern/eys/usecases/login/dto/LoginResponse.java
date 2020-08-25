package yte.intern.eys.usecases.login.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LoginResponse {
	private final String token;
	private final String firstname;
	private final String surname;
	private final String turkishID;
	private final String email;
	private final String username;
	private final List<String> roles;

}
