package com.danzal.lib_project.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookTest {

    public static Long ID = new Long(1L);
    public static String TITLE = "Test Title";
    public static Format FORMAT = Format.HARDCOVER;
    public static Language LANGUAGE = Language.ENGLISH;
    public static String PUBLISHER = "Test publisher";
    public static String DESCRIPTION = "Test description";
    public static Long LIBRARIAN_ID = new Long(1L);
    public static Category CATEGORY = Category.BIOGRAPHIES;

    public static Long AUTH_ID1 = new Long(1L);
    public static Long AUTH_ID2 = new Long(2L);
    public static Long AUTH_ID3 = new Long(3L);

    Book book;

    @Before
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testBookInfo() throws Exception {
        book.setId(ID);
        book.setTitle(TITLE);
        book.setFormat(FORMAT);
        book.setLanguage(LANGUAGE);
        book.setPublisher(PUBLISHER);
        book.setDescription(DESCRIPTION);
        Librarian librarian = new Librarian();
        librarian.setId(LIBRARIAN_ID);
        book.setLibrarian(librarian);
        book.setCategory(CATEGORY);

        assertNotNull(book);
        assertEquals(book.getId(), ID);
        assertEquals(book.getTitle(), TITLE);
        assertEquals(book.getFormat(), FORMAT);
        assertEquals(book.getLanguage(), LANGUAGE);
        assertEquals(book.getPublisher(), PUBLISHER);
        assertEquals(book.getDescription(), DESCRIPTION);
        assertEquals(book.getLibrarian().getId(), LIBRARIAN_ID);
        assertEquals(book.getCategory(), CATEGORY);
    }

    @Test
    public void testGetAuthors() throws Exception {
        Author author = new Author();
        author.setId(AUTH_ID1);
        Author author1 = new Author();
        author1.setId(AUTH_ID2);

        book.getAuthors().add(author);
        book.getAuthors().add(author1);

        assertEquals(book.getAuthors().size(), 2);

        Set<Author> authors = new HashSet<>();
        Author author2 = new Author();
        author2.setId(AUTH_ID3);
        authors.add(author);
        authors.add(author1);
        authors.add(author2);

        book.setAuthors(authors);
        assertEquals(book.getAuthors().size(), 3);
        assertEquals(book.getAuthors().iterator().next().getId(), authors.iterator().next().getId());
    }

    @Test
    public void testGetAuthorsId() throws Exception {
        book.getAuthorId().add(AUTH_ID1);
        book.getAuthorId().add(AUTH_ID2);

        assertEquals(book.getAuthorId().size(), 2);

        Set<Long> authorsId = new HashSet<>();
        authorsId.add(AUTH_ID1);
        authorsId.add(AUTH_ID2);
        authorsId.add(AUTH_ID3);

        book.setAuthorId(authorsId);

        assertEquals(book.getAuthorId().iterator().next().toString(), authorsId.iterator().next().toString());
        assertEquals(book.getAuthorId().size(), 3);

    }

}
