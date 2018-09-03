package model.dao.impl;

import model.dao.BookDAO;
import model.domain.Book;
import utils.page.CriteriaBook;
import utils.page.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO
{

    /**
     * 插入指定数量
     * @param booksId
     * @param code
     * @param amount
     */
    @Override
    public void insertBook(int booksId, String code, int amount)
    {
        String sql = "INSERT INTO BOOK(BOOKSTATE, BOOKSID, USESTATE) " +
                " VALUES(?, ?, ?)";
        int id = 0;
        for (int i = 0; i < amount; i++)
        {
            id = insert(sql, new String[]{"BOOKID"}, 0, booksId, 1);
            updateCode(id, code);
        }
    }

    /**
     * 删除指定数量
     * @param booksId
     * @param amount
     */
    @Override
    public void deleteBook(int booksId, int amount)
    {
        if (amount > 0)
        {
            String sql = "SELECT BOOKID, BOOKSTATE, BOOKSID, BOOKCODE, USESTATE " +
                    " FROM BOOK WHERE BOOKSID = ? AND BOOKSTATE = ?";
            List<Book> list = queryForList(sql, booksId, 0);
            sql = "DELETE FROM BOOK WHERE BOOKID = ?";
            if (amount > list.size())
            {
                amount = list.size();
            }
            for (int i = 0; i < amount; i++)
            {
                update(sql, list.get(i).getBookId());
            }
        }

    }

    /**
     * 更新bookCode
     * @param bookId
     * @param bookCode
     */
    private void updateCode(int bookId, String bookCode)
    {
        String sql = "UPDATE BOOK SET BOOKCODE = ? WHERE BOOKID = ?";
        update(sql, bookCode, bookId);
    }

    /**
     * 借书
     * @param booksId
     * @return
     */
    @Override
    public Book borrowBook(int booksId)
    {
        String sql = "SELECT BOOKID, BOOKSTATE, BOOKSID, BOOKCODE, USESTATE " +
                " FROM BOOK WHERE BOOKSID = ? AND BOOKSTATE = ? AND USESTATE = ?";
        List<Book> list = new ArrayList<>(queryForList(sql, booksId, 0, 1));
        Book book = list.get(0);
        book.setBookState(1);
        updateBook(book);
        return book;
    }

    /**
     * 查看信息
     * @param bookId
     * @return
     */
    @Override
    public Book getBook(int bookId)
    {
        String sql = "SELECT BOOKID, BOOKSTATE, BOOKSID, BOOKCODE, USESTATE " +
                " FROM BOOK WHERE BOOKID = ?";
        Book book = query(sql, bookId);
        return book;
    }

    /**
     * 更新某本书的信息
     * @param book
     */
    @Override
    public void updateBook(Book book)
    {
        String sql = "UPDATE BOOK SET BOOKSTATE = ?, BOOKCODE = ?, USESTATE = ? " +
                " WHERE BOOKID = ?";
        update(sql, book.getBookState(), book.getBookCode(), book.getUseState(), book.getBookId());
    }

    /**
     * 删除某一本书
     * @param bookId
     */
    @Override
    public void deleteBook(int bookId)
    {
        String sql = "DELETE FROM BOOK WHERE BOOKID = ?";
        update(sql, bookId);

    }

    /**
     * 分页
     * @param criteriaBook
     * @return
     */
    @Override
    public Page<Book> getPage(CriteriaBook criteriaBook)
    {
        /**
         * 实例化Page对象
         * 设置参数
         */
        Page<Book> page = new Page<>(criteriaBook.getPageNo());
        page.setLineSize(criteriaBook.getLineSize());
        page.setColumnSize(criteriaBook.getColumnSize());
        page.setPageSize();
        int totalItemNumber = getTotalItemNumber(criteriaBook);
        page.setTotalItemNumber(totalItemNumber);
        page.setPageMap(getUsersMap(criteriaBook, page.getPageSize()));
        return page;
    }

    /**
     *获取符合条件的book
     * @param criteriaBook
     * @return
     */
    private int getTotalItemNumber(CriteriaBook criteriaBook)
    {
        String keyWord = criteriaBook.getKeyWords();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        BigDecimal bigDecimal = null;
        if ("".equals(keyWord.trim()) || keyWord == null)
        {
            String sql = "SELECT COUNT(BOOKID) FROM BOOK ";
            bigDecimal = queryForValue(sql);
        }
        else
        {
            keyWord = "%" +keyWord +"%";
            String sql = "SELECT COUNT(BOOKID) FROM BOOK WHERE BOOKCODE LIKE ? ";
            bigDecimal = queryForValue(sql, keyWord);
        }
        int totalItemNumber = bigDecimal.intValue();
        return totalItemNumber;
    }

    /**
     * 获取pageMap
     * @param criteriaBook
     * @param pageSize
     * @return
     */
    private Map<Integer, List<Book>> getUsersMap(CriteriaBook criteriaBook, int pageSize)
    {
        /**
         * 参数
         */
        int lineSize = criteriaBook.getLineSize();
        int columnSize = criteriaBook.getColumnSize();
        String keyWord = criteriaBook.getKeyWords();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        /**
         * pageMap
         */
        Map<Integer, List<Book>> pageMap = new LinkedHashMap<>();
        /**
         * 没有输入条件查询全部
         */
        if ("".equals(keyWord.trim()) || keyWord == null)
        {
            String sql = "SELECT BOOKID, BOOKSTATE, BOOKSID, BOOKCODE, USESTATE " +
                    " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM BOOK) A WHERE ROWNUM <= ?) " +
                    " WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<Book> booksList = new ArrayList<>(queryForList(sql,
                        (criteriaBook.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaBook.getPageNo()-1)*pageSize+i*columnSize));
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
            String sql = "SELECT BOOKID, BOOKSTATE, BOOKSID, BOOKCODE, USESTATE  " +
                    " FROM(SELECT A.*, ROWNUM rn FROM(SELECT * FROM BOOK WHERE BOOKCODE LIKE ?) A WHERE ROWNUM <= ?) " +
                    " WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<Book> booksList = new ArrayList<>(queryForList(sql, keyWord,
                        (criteriaBook.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaBook.getPageNo()-1)*pageSize+i*columnSize));
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

        return pageMap;
    }
}
