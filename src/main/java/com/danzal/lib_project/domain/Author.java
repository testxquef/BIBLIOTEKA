package com.danzal.lib_project.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection(targetClass = Long.class)
    private Set<Long> bookId = new HashSet<>();
    private String firstName;
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Nationality nationality;

    @ManyToMany
    @JoinTable(name = "author_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();


    public String getNames() {
        return this.firstName + " " + this.lastName;
    }


}
