package model.dao.impl;

import model.dao.UserDAO;
import model.domain.Users;
import utils.JDBCUtils;
import utils.XMLUtils;
import utils.page.CriteriaUsers;
import utils.page.Page;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl extends BaseDAO<Users> implements UserDAO
{
    /**
     * 添加User
     * @param users
     */
    @Override
    public void insertUser(Users users)
    {
        String sql = "INSERT INTO USERS(USERNAME, PASSWORD, USERTYPE, EMAIL, " +
                " EMAILSTATE, BOOKNUMBER, ACCOUNTID) " +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";

        /**
         * 插入非图片
         */
        int userId = insert(sql, new String[]{"USERID"}, users.getUserName(), users.getPassword(), users.getUserType(), users.getEmail(),
                users.getEmailState(), users.getBookNumber(), users.getAccountId());

        /**
         * 设置主键
         */
        users.setUserId(userId);

        /**
         * 插入图片
         */
        updatePicture(users);
    }

    /**
     * 更新图片
     * @param users
     */
    private void updatePicture(Users users)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE USERS SET USERPICTURE = ? WHERE USERID = ?";
        File file = users.getUserPicture();
        if (file != null) {
            connection = JDBCUtils.getConnection();
            try {
                preparedStatement = connection.prepareStatement(sql);
                /**
                 *获取blob
                 */
                InputStream in = new BufferedInputStream(new FileInputStream(users.getUserPicture()));
                Blob blob = connection.createBlob();
                OutputStream out = blob.setBinaryStream(1);
                int i = 0;
                byte[] buff = new byte[4096];
                while ((i = in.read(buff)) > 0) {
                    out.write(buff);
                }
                out.close();
                in.close();
                /**
                 * 执行
                 */
                preparedStatement.setBlob(1, blob);
                preparedStatement.setInt(2, users.getUserId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.release(null, preparedStatement, connection);
            }
        }
    }

    /**
     * 删除User
     * @param userId
     */
    @Override
    public void deleteUser(int userId)
    {
        String sql = "DELETE FROM USERS WHERE USERID = ?";
        update(sql, userId);
    }

    @Override
    public void updateUser(Users users)
    {
        String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, USERTYPE = ?, EMAIL = ?, " +
                " EMAILSTATE = ?, BOOKNUMBER = ?, ACCOUNTID = ? " +
                " WHERE USERId = ?";

        /**
         * 更新非图片
         */
        update(sql, users.getUserName(), users.getPassword(), users.getUserType(), users.getEmail(),
                users.getEmailState(), users.getBookNumber(), users.getAccountId(),
                users.getUserId());
        /**
         * 更新图片
         */
        updatePicture(users);
    }

    /**
     * 查询User
     * @param userId
     * @return
     */
    @Override
    public Users getUser(int userId)
    {
        Users user = null;
        String sql = "SELECT USERID, USERNAME, PASSWORD, USERTYPE, EMAIL, " +
                " EMAILSTATE, BOOKNUMBER, ACCOUNTID " +
                " FROM USERS WHERE USERID = ? ";
        user = query(sql, userId);
        /**
         * 读取图片
         */
        getPicture(user);
        return user;
    }

    /**
     * 根据邮箱
     * @param email
     * @return
     */
    @Override
    public Users getUser(String email)
    {
        Users user = null;
        String sql = "SELECT USERID, USERNAME, PASSWORD, USERTYPE, EMAIL, " +
                " EMAILSTATE, BOOKNUMBER, ACCOUNTID " +
                " FROM USERS WHERE EMAIL = ? ";
        user = query(sql, email);

        return user;
    }

    /**
     * 给user的图片赋值 从数据库读取
     * @param users
     */
    private void getPicture(Users users)
    {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        if (users != null)
        {
            String sql = "SELECT USERPICTURE FROM USERS WHERE USERID = ?";

            /**
             * 图片存在
             */
            try {
                connection = JDBCUtils.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, users.getUserId());

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                {
                    /**
                     * Blob转二进制流
                     */
                    Blob blob = resultSet.getBlob(1);
                    if (blob != null)
                    {
                        File file = new File(XMLUtils.getUserPicAdress() +"\\" +users.getUserId() +".jpg");
                        InputStream inputStream = blob.getBinaryStream();
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                        byte[] buff = new byte[4096];
                        int i = 0;
                        while ((i = inputStream.read(buff)) > 0)
                        {
                            outputStream.write(buff);
                        }
                        outputStream.close();
                        inputStream.close();
                        users.setUserPicture(file);
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                JDBCUtils.release(resultSet, preparedStatement, connection);
            }
        }


    }

    /**
     * 获取分页
     * @param criteriaUsers
     * @return
     */
    @Override
    public Page<Users> getPage(CriteriaUsers criteriaUsers)
    {
        /**
         * 实例化Page对象
         * 设置参数
         */
        Page<Users> page = new Page<>(criteriaUsers.getPageNo());
        page.setLineSize(criteriaUsers.getLineSize());
        page.setColumnSize(criteriaUsers.getColumnSize());
        page.setPageSize();
        int totalItemNumber = getTotalItemNumber(criteriaUsers);
        page.setTotalItemNumber(totalItemNumber);
        page.setPageMap(getUsersMap(criteriaUsers, page.getPageSize()));
        return page;

    }

    /**
     *获取符合条件的user
     * @param criteriaUsers
     * @return
     */
    private int getTotalItemNumber(CriteriaUsers criteriaUsers)
    {
        String keyWord = criteriaUsers.getKeyWords();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        BigDecimal bigDecimal = null;
        if ("".equals(keyWord.trim()) || keyWord == null)
        {
            String sql = "SELECT COUNT(USERID) FROM USERS ";
            bigDecimal = queryForValue(sql);
        }
        else
        {
            keyWord = "%" +keyWord +"%";
            String sql = "SELECT COUNT(USERID) FROM USERS WHERE USERNAME LIKE ? ";
            bigDecimal = queryForValue(sql, keyWord);
        }
        int totalItemNumber = bigDecimal.intValue();
        return totalItemNumber;
    }

    /**
     * 获取pageMap
     * @param criteriaUsers
     * @param pageSize
     * @return
     */
    private Map<Integer, List<Users>> getUsersMap(CriteriaUsers criteriaUsers, int pageSize)
    {
        /**
         * 参数
         */
        int lineSize = criteriaUsers.getLineSize();
        int columnSize = criteriaUsers.getColumnSize();
        String keyWord = criteriaUsers.getKeyWords().trim();
        /**
         * pageMap
         */
        Map<Integer, List<Users>> pageMap = new LinkedHashMap<>();
        /**
         * 没有输入条件查询全部
         */
        if ("".equals(keyWord) || keyWord == null)
        {
            String sql = "SELECT USERID, USERNAME, PASSWORD, USERTYPE, EMAIL, " +
                    " EMAILSTATE, BOOKNUMBER, ACCOUNTID " +
                    " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM USERS) A WHERE ROWNUM <= ?) " +
                    " WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<Users> booksList = queryForList(sql,
                        (criteriaUsers.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaUsers.getPageNo()-1)*pageSize+i*columnSize);
                if (booksList != null && booksList.size() != 0)
                {
                    pageMap.put(i, booksList);
                }
                else
                {
                    i = lineSize;
                }
                i++;
            }
        }
        else
        {
            keyWord = "%" +keyWord +"%";
            String sql = "SELECT USERID, USERNAME, PASSWORD, USERTYPE, EMAIL, " +
                    " EMAILSTATE, BOOKNUMBER, ACCOUNTID " +
                    " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM USERS WHERE USERNAME LIKE ? ) A WHERE ROWNUM <= ?) " +
                    " WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<Users> booksList = queryForList(sql, keyWord,
                        (criteriaUsers.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaUsers.getPageNo()-1)*pageSize+i*columnSize);
                if (booksList != null && booksList.size() != 0)
                {
                    pageMap.put(i, booksList);
                }
                else
                {
                    i = lineSize;
                }
                i++;
            }
        }
        /**
         * 给picture赋值
         */
        if (pageMap.size() > 0)
        {
            for (int i = 0; i < pageMap.size(); i++)
            {
                for (Users users : pageMap.get(i))
                {
                    getPicture(users);
                }
            }
        }
        return pageMap;
    }
}
