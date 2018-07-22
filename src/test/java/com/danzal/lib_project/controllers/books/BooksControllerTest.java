package com.danzal.lib_project.controllers.books;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.controllers.ControllerExceptionHandler;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Book.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BooksControllerTest {

    @Mock
    BookService bookService;

    @Mock
    Model model;

    MockMvc mockMvc;

    BooksController booksController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        booksController = new BooksController(bookService);

        mockMvc = MockMvcBuilders.standaloneSetup(booksController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();


    }

    @Test
    public void testMockMVC() throws Exception {

        mockMvc.perform(get("/books/home/")).andExpect(status().isOk()).andExpect(view().name("books/home"));


    }

    @Test
    public void showBooks() {

        Set<Book> books = new HashSet<>();
        books.add(new Book());

        Book book = new Book();
        book.setTitle("Test tittle");

        books.add(book);

        when(bookService.getBooks()).thenReturn(books);

        ArgumentCaptor<Set<Book>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = booksController.showBooks(model);

        assertEquals("books/home", viewName);
        verify(bookService, times(1)).getBooks();
        verify(model, times(1)).addAttribute(eq("books"), argumentCaptor.capture());
        Set<Book> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testGetBook() throws Exception {

        Book book = new Book();
        book.setId(1L);

        when(bookService.findById(anyLong())).thenReturn(book);

        mockMvc.perform(get("/books/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/show"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    public void testGetBookNotFound() throws Exception {

        when(bookService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/books/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    public void testGetBooksNumberFormatException() throws Exception {

        mockMvc.perform(get("/books/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewBookForm() throws Exception {
        BookCommand command = new BookCommand();

        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/bookform"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    public void testPostNewBookForm() throws Exception {
        BookCommand command = new BookCommand();
        command.setId(2L);

        when(bookService.saveBookCommand(any())).thenReturn(command);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("title", "some string")
                .param("publisher", "some publisher")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/home"));
    }

    @Test
    public void testPostNewBookFormValidationFail() throws Exception {
        BookCommand command = new BookCommand();
        command.setId(2L);

        when(bookService.saveBookCommand(any())).thenReturn(command);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("pubYear", "werewrwre")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("books/bookform"));
    }


    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/books/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/home"));

        verify(bookService, times(1)).deleteById(anyLong());
    }


}
