package com.danzal.lib_project.services;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.converters.Author.AuthorCommandToAuthor;
import com.danzal.lib_project.converters.Author.AuthorToAuthorCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.repositories.AuthorRepository;
import com.danzal.lib_project.repositories.BookRepository;
import com.danzal.lib_project.services.Author.AuthorService;
import com.danzal.lib_project.services.Author.AuthorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

    AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorCommandToAuthor authorCommandToAuthor;

    @Mock
    AuthorToAuthorCommand authorToAuthorCommand;

    @Mock
    BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        authorService = new AuthorServiceImpl(authorRepository, authorCommandToAuthor, authorToAuthorCommand, bookRepository);
    }


    @Test
    public void testGetAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

        Author authorReturned = authorService.findById(1L);

        assertNotNull("Null recipe returned", authorReturned);
        verify(authorRepository, times(1)).findById(anyLong());
        verify(authorRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void testGetAuthorByIdNotFound() throws Exception {
        Optional<Author> authorOptional = Optional.empty();

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);
        Author authorReturned = authorService.findById(1L);
    }


    @Test
    public void testGetAuthorCommandById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(1L);

        when(authorToAuthorCommand.convert(any())).thenReturn(authorCommand);

        AuthorCommand authorCommandById = authorService.findCommandById(1L);

        assertNotNull(authorCommandById);
        verify(authorRepository, times(1)).findById(anyLong());
        verify(authorRepository, never()).findAll();

    }
    @Test
    public void testSaveAuthorCommand() throws Exception {
        Book book = new Book();
        book.setId(1L);

        Optional<Book> optionalBook = Optional.of(book);


        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        Author author = new Author();
        author.setId(1L);
        author.getBookId().add(1L);

        when(authorRepository.save(any())).thenReturn(author);

        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(1L);

        when(authorToAuthorCommand.convert(any())).thenReturn(authorCommand);

        AuthorCommand authorCommandTest = authorService.saveAuthorCommand(authorCommand);

        assertNotNull(authorCommandTest);
        verify(authorCommandToAuthor, times(1)).convert(any());
        verify(authorToAuthorCommand, times(1)).convert(any());


    }

    @Test
    public void testGetAuthors() {

        Author author = new Author();
        HashSet authorsData = new HashSet();
        authorsData.add(author);


        when(authorService.getAuthors()).thenReturn(authorsData);
        Set<Author> authors = authorService.getAuthors();

        assertEquals(authors.size(), 1);
        verify(authorRepository, times(1)).findAll();
        verify(authorRepository, never()).findById(anyLong());

    }

    @Test
    public void testDeleteById() throws Exception {

        Long idToDelete = Long.valueOf(2L);

        authorService.deleteById(idToDelete);

        verify(authorRepository, times(1)).deleteById(anyLong());

    }
}