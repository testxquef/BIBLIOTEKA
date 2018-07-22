package com.danzal.lib_project.services.Book;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.converters.Book.BookCommandToBook;
import com.danzal.lib_project.converters.Book.BookToBookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.repositories.AuthorRepository;
import com.danzal.lib_project.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookCommandToBook bookCommandToBook;
    private final BookToBookCommand bookToBookCommand;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookCommandToBook bookCommandToBook, BookToBookCommand bookToBookCommand) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookCommandToBook = bookCommandToBook;
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public Set<Book> getBooks() {

        log.debug("I'm in the service");
        Set<Book> books = new HashSet<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }


    @Override
    public Set<Author> getAuthors() {
        Set<Author> bookAuthors = new HashSet<>();

        authorRepository.findAll().iterator().forEachRemaining(bookAuthors::add);

        return bookAuthors;
    }

    @Override
    public Book findById(Long l) {
        Optional<Book> bookOptional = bookRepository.findById(l);

        if (!bookOptional.isPresent()) {
            throw new NotFoundException("Book Not Found! For ID value: " + l.toString());
        }

        return bookOptional.get();
    }

    @Override
    @Transactional
    public BookCommand findCommandById(Long l) {
        return bookToBookCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public BookCommand saveBookCommand(BookCommand command) {
        Book detachedBook = bookCommandToBook.convert(command);

        Book savedBook = bookRepository.save(detachedBook);

        for (Long authorId : savedBook.getAuthorId()
                ) {
            authorRepository.findById(authorId).get().getBooks().add(bookRepository.findById(savedBook.getId()).get());
            // savedBook.getAuthors().add(authorRepository.findById(authorId).get());
        }

        log.debug("Saved BookID: " + savedBook.getId());

        return bookToBookCommand.convert(savedBook);
    }

    @Override
    public void deleteById(Long idToDelete) {

        bookRepository.deleteById(idToDelete);

    }
}
