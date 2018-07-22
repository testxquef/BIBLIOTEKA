package com.danzal.lib_project.commands;

import com.danzal.lib_project.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class BookCommand {

    private Long id;
    private String title;
    private Format format;
    private Set<Long> authorId = new HashSet<>();
    private Set<Author> authors = new HashSet<>();
    private Language language;
    private int pubYear;
    private String publisher;
    private String description;
    private Category category;
    private Librarian librarian;


}
