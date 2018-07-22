package com.danzal.lib_project.services.Author;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;

import java.util.Set;

public interface AuthorService {

    Set<Author> getAuthors();

    Set<Book> getBooks();

    Author findById(Long l);

    AuthorCommand findCommandById(Long l);

    AuthorCommand saveAuthorCommand(AuthorCommand command);

    void deleteById(Long idToDelete);
}
