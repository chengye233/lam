package test;

import model.dao.BookDAO;
import model.dao.impl.BookDAOImpl;
import model.domain.Book;
import org.junit.Test;
import utils.page.CriteriaBook;
import utils.page.Page;

import java.util.List;

import static org.junit.Assert.*;

public class BookDAOImplTest
{
    BookDAO bookDAO = new BookDAOImpl();
    @Test
    public void insertBook()
            throws Exception
    {
        bookDAO.insertBook(5, "K" ,50);
    }

    @Test
    public void queryBook()
            throws Exception
    {
        Book book = bookDAO.getBook(5);
        System.out.println(book.getBookCode());
    }

    @Test
    public void getBook()
            throws Exception
    {
        Book book = bookDAO.getBook(21);
        System.out.println(book.getBookCode());
    }

    @Test
    public void deleteBook()
            throws Exception
    {
        //bookDAO.deleteBook(5, 10);
        bookDAO.deleteBook(21);
    }


    @Test
    public void updateBook()
            throws Exception
    {
    }

    @Test
    public void getPage()
            throws Exception
    {
        CriteriaBook criteriaBook = new CriteriaBook(4, "", 2, 3);
        Page<Book> page = bookDAO.getPage(criteriaBook);
        for (int i = 0; i < page.getPageMap().size(); i++)
        {
            for (Book book : page.getPageMap().get(i))
            {
                System.out.print(book.getBookCode() +"\t");
            }
            System.out.println();
        }
    }

}