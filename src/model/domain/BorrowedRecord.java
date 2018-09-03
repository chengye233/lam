package model.domain;

import java.sql.Date;

/**
 * 借书记录类
 */
public class BorrowedRecord
{
    private int recordId;  //主键
    private int userId;  //借书人的userId
    private String userName;  //借书人用户名
    private int bookId;  //所借书的bookId;
    private String bookName;  //书名
    private Date borrowedTime; //借书时间 如果续借更新此时间
    private Date backTime;  //还书时间 如果续借更新此时间
    private int backState = 0;  //是否还书 1 已还; 0 未还
    private int renewState = 0;  //是否续借 只能续借1次 1 续借; 0 不续借
    private float fine = 0;  //罚款

    /**
     *setters&getters
     */
    public int getRecordId()
    {
        return recordId;
    }

    public void setRecordId(int recordId)
    {
        this.recordId = recordId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public Date getBorrowedTime()
    {
        return borrowedTime;
    }

    public void setBorrowedTime(Date borrowedTime)
    {
        this.borrowedTime = borrowedTime;
    }

    public Date getBackTime()
    {
        return backTime;
    }

    public void setBackTime(Date backTime)
    {
        this.backTime = backTime;
    }

    public int getBackState()
    {
        return backState;
    }

    public void setBackState(int backState)
    {
        this.backState = backState;
    }

    public int getRenewState()
    {
        return renewState;
    }

    public void setRenewState(int renewState)
    {
        this.renewState = renewState;
    }

    public float getFine()
    {
        return fine;
    }

    public void setFine(float fine)
    {
        this.fine = fine;
    }


}
