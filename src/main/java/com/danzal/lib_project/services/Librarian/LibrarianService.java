package com.danzal.lib_project.services.Librarian;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Librarian;

import java.util.Set;

public interface LibrarianService {

    Set<Librarian> getLibrarians();

    Set<Book> getBooks();

    Librarian findById(Long l);

    LibrarianCommand findCommandById(Long l);

    LibrarianCommand saveLibrrarianCommand(LibrarianCommand command);

    void deleteById(Long idToDelete);
}
