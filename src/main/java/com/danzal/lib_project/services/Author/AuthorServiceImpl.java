package com.danzal.lib_project.services.Author;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.converters.Author.AuthorCommandToAuthor;
import com.danzal.lib_project.converters.Author.AuthorToAuthorCommand;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorCommandToAuthor authorCommandToAuthor;
    private final AuthorToAuthorCommand authorToAuthorCommand;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorCommandToAuthor authorCommandToAuthor, AuthorToAuthorCommand authorToAuthorCommand, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.authorCommandToAuthor = authorCommandToAuthor;
        this.authorToAuthorCommand = authorToAuthorCommand;
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<Author> getAuthors() {
        Set<Author> authors = new HashSet<>();

        authorRepository.findAll().iterator().forEachRemaining(authors::add);
        return authors;
    }

    @Override
    public Set<Book> getBooks() {
        Set<Book> books = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    @Override
    public Author findById(Long l) {
        Optional<Author> authorOptional = authorRepository.findById(l);

        if (!authorOptional.isPresent()) {
            throw new NotFoundException("Author Not Found!");
        }

        return authorOptional.get();
    }


    @Override
    @Transactional
    public AuthorCommand findCommandById(Long l) {
        return authorToAuthorCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public AuthorCommand saveAuthorCommand(AuthorCommand command) {
        Author detachedAuthor = authorCommandToAuthor.convert(command);

        Author savedAuthor = authorRepository.save(detachedAuthor);
        for (Long bookId : savedAuthor.getBookId()
                ) {
            savedAuthor.getBooks().add(bookRepository.findById(bookId).get());
        }

        log.debug("Saved AuthorID: " + savedAuthor.getId());

        return authorToAuthorCommand.convert(savedAuthor);
    }

    @Override
    public void deleteById(Long idToDelete) {
        authorRepository.deleteById(idToDelete);
    }
}
