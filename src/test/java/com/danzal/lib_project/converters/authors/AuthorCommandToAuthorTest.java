package com.danzal.lib_project.converters.authors;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.converters.Author.AuthorCommandToAuthor;
import com.danzal.lib_project.converters.Book.BookCommandToBook;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Nationality;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class AuthorCommandToAuthorTest {

    public static final Long ID = new Long(1L);
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Last";
    public static final Nationality NATIONALITY = Nationality.ENGLISH;
    public static final Long BOOK_ID1 = new Long(1L);
    public static final Long BOOK_ID2 = new Long(2L);
    public static final Long BOOK_ID3 = new Long(3L);

    AuthorCommandToAuthor authorCommandToAuthor;

    @Before
    public void setUp() {
        authorCommandToAuthor = new AuthorCommandToAuthor(new BookCommandToBook());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(authorCommandToAuthor.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(authorCommandToAuthor.convert(new AuthorCommand()));
    }

    @Test
    public void testConvert() throws Exception {
        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(ID);
        authorCommand.setFirstName(FIRST_NAME);
        authorCommand.setLastName(LAST_NAME);
        authorCommand.setNationality(NATIONALITY);

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(BOOK_ID1);
        BookCommand bookCommand1 = new BookCommand();
        bookCommand1.setId(BOOK_ID2);
        BookCommand bookCommand2 = new BookCommand();
        bookCommand2.setId(BOOK_ID3);

        authorCommand.getBooks().add(bookCommand);
        authorCommand.getBooks().add(bookCommand1);
        authorCommand.getBooks().add(bookCommand2);

        Author author = authorCommandToAuthor.convert(authorCommand);

        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(NATIONALITY, author.getNationality());
        assertEquals(3, author.getBooks().size());
    }

}
