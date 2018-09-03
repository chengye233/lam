package model.dao.impl;

import model.dao.DAO;
import oracle.jdbc.proxy.annotation.Pre;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtils;
import utils.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO<T> implements DAO<T>
{
    private QueryRunner queryRunner = new QueryRunner();
    private Class<T> clazz;

    public BaseDAO()
    {
        //获取T的类型
        clazz = ReflectionUtils.getSuperGenericType(this.getClass());
    }

    /**
     * 插入
     * @param sql
     * @param args
     * @return 主键
     */
    @Override
    public int insert(String sql, String[] col, Object... args)
    {
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            /**
             * 返回列名为col的列(主键)
             */
            preparedStatement = connection.prepareStatement(sql, col);
            for (int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i+1, args[i]);
            }
            preparedStatement.executeUpdate();
            /**
             * 获取主键
             */
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next())
            {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(resultSet, preparedStatement, connection);
        }
        return id;

    }

    /**
     * 更新&删除
     * @param sql
     * @param args
     */
    @Override
    public void update(String sql, Object... args)
    {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection);
        }
    }

    /**
     * 查询单条记录
     * @param sql
     * @param args
     * @return T
     */
    @Override
    public T query(String sql, Object... args)
    {
        Connection connection = null;
        T obj = null;
        try {
            connection = JDBCUtils.getConnection();
            obj = queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection);
        }
        return obj;
    }

    /**
     * 获取一组记录 以List存储
     * @param sql
     * @param args
     * @return List<T>
     */
    @Override
    public List<T> queryForList(String sql, Object... args)
    {
        Connection connection = null;
        List<T> list = null;
        try {
            connection = JDBCUtils.getConnection();
            list = new ArrayList<>(queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection);
            return list;
        }

    }

    /**
     * 查询单个值
     * eg:COUNT(),MAX(),MIN()
     * 可能会有类型转换
     * @param sql
     * @param args
     * @param <V>
     * @return 查询的值的类型
     */
    @Override
    public <V> V queryForValue(String sql, Object... args)
    {
        Connection connection = null;
        V value = null;
        try {
            connection = JDBCUtils.getConnection();
            value = queryRunner.query(connection, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection);
        }
        return value;
    }
}
