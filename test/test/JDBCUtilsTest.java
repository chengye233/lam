package test;

import jdk.nashorn.internal.scripts.JD;
import model.dao.UserDAO;
import model.dao.impl.UserDAOImpl;
import model.domain.Users;
import org.junit.Test;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class JDBCUtilsTest
{
    @Test
    public void getConnection()
            throws Exception
    {

    }

    @Test
    public void release()
            throws Exception
    {
        String sql = "select * from users where userid = ?";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 22);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
        {
            System.out.println(resultSet.getInt(1));
        }
        JDBCUtils.release(resultSet, preparedStatement, connection);
    }

    @Test
    public void release1()
            throws Exception
    {
    }

}