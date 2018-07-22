package com.danzal.lib_project.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"loanBooks"})
@Data
public class Librarian {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;
    private int librarianNumber;

    @Lob
    private String adress;

    @ElementCollection(targetClass = Long.class)
    private Set<Long> loanBookId = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "librarian")
    private Set<Book> loanBooks = new HashSet<>();


}
