package com.danzal.lib_project.converters.librarians;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.converters.Librarian.LibrarianToLibrarianCommand;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Librarian;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class LibrarianToLibrarianCommandTest {


    public static Long ID = new Long(1L);
    public static String FIRST_NAME = "First Name Test";
    public static String LAST_NAME = "Last Name Test";
    public static int LIB_NUMBER = 100001;
    public static String ADRESS = "Some test adress 00-000 xxx ";
    public static Long LOAN_ID1 = new Long(1L);
    public static Long LOAN_ID2 = new Long(2L);

    LibrarianToLibrarianCommand librarianToLibrarianCommand;

    @Before
    public void setUp() {
        librarianToLibrarianCommand = new LibrarianToLibrarianCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(librarianToLibrarianCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(librarianToLibrarianCommand.convert(new Librarian()));
    }

    @Test
    public void testConvert() throws Exception {
        Librarian librarian = new Librarian();
        librarian.setId(ID);
        librarian.setFirstName(FIRST_NAME);
        librarian.setLastName(LAST_NAME);
        librarian.setLibrarianNumber(LIB_NUMBER);
        librarian.setAdress(ADRESS);

        Book book = new Book();
        book.setId(LOAN_ID1);
        Book book1 = new Book();
        book1.setId(LOAN_ID2);

        librarian.getLoanBooks().add(book);
        librarian.getLoanBooks().add(book1);

        LibrarianCommand librarianCommand = librarianToLibrarianCommand.convert(librarian);


        assertNotNull(librarianCommand);
        assertEquals(ID, librarianCommand.getId());
        assertEquals(FIRST_NAME, librarianCommand.getFirstName());
        assertEquals(LAST_NAME, librarianCommand.getLastName());
        assertEquals(LIB_NUMBER, librarianCommand.getLibrarianNumber());
        assertEquals(ADRESS, librarianCommand.getAdress());
        assertEquals(2, librarianCommand.getLoanBooks().size());
    }
}
