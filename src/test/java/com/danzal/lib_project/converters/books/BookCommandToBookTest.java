package com.danzal.lib_project.converters.books;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.converters.Book.BookCommandToBook;
import com.danzal.lib_project.domain.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class BookCommandToBookTest {

    public static Long ID = new Long(1L);
    public static String TITLE = "Test Title";
    public static Format FORMAT = Format.HARDCOVER;
    public static Language LANGUAGE = Language.ENGLISH;
    public static String PUBLISHER = "Test publisher";
    public static String DESCRIPTION = "Test description";
    public static Long LIBRARIAN_ID1 = new Long(1L);
    public static Category CATEGORY = Category.BIOGRAPHIES;

    BookCommandToBook bookCommandToBook;


    @Before
    public void setUp() {
        bookCommandToBook = new BookCommandToBook();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(bookCommandToBook.convert(null));
    }

    @Test
    public void testEmptyObjeect() throws Exception {
        assertNotNull(bookCommandToBook.convert(new BookCommand()));
    }

    @Test
    public void testConvert() throws Exception {

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(ID);
        bookCommand.setTitle(TITLE);
        bookCommand.setFormat(FORMAT);
        bookCommand.setLanguage(LANGUAGE);
        bookCommand.setPublisher(PUBLISHER);
        bookCommand.setDescription(DESCRIPTION);
        bookCommand.setCategory(CATEGORY);

        Librarian librarian = new Librarian();
        librarian.setId(LIBRARIAN_ID1);
        bookCommand.setLibrarian(librarian);

        Book book = bookCommandToBook.convert(bookCommand);

        assertNotNull(book);
        assertEquals(ID, book.getId());
        assertEquals(TITLE, book.getTitle());
        assertEquals(FORMAT, book.getFormat());
        assertEquals(LANGUAGE, book.getLanguage());
        assertEquals(PUBLISHER, book.getPublisher());
        assertEquals(DESCRIPTION, book.getDescription());
        assertEquals(CATEGORY, book.getCategory());
        assertEquals(LIBRARIAN_ID1, book.getLibrarian().getId());
    }
}
