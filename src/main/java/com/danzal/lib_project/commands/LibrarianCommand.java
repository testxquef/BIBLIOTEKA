package com.danzal.lib_project.commands;


import com.danzal.lib_project.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LibrarianCommand {


    private Long id;
    private String firstName;
    private String lastName;
    private int librarianNumber;
    private String adress;
    private Set<Long> loanBookId = new HashSet<>();
    private Set<Book> loanBooks = new HashSet<>();
}
