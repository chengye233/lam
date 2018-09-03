package model.dao;

import model.domain.Book;
import utils.page.CriteriaBook;
import utils.page.Page;

public interface BookDAO
{

    /**
     * 添加指定数量的同种书
     * code为books.getBookType+books.getBooksId
     * @param booksId
     * @param code
     * @param amount
     */
    void insertBook(int booksId, String code, int amount);

    /**
     * 删除指定数量的书
     * @param booksId
     * @param amount
     */
    void deleteBook(int booksId, int amount);

    /**
     * 借书
     * @param booksId
     * @return
     */
    Book borrowBook(int booksId);

    /**
     * 根据主键获取 分页点击时使用
     * @param bookId
     * @return
     */
    Book getBook(int bookId);

    /**
     * 更新书的状态
     * @param book
     */
    void updateBook(Book book);

    /**
     * 删除某一本书
     * @param bookId
     */
    void deleteBook(int bookId);

    /**
     * 获取分页
     * @param criteriaBook
     * @return
     */
    Page<Book> getPage(CriteriaBook criteriaBook);
}
