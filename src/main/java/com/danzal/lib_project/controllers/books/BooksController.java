package com.danzal.lib_project.controllers.books;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.exceptions.NotFoundException;
import com.danzal.lib_project.services.Book.BookService;
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
public class BooksController {

    private final BookService bookService;


    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/home")
    public String showBooks(Model model) {

        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("authors", bookService.getAuthors());

        return "books/home";
    }

    @GetMapping("/books/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("book", bookService.findById(new Long(id)));
        model.addAttribute("authors", bookService.getAuthors());

        return "books/show";
    }

    @GetMapping("/books/new")
    public String newBook(Model model) {

        model.addAttribute("book", new BookCommand());
        model.addAttribute("authors", bookService.getAuthors());

        return "books/bookform";
    }

    @PostMapping("/books")
    public String saveOrUpdate(@Valid @ModelAttribute("books") BookCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "books/bookform";
        }
        BookCommand savedBook = bookService.saveBookCommand(command);
        return "redirect:/books/home";
    }

    @GetMapping("/books/{id}/delete")
    public String deleteById(@PathVariable String id) {

        bookService.deleteById(Long.valueOf(id));

        return "redirect:/books/home";
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
