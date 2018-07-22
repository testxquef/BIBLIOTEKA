package com.danzal.lib_project.controllers.authors;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.controllers.ControllerExceptionHandler;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Author.AuthorService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorsControllerTest {

    @Mock
    AuthorService authorService;

    @Mock
    Model model;

    MockMvc mockMvc;

    AuthorsController authorsController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        authorsController = new AuthorsController(authorService);

        mockMvc = MockMvcBuilders.standaloneSetup(authorsController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();


    }

    @Test
    public void testMockMVC() throws Exception {

        mockMvc.perform(get("/authors/home/")).andExpect(status().isOk()).andExpect(view().name("authors/home"));


    }

    @Test
    public void showAuthors() {

        Set<Author> authors = new HashSet<>();
        authors.add(new Author());

        Author author = new Author();
        author.setFirstName("Some first name");

        authors.add(author);

        when(authorService.getAuthors()).thenReturn(authors);

        ArgumentCaptor<Set<Author>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = authorsController.showAuthors(model);

        assertEquals("authors/home", viewName);
        verify(authorService, times(1)).getAuthors();
        verify(model, times(1)).addAttribute(eq("authors"), argumentCaptor.capture());
        Set<Author> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testGeAuthor() throws Exception {

        Author author = new Author();
        author.setId(1L);

        when(authorService.findById(anyLong())).thenReturn(author);

        mockMvc.perform(get("/authors/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("authors/show"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    public void testGetAuthorNotFound() throws Exception {

        when(authorService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/authors/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    public void testGetAuthorsFailException() throws Exception {

        mockMvc.perform(get("/authors/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewAuthorsForm() throws Exception {
        AuthorCommand command = new AuthorCommand();

        mockMvc.perform(get("/authors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("authors/authorform"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    public void testPostNewAuthorForm() throws Exception {
        AuthorCommand command = new AuthorCommand();
        command.setId(2L);

        when(authorService.saveAuthorCommand(any())).thenReturn(command);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "some string")
                .param("lastName", "some last name")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors/home"));
    }

    @Test
    public void testPostNewAuthorFormValidationFail() throws Exception {
        AuthorCommand command = new AuthorCommand();
        command.setId(2L);

        when(authorService.saveAuthorCommand(any())).thenReturn(command);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("nationality", "123123213")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("authors/authorform"));
    }


    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/authors/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors/home"));

        verify(authorService, times(1)).deleteById(anyLong());
    }

}