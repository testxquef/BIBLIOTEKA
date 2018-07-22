package com.danzal.lib_project.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthorTest {

    public static final Long ID = new Long(1L);
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Last";
    public static final Nationality NATIONALITY = Nationality.ENGLISH;
    public static final Long BOOK_ID1 = new Long(1L);
    public static final Long BOOK_ID2 = new Long(2L);
    public static final Long BOOK_ID3 = new Long(3L);

    Author author;

    @Before
    public void setUp() {
        author = new Author();
    }

    @Test
    public void testAuthorInfo() throws Exception {
        author.setId(ID);
        author.setFirstName(FIRST_NAME);
        author.setLastName(LAST_NAME);
        author.setNationality(NATIONALITY);

        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(NATIONALITY, author.getNationality());
        assertEquals(FIRST_NAME + " " + LAST_NAME, author.getNames());
    }


    @Test
    public void testGetBooks() throws Exception {

        Book book = new Book();
        book.setId(BOOK_ID1);
        Book book1 = new Book();
        book.setId(BOOK_ID2);

        author.getBooks().add(book);
        author.getBooks().add(book1);

        assertEquals(author.getBooks().size(), 2);

        Set<Book> books = new HashSet<>();
        Book book2 = new Book();
        book2.setId(BOOK_ID3);
        books.add(book);
        books.add(book1);
        books.add(book2);

        author.setBooks(books);

        assertEquals(author.getBooks().size(), 3);
        assertEquals(books.iterator().next().getId(), author.getBooks().iterator().next().getId());

    }

    @Test
    public void testGetBooksId() throws Exception {
        author.getBookId().add(BOOK_ID1);
        author.getBookId().add(BOOK_ID2);

        assertEquals(author.getBookId().size(), 2);

        Set<Long> booksId = new HashSet<>();
        booksId.add(BOOK_ID1);
        booksId.add(BOOK_ID2);
        booksId.add(BOOK_ID3);

        author.setBookId(booksId);

        assertEquals(author.getBookId().size(), 3);
        assertEquals(author.getBookId().iterator().next().toString(), booksId.iterator().next().toString());

    }
}