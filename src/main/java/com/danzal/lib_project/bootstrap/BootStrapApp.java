package com.danzal.lib_project.bootstrap;


import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Librarian;
import com.danzal.lib_project.repositories.AuthorRepository;
import com.danzal.lib_project.repositories.BookRepository;
import com.danzal.lib_project.repositories.LibrarianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapApp implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LibrarianRepository librarianRepository;

    public BootStrapApp(AuthorRepository authorRepository, BookRepository bookRepository, LibrarianRepository librarianRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.librarianRepository = librarianRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (authorRepository.count() == 0L) {
            log.debug("Loading Authors data...");
            loadAuthors();
        }

        if (bookRepository.count() == 0L) {
            log.debug("Loading Book data...");
            loadBooks();
        }

        if (librarianRepository.count() == 0L) {
            log.debug("Loading Librarian data...");
            loadLibrarian();
        }
    }

    private void loadAuthors() {
        Author author1 = new Author();
        author1.setFirstName("John");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setFirstName("Mark");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setFirstName("Dave");
        authorRepository.save(author3);

        Author author4 = new Author();
        author4.setFirstName("Natan");
        authorRepository.save(author4);


    }

    private void loadBooks() {
        Book book1 = new Book();
        book1.setTitle("Lord of the rings");
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("The Witcher");
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter");
        bookRepository.save(book3);
    }

    private void loadLibrarian() {
        Librarian librarian1 = new Librarian();
        librarian1.setFirstName("Ann");
        librarianRepository.save(librarian1);

        Librarian librarian2 = new Librarian();
        librarian2.setFirstName("Sara");
        librarianRepository.save(librarian2);

        Librarian librarian3 = new Librarian();
        librarian3.setFirstName("Alice");
        librarianRepository.save(librarian3);

    }
}
