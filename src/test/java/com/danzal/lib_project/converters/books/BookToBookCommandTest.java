package com.danzal.lib_project.converters.books;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.converters.Book.BookToBookCommand;
import com.danzal.lib_project.domain.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class BookToBookCommandTest {

    public static Long ID = new Long(1L);
    public static String TITLE = "Test Title";
    public static Format FORMAT = Format.HARDCOVER;
    public static Language LANGUAGE = Language.ENGLISH;
    public static String PUBLISHER = "Test publisher";
    public static String DESCRIPTION = "Test description";
    public static Long LIBRARIAN_ID1 = new Long(1L);
    public static Category CATEGORY = Category.BIOGRAPHIES;

    BookToBookCommand bookToBookCommand;


    @Before
    public void setUp() {
        bookToBookCommand = new BookToBookCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(bookToBookCommand.convert(null));
    }

    @Test
    public void testEmptyObjeect() throws Exception {
        assertNotNull(bookToBookCommand.convert(new Book()));
    }

    @Test
    public void testConvert() throws Exception {

        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setFormat(FORMAT);
        book.setLanguage(LANGUAGE);
        book.setPublisher(PUBLISHER);
        book.setDescription(DESCRIPTION);
        book.setCategory(CATEGORY);


        Librarian librarian = new Librarian();
        librarian.setId(LIBRARIAN_ID1);
        book.setLibrarian(librarian);

        BookCommand bookCommand = bookToBookCommand.convert(book);

        assertNotNull(bookCommand);
        assertEquals(ID, bookCommand.getId());
        assertEquals(TITLE, bookCommand.getTitle());
        assertEquals(FORMAT, bookCommand.getFormat());
        assertEquals(LANGUAGE, bookCommand.getLanguage());
        assertEquals(PUBLISHER, bookCommand.getPublisher());
        assertEquals(DESCRIPTION, bookCommand.getDescription());
        assertEquals(CATEGORY, bookCommand.getCategory());
        assertEquals(LIBRARIAN_ID1, bookCommand.getLibrarian().getId());
    }
}
