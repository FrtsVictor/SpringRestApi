package com.br.restApi.service.auth;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.restApi.model.Users;
import com.br.restApi.repository.UserRepository;


@Service
@Component
public class CustomUserDetailsService implements UserDetailsService{

	//pego o usuario do UserRepository
	@Autowired //se cria e gerencia com metodos do  Userrepository
	UserRepository userRepository;
	


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = Optional.ofNullable(userRepository.findByUsername(username))
		.orElseThrow(()-> new UsernameNotFoundException("UserNotFound"));
		//listta de permissoes
		List<GrantedAuthority>authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority>authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		return new org.springframework.security.core.userdetails.User
				(users.getUsername(), users.getPassword(), users.isAdmin() ? authorityListAdmin : authorityListUser );
	}
	//permite ver se é nulo
}
