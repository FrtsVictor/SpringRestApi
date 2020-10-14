package com.br.restApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.br.restApi.model.Student;

@Repository
public interface StudendRepository extends PagingAndSortingRepository<Student, Long> {

	List<Student> findByNameIgnoreCaseContaining(String name);
	
	@Query(value = "FROM Student WHERE id = ?1")
	Student studentById(Long id);

}
