package com.br.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.restApi.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String username);
	
}
