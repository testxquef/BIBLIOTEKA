package com.danzal.lib_project.services;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.converters.Book.BookCommandToBook;
import com.danzal.lib_project.converters.Book.BookToBookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.repositories.AuthorRepository;
import com.danzal.lib_project.repositories.BookRepository;
import com.danzal.lib_project.services.Book.BookService;
import com.danzal.lib_project.services.Book.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookCommandToBook bookCommandToBook;

    @Mock
    BookToBookCommand bookToBookCommand;

    @Mock
    AuthorRepository authorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        bookService = new BookServiceImpl(bookRepository, authorRepository, bookCommandToBook, bookToBookCommand);
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        Book bookReturned = bookService.findById(1L);

        assertNotNull("Null recipe returned", bookReturned);
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void testGetBookByIdNotFound() throws Exception {
        Optional<Book> bookOptional = Optional.empty();

        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);
        Book bookReturned = bookService.findById(1L);
    }


    @Test
    public void testGetBookCommandById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);

        when(bookToBookCommand.convert(any())).thenReturn(bookCommand);

        BookCommand bookCommandById = bookService.findCommandById(1L);

        assertNotNull(bookCommandById);
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).findAll();

    }


    @Test
    public void testSaveBookCommand() throws Exception {
        Author author = new Author();
        author.setId(1L);

        Optional<Author> optionalAuthor = Optional.of(author);


        when(authorRepository.findById(anyLong())).thenReturn(optionalAuthor);

        Book book = new Book();
        book.setId(1L);
        book.getAuthorId().add(1L);

        when(bookRepository.save(any())).thenReturn(book);

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);

        when(bookToBookCommand.convert(any())).thenReturn(bookCommand);

        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepository.findById(any())).thenReturn(bookOptional);

        BookCommand bookCommandTest = bookService.saveBookCommand(bookCommand);

        assertNotNull(bookCommandTest);
        verify(bookCommandToBook, times(1)).convert(any());
        verify(bookCommandToBook, times(1)).convert(any());


    }

    @Test
    public void testGetBooks() {

        Book book = new Book();
        HashSet booksData = new HashSet();
        booksData.add(book);


        when(bookService.getBooks()).thenReturn(booksData);
        Set<Book> books = bookService.getBooks();

        assertEquals(books.size(), 1);
        verify(bookRepository, times(1)).findAll();
        verify(bookRepository, never()).findById(anyLong());

    }

    @Test
    public void testDeleteById() throws Exception {

        Long idToDelete = Long.valueOf(2L);

        bookService.deleteById(idToDelete);

        verify(bookRepository, times(1)).deleteById(anyLong());

    }
}
