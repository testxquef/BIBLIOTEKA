package com.danzal.lib_project.converters.librarians;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.converters.Librarian.LibrarianCommandToLibrarian;
import com.danzal.lib_project.domain.Librarian;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class LibrarianCommandToLibrarianTest {

    public static Long ID = new Long(1L);
    public static String FIRST_NAME = "First Name Test";
    public static String LAST_NAME = "Last Name Test";
    public static int LIB_NUMBER = 100001;
    public static String ADRESS = "Some test adress 00-000 xxx ";
    public static Long LOAN_ID1 = new Long(1L);
    public static Long LOAN_ID2 = new Long(2L);

    LibrarianCommandToLibrarian librarianCommandToLibrarian;

    @Before
    public void setUp() {
        librarianCommandToLibrarian = new LibrarianCommandToLibrarian();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(librarianCommandToLibrarian.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(librarianCommandToLibrarian.convert(new LibrarianCommand()));
    }

    @Test
    public void testConvert() throws Exception {
        LibrarianCommand librarianCommand = new LibrarianCommand();
        librarianCommand.setId(ID);
        librarianCommand.setFirstName(FIRST_NAME);
        librarianCommand.setLastName(LAST_NAME);
        librarianCommand.setLibrarianNumber(LIB_NUMBER);
        librarianCommand.setAdress(ADRESS);

        librarianCommand.getLoanBookId().add(LOAN_ID1);
        librarianCommand.getLoanBookId().add(LOAN_ID2);

        Librarian librarian = librarianCommandToLibrarian.convert(librarianCommand);


        assertNotNull(librarian);
        assertEquals(ID, librarian.getId());
        assertEquals(FIRST_NAME, librarian.getFirstName());
        assertEquals(LAST_NAME, librarian.getLastName());
        assertEquals(LIB_NUMBER, librarian.getLibrarianNumber());
        assertEquals(ADRESS, librarian.getAdress());
        assertEquals(2, librarian.getLoanBookId().size());
    }
}
