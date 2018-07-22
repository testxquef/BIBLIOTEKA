package com.danzal.lib_project.controllers.librarians;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.controllers.ControllerExceptionHandler;
import com.danzal.lib_project.domain.Librarian;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Librarian.LibrarianService;
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

public class LibrariansControllerTest {

    @Mock
    LibrarianService librarianService;

    @Mock
    Model model;

    MockMvc mockMvc;

    LibrariansController librariansController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        librariansController = new LibrariansController(librarianService);

        mockMvc = MockMvcBuilders.standaloneSetup(librariansController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();


    }

    @Test
    public void testMockMVC() throws Exception {

        mockMvc.perform(get("/librarians/home/")).andExpect(status().isOk()).andExpect(view().name("librarians/home"));


    }

    @Test
    public void showLibrarians() {

        Set<Librarian> librarians = new HashSet<>();
        librarians.add(new Librarian());

        Librarian librarian = new Librarian();
        librarian.setFirstName("Some first name");

        librarians.add(librarian);

        when(librarianService.getLibrarians()).thenReturn(librarians);

        ArgumentCaptor<Set<Librarian>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = librariansController.showLibrarians(model);

        assertEquals("librarians/home", viewName);
        verify(librarianService, times(1)).getLibrarians();
        verify(model, times(1)).addAttribute(eq("librarians"), argumentCaptor.capture());
        Set<Librarian> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testGeLibrarian() throws Exception {

        Librarian librarian = new Librarian();
        librarian.setId(1L);

        when(librarianService.findById(anyLong())).thenReturn(librarian);

        mockMvc.perform(get("/librarians/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("librarians/show"))
                .andExpect(model().attributeExists("librarian"));
    }

    @Test
    public void testGetLibrarianNotFound() throws Exception {

        when(librarianService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/librarians/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    public void testGetLibrarianFailException() throws Exception {

        mockMvc.perform(get("/librarians/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewLibrarianForm() throws Exception {
        LibrarianCommand command = new LibrarianCommand();

        mockMvc.perform(get("/librarians/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("librarians/librarianform"))
                .andExpect(model().attributeExists("librarian"));
    }

    @Test
    public void testPostNewLibrarianForm() throws Exception {
        LibrarianCommand command = new LibrarianCommand();
        command.setId(2L);

        when(librarianService.saveLibrrarianCommand(any())).thenReturn(command);

        mockMvc.perform(post("/librarians")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "some string")
                .param("lastName", "some last name")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/librarians/home"));
    }

    @Test
    public void testPostNewLibrarianFormValidationFail() throws Exception {
        LibrarianCommand command = new LibrarianCommand();
        command.setId(2L);

        when(librarianService.saveLibrrarianCommand(any())).thenReturn(command);

        mockMvc.perform(post("/librarians")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("librarianNumber", "qweqweqe")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("librarians"))
                .andExpect(view().name("librarians/librarianform"));
    }


    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/librarians/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/librarians/home"));

        verify(librarianService, times(1)).deleteById(anyLong());
    }
}
