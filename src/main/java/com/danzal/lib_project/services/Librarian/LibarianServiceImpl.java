package com.danzal.lib_project.services.Librarian;


import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.converters.Librarian.LibrarianCommandToLibrarian;
import com.danzal.lib_project.converters.Librarian.LibrarianToLibrarianCommand;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Librarian;
import com.danzal.lib_project.repositories.BookRepository;
import com.danzal.lib_project.repositories.LibrarianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LibarianServiceImpl implements LibrarianService {

    private final LibrarianRepository librarianRepository;
    private final BookRepository bookRepository;
    private final LibrarianCommandToLibrarian librarianCommandToLibrarian;
    private final LibrarianToLibrarianCommand librarianToLibrarianCommand;

    public LibarianServiceImpl(LibrarianRepository librarianRepository, BookRepository bookRepository, LibrarianCommandToLibrarian librarianCommandToLibrarian, LibrarianToLibrarianCommand librarianToLibrarianCommand) {
        this.librarianRepository = librarianRepository;
        this.bookRepository = bookRepository;
        this.librarianCommandToLibrarian = librarianCommandToLibrarian;
        this.librarianToLibrarianCommand = librarianToLibrarianCommand;
    }

    @Override
    public Set<Librarian> getLibrarians() {
        log.debug("Getting librarians");
        Set<Librarian> librarians = new HashSet<>();

        librarianRepository.findAll().iterator().forEachRemaining(librarians::add);
        return librarians;
    }

    @Override
    public Set<Book> getBooks() {

        Set<Book> books = new HashSet<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);
        return books;
    }

    @Override
    public Librarian findById(Long l) {
        Optional<Librarian> librarianOptional = librarianRepository.findById(l);

        if (!librarianOptional.isPresent()) {
            throw new RuntimeException("Librarian not found");
        }

        return librarianOptional.get();
    }

    @Override
    @Transactional
    public LibrarianCommand findCommandById(Long l) {
        return librarianToLibrarianCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public LibrarianCommand saveLibrrarianCommand(LibrarianCommand command) {
        Librarian detachedLibrarian = librarianCommandToLibrarian.convert(command);

        Librarian savedLibrarian = librarianRepository.save(detachedLibrarian);

        for (Long bookId : savedLibrarian.getLoanBookId()) {
            bookRepository.findById(bookId).get().setLibrarian(savedLibrarian);
        }

        return librarianToLibrarianCommand.convert(savedLibrarian);
    }

    @Override
    public void deleteById(Long idToDelete) {

        librarianRepository.deleteById(idToDelete);
    }
}
