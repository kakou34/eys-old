package yte.intern.eys.usecases.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.intern.eys.usecases.user.entity.Authority;
import yte.intern.eys.usecases.user.entity.Users;
import yte.intern.eys.usecases.user.repository.AuthorityRepository;
import yte.intern.eys.usecases.user.repository.UserRepository;
import yte.intern.eys.usecases.user.dto.AddUserDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

	private final AuthorityRepository authorityRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public String addUser(AddUserDTO addUserDTO) {
		Optional<Authority> userAuthority = authorityRepository.findById(2L);
		ArrayList<Authority> result = new ArrayList<>();
		userAuthority.ifPresent(result::add);
		Set<Authority> authorities = new HashSet<Authority>(result);
		authorityRepository.saveAll(authorities);


		Users users = new Users(
				null,addUserDTO.getUsername(),passwordEncoder.encode(addUserDTO.getPassword()),
				addUserDTO.getFirstname(), addUserDTO.getLastname(), addUserDTO.getTurkishID(), addUserDTO.getEmail(),
				authorities,true,true,true,true);

		userRepository.save(users);

		return "User Added Successfully!";
	}

	public String addAdmin(AddUserDTO addUserDTO) {
		Optional<Authority> userAuthority = authorityRepository.findById(2L);
		Optional<Authority> adminAuthority = authorityRepository.findById(1L);
		ArrayList<Authority> result = new ArrayList<>();
		userAuthority.ifPresent(result::add);
		adminAuthority.ifPresent(result::add);
		Set<Authority> authorities = new HashSet<>(result);
		authorityRepository.saveAll(authorities);

		Users users = new Users(
				null,addUserDTO.getUsername(),passwordEncoder.encode(addUserDTO.getPassword()),
				addUserDTO.getFirstname(), addUserDTO.getLastname(), addUserDTO.getTurkishID(), addUserDTO.getEmail(),
				authorities,true,true,true,true);

		userRepository.save(users);

		return "Admin Added Successfully!";
	}
}
