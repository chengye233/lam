package test;

import model.dao.DAO;
import model.dao.impl.BaseDAO;
import model.domain.Book;
import model.domain.Books;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseDAOTest
{
    DAO dao = new BaseDAO<Books>();
    @Test
    public void update()
            throws Exception
    {
        /**
         * 更新
         */
//        String sql = "UPDATE books SET price = ? WHERE booksId = ?";
//        dao.update(sql, 90, 3);
        String sql = "DELETE FROM books WHERE booksId = ?";
        dao.update(sql, 21);

    }

    @Test
    public void query()
            throws Exception
    {
        String sql = "SELECT booksId, bookName, bookType, author, content, " +
                "price, registerTime, registerPerson, totalAmount, leftAmount, " +
                " FROM books WHERE booksId = ?";
        Books books = (Books) dao.query(sql, 3);
        System.out.println(books.getPrice());
    }

    @Test
    public void queryForList()
            throws Exception
    {
    }

    @Test
    public void queryForValue()
            throws Exception
    {
        String sql = "SELECT MAX(booksId) FROM Books WHERE booksId < ?";
        java.math.BigDecimal bigDecimal = (java.math.BigDecimal)dao.queryForValue(sql, 50);
        int count = bigDecimal.intValue();

        System.out.println(count);
    }

}