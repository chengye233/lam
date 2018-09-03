package service.impl;

import model.dao.AccountDAO;
import model.dao.UserDAO;
import model.dao.impl.AccountDAOImpl;
import model.dao.impl.UserDAOImpl;
import model.domain.Users;
import service.RegisterService;
import utils.MailUtils;

public class RegisterServiceImpl implements RegisterService
{
    UserDAO userDAO = new UserDAOImpl();
    AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public void register(Users user)
    {
        //获取accountId
        int accountId = accountDAO.insertAccount(1000);
        user.setAccountId(accountId);
        userDAO.insertUser(user);
        //发送邮件
        String emailMsg = "注册成功，请点击<a>http://10.151.131.64:60000/lam/user/registerServlet?m=active&userId="
                +user.getUserId()
                +"</a>激活邮件<br>" + "如果无法点击，请复制到浏览器访问。";
        MailUtils.sendMail(user.getEmail(), "用户激活", emailMsg);
    }

    @Override
    public boolean activeUser(int userId)
    {
        Users user = userDAO.getUser(userId);
        if (user == null)
        {
            return false;
        }
        if (user.getEmailState() != 1)
        {
            user.setEmailState(1);
            userDAO.updateUser(user);
        }
        return true;
    }

    @Override
    public Users getUser(String email)
    {
        Users user = null;
        user = userDAO.getUser(email);
        return user;
    }

    @Override
    public void password(String email)
    {
        Users user = userDAO.getUser(email);
        String password = user.getPassword();
        //发送邮件
        String emailMsg = "您的密码为 " +password +"<br>请及时即使修改密码，点击" +
                "<a>http://192.168.43.184:60000/lam/user/registerServlet?m=forward&p=login</a>登陆。";

        MailUtils.sendMail(user.getEmail(), "找回密码", emailMsg);
    }
}
