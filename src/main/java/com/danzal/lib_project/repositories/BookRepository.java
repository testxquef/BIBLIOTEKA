package com.danzal.lib_project.repositories;

import com.danzal.lib_project.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
