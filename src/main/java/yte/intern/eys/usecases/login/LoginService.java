package yte.intern.eys.usecases.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import yte.intern.eys.security.CustomUserDetails;
import yte.intern.eys.usecases.login.dto.LoginRequest;
import yte.intern.eys.usecases.login.dto.LoginResponse;
import yte.intern.eys.security.util.JwtUtil;
import yte.intern.eys.usecases.user.entity.Users;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

	@Value(value = "${security.jwt.secret-key}")
	private String secretKey;

	private final DaoAuthenticationProvider authenticationProvider;

	public LoginResponse login(final LoginRequest loginRequest) {

		Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

		try {
			Authentication user = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
			String token = JwtUtil.generateToken(user, secretKey, 15);

			CustomUserDetails userDetails = CustomUserDetails.build((Users) user.getPrincipal()) ;
			List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			return (new LoginResponse( token,
					userDetails.getFirstname(),
					userDetails.getLastname(),
					userDetails.getTurkishID(),
					userDetails.getEmail(),
					userDetails.getUsername(),
					roles));

		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		return null;
	}
}
