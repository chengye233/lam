package model.domain;

import java.io.File;

/**
 * 用户类
 */
public class Users
{
    private int userId;  //主键 自增
    private String userName;  //用户名
    private String password;  //密码
    private int userType;  //角色类型 0:user; 1:bookManager; 2:systemManager
    private String email;  //email地址
    private int emailState;  //账号激活状态 0:未激活; 1:激活
    private int bookNumber;  //借书数量
    private int accountId;  //账户关联
    private File userPicture;  //用户头像

    /**
     *setters&getters
     */
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUserType()
    {
        return userType;
    }

    public void setUserType(int userType)
    {
        this.userType = userType;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getEmailState()
    {
        return emailState;
    }

    public void setEmailState(int emailState)
    {
        this.emailState = emailState;
    }

    public int getBookNumber()
    {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber)
    {
        this.bookNumber = bookNumber;
    }

    public int getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    public File getUserPicture()
    {
        return userPicture;
    }

    public void setUserPicture(File userPicture)
    {
        this.userPicture = userPicture;
    }
}
