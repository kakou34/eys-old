package yte.intern.eys.usecases.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.eys.usecases.login.dto.LoginRequest;
import yte.intern.eys.usecases.login.dto.LoginResponse;
import yte.intern.eys.usecases.login.LoginService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
		return loginService.login(loginRequest);
	}
}
