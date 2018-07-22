package com.danzal.lib_project.controllers.authors;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Author.AuthorService;
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
public class AuthorsController {

    private final AuthorService authorService;


    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping({"/authors/home", "/authors/home"})
    public String showAuthors(Model model) {

        model.addAttribute("authors", authorService.getAuthors());

        return "authors/home";
    }

    @GetMapping("/authors/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("author", authorService.findById(new Long(id)));

        model.addAttribute("books", authorService.getBooks());
        return "authors/show";
    }


    @GetMapping("/authors/new")
    public String newAuthor(Model model) {
        model.addAttribute("author", new AuthorCommand());
        model.addAttribute("books", authorService.getBooks());

        return "authors/authorform";
    }


    @GetMapping({"authors/{id}/update", "/authors/{id}/update"})
    public String updateAuthor(@PathVariable String id, Model model) {
        model.addAttribute("author", authorService.findCommandById(Long.valueOf(id)));
        model.addAttribute("books", authorService.getBooks());
        return "authors/authorform";

    }

    @PostMapping("/authors")
    public String saveOrUpdate(@Valid @ModelAttribute("authors") AuthorCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "authors/authorform";
        }
        AuthorCommand savedCommand = authorService.saveAuthorCommand(command);
        return "redirect:/authors/home";
    }

    @GetMapping("authors/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        authorService.deleteById(Long.valueOf(id));

        return "redirect:/authors/home";
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
