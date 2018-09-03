package test;

import model.dao.BooksDAO;
import model.dao.impl.BooksDAOImpl;
import model.domain.Books;
import org.junit.Test;
import utils.JDBCUtils;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

public class BlobTestTest
{
    @Test
    public void getId()
            throws Exception
    {
        Books books = new Books();
        books.setBooksId(22);
        File file = new File("web/img/bookPictures/" +books.getBooksId() +".jpg");
        books.setBookPicture(file);
        BooksDAOImpl booksDAO = new BooksDAOImpl();
        booksDAO.updatePicture(books);

    }

    @Test
    public void setId()
            throws Exception
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE books SET BOOKPICTURE = ? WHERE BOOKSID = ?";
        connection = JDBCUtils.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        /**
         *
         */
        File file = new File("web/img/bookPictures/" +24 +".jpg");
        Books books = new Books();
        books.setBooksId(24);
        books.setBookPicture(file);
        /**
         *
         */
        InputStream in = new BufferedInputStream(new FileInputStream(books.getBookPicture()));
        Blob blob = connection.createBlob();
        OutputStream out = blob.setBinaryStream(1);
        int i = 0;
        byte[] buff = new byte[4096];
        while ((i = in.read(buff)) > 0)
        {
            out.write(buff);
        }
        out.close();
        in.close();
        preparedStatement.setBlob(1, blob);
        preparedStatement.setInt(2, books.getBooksId());
        preparedStatement.executeUpdate();
        JDBCUtils.release(null, preparedStatement, connection);
    }

    @Test
    public void getPic()
            throws Exception
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE blobTest SET pic = ? WHERE id = ?";
        connection = JDBCUtils.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        File file = new File("web/img/bookPictures/" +23 +".jpg");
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        Blob blob = connection.createBlob();
        OutputStream out = blob.setBinaryStream(1);
        int i = 0;
        byte[] buff = new byte[4096];
        while ((i = in.read(buff)) > 0)
        {
            out.write(buff);
        }
        out.close();
        in.close();
        preparedStatement.setBlob(1, blob);
        preparedStatement.setString(2, "1");
        preparedStatement.executeUpdate();
        JDBCUtils.release(null, preparedStatement, connection);
    }

    @Test
    public void setPic()
            throws Exception
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO blobTest(id) VALUES (?)";
        connection = JDBCUtils.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "3");
        preparedStatement.executeUpdate();
        JDBCUtils.release(null, preparedStatement, connection);
    }

}