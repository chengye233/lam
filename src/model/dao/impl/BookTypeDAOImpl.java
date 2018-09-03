package model.dao.impl;

import model.dao.BookTypeDAO;
import model.domain.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookTypeDAOImpl extends BaseDAO<BookType> implements BookTypeDAO
{
    @Override
    public BookType getBookType(String bookType)
    {
        String sql = "SELECT BOOKTYPE, TYPENAME FROM BOOKTYPE " +
                "WHERE BOOKTYPE = ?";
        BookType bookType1 = query(sql, bookType);
        return bookType1;

    }

    @Override
    public void insertBookType(BookType bookType)
    {
        String sql = "INSERT INTO BOOKTYPE(BOOKTYPE, TYPENAME) VALUES(?, ?)";
        update(sql, bookType.getBookType(), bookType.getTypeName());

    }

    @Override
    public void deleteBookType(String bookType)
    {
        String sql = "DELETE FROM BOOKTYPE WHERE BOOKTYPE = ?";
        update(sql, bookType);
    }

    @Override
    public void updateBookType(BookType bookType)
    {
        String sql = "UPDATE BOOKTYPE SET TYPENAME = ? " +
                "WHERE BOOKTYPE = ?";
        update(sql, bookType.getTypeName(), bookType.getBookType());
    }

    @Override
    public List<BookType> getBookTypeList()
    {
        String sql = "SELECT BOOKTYPE, TYPENAME FROM BOOKTYPE";
        List<BookType> list = queryForList(sql);
        return list;
    }
}
