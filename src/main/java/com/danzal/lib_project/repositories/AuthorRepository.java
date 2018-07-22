package com.danzal.lib_project.repositories;

import com.danzal.lib_project.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByFirstName(String first_name);
}
