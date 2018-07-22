package com.danzal.lib_project.controllers.librarians;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Librarian.LibrarianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class LibrariansController {

    private final LibrarianService librarianService;

    public LibrariansController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping({"/librarians/home", "librarians/home"})
    public String showLibrarians(Model model) {
        model.addAttribute("librarians", librarianService.getLibrarians());
        model.addAttribute("books", librarianService.getBooks());

        return "librarians/home";
    }

    @GetMapping({"/librarians/{id}/show", "/librarians/{id}/show/"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("librarian", librarianService.findById(new Long(id)));
        model.addAttribute("books", librarianService.getBooks());

        return "librarians/show";
    }

    @GetMapping({"/librarians/new", "/librarians/new/"})
    public String newLibrarian(Model model) {
        model.addAttribute("librarian", new LibrarianCommand());
        model.addAttribute("books", librarianService.getBooks());

        return "librarians/librarianform";
    }

    @PostMapping("/librarians")
    public String saveOrUpdate(@Valid @ModelAttribute("librarians") LibrarianCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "librarians/librarianform";
        }

        LibrarianCommand librarianCommand = librarianService.saveLibrrarianCommand(command);

        return "redirect:/librarians/home";
    }

    @GetMapping({"/librarians/{id}/delete", "/librarians/{id}/detele/"})
    public String deleteById(@PathVariable String id) {
        librarianService.deleteById(Long.valueOf(id));

        return "redirect:/librarians/home";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
