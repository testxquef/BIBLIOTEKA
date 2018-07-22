package com.danzal.lib_project.converters.authors;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.converters.Author.AuthorToAuthorCommand;
import com.danzal.lib_project.converters.Book.BookToBookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Nationality;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class AuthorToAuthorCommandTest {


    public static final Long ID = new Long(1L);
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Last";
    public static final Nationality NATIONALITY = Nationality.ENGLISH;
    public static final Long BOOK_ID1 = new Long(1L);
    public static final Long BOOK_ID2 = new Long(2L);
    public static final Long BOOK_ID3 = new Long(3L);

    AuthorToAuthorCommand converter;

    @Before
    public void setUp() {
        converter = new AuthorToAuthorCommand(new BookToBookCommand());
    }


    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Author()));
    }


    @Test
    public void convert() throws Exception {
        Author author = new Author();
        author.setId(ID);
        author.setFirstName(FIRST_NAME);
        author.setLastName(LAST_NAME);
        author.setNationality(NATIONALITY);

        Book book = new Book();
        book.setId(BOOK_ID1);

        Book book1 = new Book();
        book1.setId(BOOK_ID2);

        Book book2 = new Book();
        book2.setId(BOOK_ID3);

        author.getBooks().add(book);
        author.getBooks().add(book1);
        author.getBooks().add(book2);

        AuthorCommand authorCommand = converter.convert(author);

        assertNotNull(authorCommand);
        assertEquals(ID, authorCommand.getId());
        assertEquals(FIRST_NAME, authorCommand.getFirstName());
        assertEquals(LAST_NAME, authorCommand.getLastName());
        assertEquals(NATIONALITY, authorCommand.getNationality());
        assertEquals(3, authorCommand.getBooks().size());


    }
}
