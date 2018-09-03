package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils
{

    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("lamc3p0");
    }

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Connection getConnection()
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接错误");
        }
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void release(Connection connection)
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param resultSet
     * @param preparedStatement
     * @param connection
     */
    public static void release(ResultSet resultSet, PreparedStatement preparedStatement,
                               Connection connection)
    {
        try {
            if (resultSet != null)
            {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connection != null)
                    {
                        connection.close();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void destroy()
    {
        try {
            DataSources.destroy(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
