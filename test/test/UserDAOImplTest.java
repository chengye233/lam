package test;

import model.dao.UserDAO;
import model.dao.impl.UserDAOImpl;
import model.domain.Users;
import org.junit.Test;
import utils.JDBCUtils;
import utils.page.CriteriaUsers;
import utils.page.Page;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class UserDAOImplTest
{
    UserDAO userDAO = new UserDAOImpl();
    @Test
    public void insertUser()
            throws Exception
    {
        Page<Users> page = new Page<>(1);
        for (Users users : page.getPageMap().get(0))
        {

        }
    }

    @Test
    public void deleteUser()
            throws Exception
    {
        userDAO.deleteUser(4);
    }

    @Test
    public void updateUser()
            throws Exception
    {
        Users users = new Users();
        users.setUserId(41);
        users.setPassword("vvv");
        File file = new File("E:\\Codes\\JAVA\\JavaEE\\LAM\\web\\img\\userPictures\\" +users.getUserId() +".jpg");
        users.setUserPicture(file);
        System.out.println(users.getUserPicture());
        userDAO.updateUser(users);
    }

    @Test
    public void getUser()
            throws Exception
    {
        Users users = userDAO.getUser(41);
        System.out.println(users.getUserPicture());
    }

    @Test
    public void getPage()
            throws Exception
    {
        CriteriaUsers criteriaUsers = new CriteriaUsers(1, "", 2, 2);
        Page<Users> page = userDAO.getPage(criteriaUsers);
        if (page != null)
        {
            //System.out.println(page.getPageMap().size());
            for (int i = 0; i < page.getPageMap().size(); i++)
            {
                for (Users users : page.getPageMap().get(i))
                {
                    System.out.print(users.getUserId() +"\t");
                }
                System.out.println();
            }

        }

    }

}