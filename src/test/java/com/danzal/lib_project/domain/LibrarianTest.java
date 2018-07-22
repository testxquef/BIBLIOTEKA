package com.danzal.lib_project.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LibrarianTest {

    public static Long ID = new Long(1L);
    public static String FIRST_NAME = "First Name Test";
    public static String LAST_NAME = "Last Name Test";
    public static int LIB_NUMBER = 100001;
    public static String ADRESS = "Some test adress 00-000 xxx ";
    public static Long LOAN_ID1 = new Long(1L);
    public static Long LOAN_ID2 = new Long(2L);
    public static Long LOAN_ID3 = new Long(3L);

    Librarian librarian;

    @Before
    public void setUp() {
        librarian = new Librarian();
    }

    @Test
    public void testLibrarianInfo() throws Exception {
        librarian.setId(ID);
        librarian.setFirstName(FIRST_NAME);
        librarian.setLastName(LAST_NAME);
        librarian.setLibrarianNumber(LIB_NUMBER);
        librarian.setAdress(ADRESS);

        assertNotNull(librarian);
        assertEquals(librarian.getId(), ID);
        assertEquals(librarian.getFirstName(), FIRST_NAME);
        assertEquals(librarian.getLastName(), LAST_NAME);
        assertEquals(librarian.getLibrarianNumber(), LIB_NUMBER);
        assertEquals(librarian.getAdress(), ADRESS);
    }

    @Test
    public void testGetLoanBooks() throws Exception {
        Book book = new Book();
        book.setId(LOAN_ID1);
        Book book1 = new Book();
        book1.setId(LOAN_ID2);

        librarian.getLoanBooks().add(book);
        librarian.getLoanBooks().add(book1);

        assertEquals(librarian.getLoanBooks().size(), 2);

        Set<Book> books = new HashSet<>();
        Book book2 = new Book();
        book2.setId(LOAN_ID3);

        books.add(book);
        books.add(book1);
        books.add(book2);

        librarian.setLoanBooks(books);

        assertEquals(librarian.getLoanBooks().iterator().next().getId(), books.iterator().next().getId());
        assertEquals(librarian.getLoanBooks().size(), 3);
    }

    @Test
    public void testGetLoanBooksId() throws Exception {
        librarian.getLoanBookId().add(LOAN_ID1);
        librarian.getLoanBookId().add(LOAN_ID2);

        assertEquals(2, librarian.getLoanBookId().size());

        Set<Long> loanBooksId = new HashSet<>();
        loanBooksId.add(LOAN_ID1);
        loanBooksId.add(LOAN_ID2);
        loanBooksId.add(LOAN_ID3);

        librarian.setLoanBookId(loanBooksId);

        assertEquals(3, librarian.getLoanBookId().size());
        assertEquals(librarian.getLoanBookId().iterator().next().toString(), loanBooksId.iterator().next().toString());

    }
}
