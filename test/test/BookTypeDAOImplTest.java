package test;

import model.dao.BookTypeDAO;
import model.dao.impl.BookTypeDAOImpl;
import model.domain.BookType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookTypeDAOImplTest
{
    BookTypeDAO bookTypeDAO = new BookTypeDAOImpl();
    @Test
    public void insertBookType()
            throws Exception
    {
        BookType bookType = new BookType();
        bookType.setBookType("K");
        bookType.setTypeName("文史");
        bookTypeDAO.insertBookType(bookType);
    }

    @Test
    public void deleteBookType()
            throws Exception
    {
        bookTypeDAO.deleteBookType("K");
    }

    @Test
    public void updateBookType()
            throws Exception
    {
        BookType bookType = new BookType();
        bookType.setBookType("K");
        bookType.setTypeName("理综");
        bookTypeDAO.updateBookType(bookType);
    }

    @Test
    public void getBookTypeList()
            throws Exception
    {
        List<BookType> list = bookTypeDAO.getBookTypeList();
        System.out.println(list);
    }

}