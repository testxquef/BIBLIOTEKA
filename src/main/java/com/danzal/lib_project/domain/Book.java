package com.danzal.lib_project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"authors"})
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String title;

    @Enumerated(value = EnumType.STRING)
    private Format format;

    @ElementCollection(targetClass = Long.class)
    private Set<Long> authorId = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Language language;

    private int pubYear;
    private String publisher;

    @Lob
    private String description;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    private Librarian librarian;

    @Enumerated(value = EnumType.STRING)
    private Category category;

}
