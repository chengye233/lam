package model.domain;

import java.util.Date;

/**
 * 某一本图书信息类
 */
public class Book
{
    private int bookId;  //主键 自增
    private int bookState = 0; //借书状态 1:借走; 0:没有借走
    private int useState = 1;  //使用状态 1:使用; 0:损坏(不使用)
    int booksId;  //外键 与 BOOKS表关联
    String bookCode;  //图书编号 可作为搜索条件

    /**
     *setters&getters
     */
    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public int getBookState()
    {
        return bookState;
    }

    public void setBookState(int bookState)
    {
        this.bookState = bookState;
    }

    public int getBooksId()
    {
        return booksId;
    }

    public void setBooksId(int booksId)
    {
        this.booksId = booksId;
    }

    public String getBookCode()
    {
        return bookCode;
    }

    public void setBookCode(String bookCode)
    {
        this.bookCode = bookCode;
    }

    public int getUseState()
    {
        return useState;
    }

    public void setUseState(int useState)
    {
        this.useState = useState;
    }
}
