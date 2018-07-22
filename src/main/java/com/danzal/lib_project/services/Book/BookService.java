package com.danzal.lib_project.services.Book;


import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;

import java.util.Set;

public interface BookService {

    Set<Book> getBooks();

    Set<Author> getAuthors();

    Book findById(Long l);

    BookCommand findCommandById(Long l);

    BookCommand saveBookCommand(BookCommand command);

    void deleteById(Long idToDelete);

}
