package model.dao;

import model.domain.Books;
import utils.page.CriteriaBooks;
import utils.page.Page;

public interface BooksDAO
{
    /**
     * 插入
     * @param books
     */
    void insertBooks(Books books);

    /**
     * 删除
     * @param booksId
     */
    void deleteBooks(int booksId);

    /**
     * 更新
     * @param books
     */
    void updateBooks(Books books);

    /**
     * 根据booksId获取Books对象
     * @param booksId
     * @return
     */
    Books getBooks(int booksId);

    /**
     * 根据CriteriaBooks封装的条件获取分页
     * @param criteriaBooks
     * @return
     */
    Page<Books> getPage(CriteriaBooks criteriaBooks);

}
