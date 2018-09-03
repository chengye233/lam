package test;

import jdk.nashorn.internal.ir.WhileNode;
import model.dao.BooksDAO;
import model.dao.impl.BooksDAOImpl;
import model.domain.Book;
import model.domain.Books;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import utils.JDBCUtils;
import utils.page.CriteriaBook;
import utils.page.CriteriaBooks;
import utils.page.Page;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BooksDAOImplTest
{
    BooksDAO booksDAO = new BooksDAOImpl();
    @Test

    public void getBooks()
            throws Exception
    {

        Books books = booksDAO.getBooks(42);
        System.out.println(books.getBookName());
        System.out.println(books.getAuthor());
        System.out.println(books.getContent());
    }

    @Test
    public void getPage()
            throws Exception
    {
        CriteriaBooks criteriaBooks = new CriteriaBooks(1, "c", 2, 2);
        Page<Books> page = booksDAO.getPage(criteriaBooks);
        System.out.println(page.getPageMap().size());
        for (int i = 0; i < page.getPageMap().size(); i++)
        {
            for (Books books : page.getPageMap().get(i))
            {
                System.out.print(books.getBooksId() +"\t");
            }
            System.out.println();
        }

    }

    @Test
    public void getTotalItemNumber()
            throws Exception
    {
        CriteriaBooks criteriaBooks = new CriteriaBooks(5, "", 1, 3);
        Page<Books> booksPage = booksDAO.getPage(criteriaBooks);
        System.out.println(booksPage.getPageMap().size());
//        for (Books books : booksPage.getPageMap().get(0))
//        {
//            System.out.println(books.getBookName());
//        }
    }

    /**
     * 插入测试
     */
    @Test
    public void insertBooks()
    {
        Books books = new Books();
        books.setBookName("哈哈");
        books.setBookType("ssss");
        booksDAO.insertBooks(books);
    }

    /**
     * 删除测试
     */
    @Test
    public void deleteBooks()
    {
        booksDAO.deleteBooks(41);
    }

    /**
     * 更新测试
     */
    @Test
    public void updateBooks()
    {
        Books books = new Books();
        books.setBooksId(6);
        System.out.println(books.getBooksId());
        File file = new File("web/img/bookPictures/" +books.getBooksId() +".jpg");
        books.setBookPicture(file);
        System.out.println(books.getBookPicture());
        booksDAO.updateBooks(books);

    }

}