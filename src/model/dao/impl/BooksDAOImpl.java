package model.dao.impl;

import model.dao.BooksDAO;
import model.domain.Books;
import utils.JDBCUtils;
import utils.XMLUtils;
import utils.page.CriteriaBooks;
import utils.page.Page;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class BooksDAOImpl extends BaseDAO<Books> implements BooksDAO
{
    /**
     * 插入
     * @param books
     */
    @Override
    public void insertBooks(Books books)
    {
        String sql = "INSERT INTO Books(bookName, bookType, author, content, " +
                " price, registerTime, registerPerson, totalAmount, leftAmount) " +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        /**
         * 插入非图片
         */

        int booksId = insert(sql, new String[]{"BOOKSID"}, books.getBookName(), books.getBookType(), books.getAuthor(), books.getContent(),
                books.getPrice(), books.getRegisterTime(), books.getRegisterPerson(),
                books.getTotalAmount(), books.getLeftAmount());

        /**
         * 设置主键
         */
        books.setBooksId(booksId);

        /**
         * 插入图片
         */
        updatePicture(books);
    }

    /**
     * 删除
     * 根据booksId(主键)
     * @param booksId
     */
    @Override
    public void deleteBooks(int booksId)
    {
        String sql = "DELETE FROM Books WHERE booksId = ?";
        update(sql, booksId);
    }

    /**
     * 更新
     * @param books
     */
    @Override
    public void updateBooks(Books books)
    {
        String sql = "UPDATE BOOKS SET BOOKNAME = ?, bookType = ?, author = ?, content = ?, " +
                " price = ?, registerTime = ?, registerPerson = ?, " +
                " totalAmount = ?, leftAmount = ? WHERE booksId = ?";

        /**
         * 更新非图片
         */
        update(sql, books.getBookName(), books.getBookType(), books.getAuthor(), books.getContent(),
                books.getPrice(), books.getRegisterTime(), books.getRegisterPerson(),
                books.getTotalAmount(), books.getLeftAmount(), books.getBooksId());
        /**
         * 更新图片
         */
        updatePicture(books);
    }

    /**
     * 更新图片
     * @param books
     */
    public void updatePicture(Books books)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE books SET BOOKPICTURE = ? WHERE BOOKSID = ?";
        File file = books.getBookPicture();
        if (file != null)
        {
            connection = JDBCUtils.getConnection();
            try {
                preparedStatement = connection.prepareStatement(sql);
                /**
                 *获取blob
                 */
                InputStream in = new BufferedInputStream(new FileInputStream(books.getBookPicture()));
                Blob blob = connection.createBlob();
                OutputStream out = blob.setBinaryStream(1);
                int i = 0;
                byte[] buff = new byte[4096];
                while ((i = in.read(buff)) > 0)
                {
                    out.write(buff);
                }
                out.close();
                in.close();
                /**
                 * 执行
                 */
                preparedStatement.setBlob(1, blob);
                preparedStatement.setInt(2, books.getBooksId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                JDBCUtils.release(null, preparedStatement, connection);
            }

        }

    }

    /**
     * 根据booksId获取图片
     * @param books
     * @return
     */
    private void getPicture(Books books)
    {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT BOOKPICTURE FROM BOOKS WHERE BOOKSID = ?";

        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, books.getBooksId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                /**
                 * Blob转二进制流
                 */
                Blob blob = resultSet.getBlob(1);
                if (blob != null)
                {
                    File file = new File(XMLUtils.getBookPicAdress() +"\\" +books.getBooksId() +".jpg");
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
                    books.setBookPicture(file);
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

    /**
     * 根据booksId获取Books对象
     * @param booksId
     * @return
     */
    @Override
    public Books getBooks(int booksId)
    {
        Books books = null;

        String sql = "SELECT booksId, bookName, bookType, author, content, " +
                " price, registerTime, registerPerson, totalAmount, leftAmount " +
                " FROM books WHERE booksId = ?";
        books = query(sql, booksId);
        /**
         * 读取图片
         */
        getPicture(books);
        return books;
    }

    /**
     * 获取分页
     * @param criteriaBooks
     * @return
     */
    @Override
    public Page<Books> getPage(CriteriaBooks criteriaBooks)
    {
        /**
         * 实例化Page对象
         * 设置参数
         */
        Page<Books> page = new Page<>(criteriaBooks.getPageNo());
        int totalItemNumber = getTotalItemNumber(criteriaBooks);
        page.setTotalItemNumber(totalItemNumber);
        page.setLineSize(criteriaBooks.getLineSize());
        page.setColumnSize(criteriaBooks.getColumnSize());
        page.setPageSize();
        page.setPageMap(getBooksMap(criteriaBooks, page.getPageSize()));
        return page;
    }

    /**
     * 获取Page的分页内容(Map)
     * @param criteriaBooks
     * @return
     */
    private Map<Integer, List<Books>> getBooksMap(CriteriaBooks criteriaBooks, int pageSize)
    {
        //参数
        int lineSize = criteriaBooks.getLineSize();
        int columnSize = criteriaBooks.getColumnSize();
        String keyWord = criteriaBooks.getKeyWords();
        String bookType = criteriaBooks.getBookType();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        Map<Integer, List<Books>> pageMap = new LinkedHashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = JDBCUtils.getConnection();

        //1.没有图书类别
        if (bookType == null || "ALL".equals(bookType))
        {
            //没有输入条件查询全部
            if ("".equals(keyWord.trim()) || keyWord == null)
            {
                String sql = "SELECT booksId, bookName, bookType, author, content, " +
                        " price, registerTime, registerPerson, " +
                        " totalAmount, leftAmount FROM (SELECT A.*, ROWNUM rn FROM (SELECT * FROM books) A " +
                        " WHERE ROWNUM <= ?) WHERE rn > ? ";
                int i = 0;
                while (i < lineSize)
                {
                    List<Books> booksList = queryForList(sql,
                            (criteriaBooks.getPageNo()-1)*pageSize+(i+1)*columnSize,
                            (criteriaBooks.getPageNo()-1)*pageSize+i*columnSize);
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
                String sql = "SELECT booksId, bookName, bookType, author, content, " +
                        " price, registerTime, registerPerson, totalAmount, leftAmount " +
                        " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM Books WHERE bookName LIKE ? OR content LIKE ?) A " +
                        " WHERE ROWNUM <= ?) WHERE rn > ? ";
                int i = 0;
                while (i < lineSize)
                {
                    List<Books> booksList = queryForList(sql, keyWord, keyWord,
                            (criteriaBooks.getPageNo()-1)*pageSize+(i+1)*columnSize,
                            (criteriaBooks.getPageNo()-1)*pageSize+i*columnSize);
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
        }
        else
        {
            //没有输入条件查询全部
            if ("".equals(keyWord.trim()) || keyWord == null)
            {
                String sql = "SELECT booksId, bookName, bookType, author, content, " +
                        " price, registerTime, registerPerson, " +
                        " totalAmount, leftAmount FROM (SELECT A.*, ROWNUM rn FROM (SELECT * FROM books WHERE bookType = ?) A " +
                        " WHERE ROWNUM <= ?) WHERE rn > ? ";
                int i = 0;
                while (i < lineSize)
                {
                    List<Books> booksList = queryForList(sql, bookType,
                            (criteriaBooks.getPageNo()-1)*pageSize+(i+1)*columnSize,
                            (criteriaBooks.getPageNo()-1)*pageSize+i*columnSize);
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
                String sql = "SELECT booksId, bookName, bookType, author, content, " +
                        " price, registerTime, registerPerson, totalAmount, leftAmount " +
                        " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM Books WHERE bookType = ? AND (bookName LIKE ? OR content LIKE ?)) A " +
                        " WHERE ROWNUM <= ?) WHERE rn > ? ";
                int i = 0;
                while (i < lineSize)
                {
                    List<Books> booksList = queryForList(sql, bookType, keyWord, keyWord,
                            (criteriaBooks.getPageNo()-1)*pageSize+(i+1)*columnSize,
                            (criteriaBooks.getPageNo()-1)*pageSize+i*columnSize);
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
        }


        //给picture赋值
        if (pageMap.size() > 0)
        {
            for (int i = 0; i < pageMap.size(); i++)
            {
                for (Books books : pageMap.get(i))
                {
                    getPicture(books);
                }
            }
        }
        return pageMap;
    }

    /**
     * 获取能查询到符合条件的书的数目
     * 转换类型
     * @param criteriaBooks
     * @return
     */
    private int getTotalItemNumber(CriteriaBooks criteriaBooks)
    {
        String keyWord = criteriaBooks.getKeyWords();
        String bookType = criteriaBooks.getBookType();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        BigDecimal bigDecimal = null;
        //bookType 为空
        if (bookType == null || "ALL".equals(bookType))
        {
            if ("".equals(keyWord.trim()) || keyWord == null)
            {
                String sql = "SELECT COUNT(booksId) FROM Books ";
                bigDecimal = queryForValue(sql);
            }
            else
            {
                keyWord = "%" +keyWord +"%";
                String sql = "SELECT COUNT(booksId) FROM Books WHERE bookName LIKE ? " +
                        " OR content LIKE ?";
                bigDecimal = queryForValue(sql, keyWord, keyWord);
            }
        }
        else
        {
            if ("".equals(keyWord.trim()) || keyWord == null)
            {
                String sql = "SELECT COUNT(booksId) FROM Books WHERE bookType = ? ";
                bigDecimal = queryForValue(sql, bookType);
            }
            else
            {
                keyWord = "%" +keyWord +"%";
                String sql = "SELECT COUNT(booksId) FROM Books WHERE bookType = ? AND (bookName LIKE ? " +
                        " OR content LIKE ? ) ";
                bigDecimal = queryForValue(sql, bookType, keyWord, keyWord);
            }
        }

        int totalItemNumber = bigDecimal.intValue();
        return totalItemNumber;
    }
}
