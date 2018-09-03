package model.dao;

import model.domain.BookType;

import java.util.List;

public interface BookTypeDAO
{
    /**
     * 根据bookType获取BookType对象
     * @param bookType
     */
    BookType getBookType(String bookType);

    /**
     * 增加图书分类
     */
    void insertBookType(BookType bookType);

    /**
     * 删除分类
     * @param bookType
     */
    void deleteBookType(String bookType);

    /**
     * 修改图书类别(还要修改所有的图书)
     * @param bookType
     */
    void updateBookType(BookType bookType);

    /**
     * 获取所有的图书类别
     * @return
     */
    List<BookType> getBookTypeList();
}
