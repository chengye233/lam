package model.domain;

import java.io.*;
import java.sql.Date;

/**
 * 某一相同的图书类
 */
public class Books
{
    private int booksId;  //主键 自增
    private String bookName;  //图书名
    private String bookType;  //图书类型
    private String author;  //作者
    private String content;  //简介
    private float price;  //价格 罚款参考
    private Date registerTime;  //登记时间
    private String registerPerson;  //登记人用户名
    private File bookPicture;  //图书图片
    private int totalAmount;  //总数量
    private int leftAmount;  //剩余数量

    /**
     * setters&getters
     */
    public int getBooksId()
    {
        return booksId;
    }

    public void setBooksId(int booksId)
    {
        this.booksId = booksId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getBookType()
    {
        return bookType;
    }

    public void setBookType(String bookType)
    {
        this.bookType = bookType;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public Date getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime)
    {
        this.registerTime = registerTime;
    }

    public String getRegisterPerson()
    {
        return registerPerson;
    }

    public void setRegisterPerson(String registerPerson)
    {
        this.registerPerson = registerPerson;
    }

    public File getBookPicture()
    {
        return bookPicture;
    }

    public void setBookPicture(File file)
    {
        bookPicture = file;
    }

    public int getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public int getLeftAmount()
    {
        return leftAmount;
    }

    public void setLeftAmount(int leftAmount)
    {
        this.leftAmount = leftAmount;
    }

    @Override
    public int hashCode()
    {
        return this.booksId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Books)
        {
            Books books = (Books) obj;
            return (this.booksId == ((Books) obj).booksId);
        }
        return super.equals(obj);
    }
}
