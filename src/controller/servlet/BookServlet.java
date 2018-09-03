package controller.servlet;

import model.domain.Book;
import model.domain.BookType;
import model.domain.Books;
import model.domain.Users;
import service.BMService;
import service.impl.BMServiceImpl;
import utils.page.CriteriaBook;
import utils.page.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class BookServlet extends HttpServlet
{
    /**
     *Service
     */
    BMService bmService = new BMServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null || user.getUserType() != 1)
        {
            request.setAttribute("errorMessage", "页面不存在!");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        String methodName = request.getParameter("m");
        List<BookType> typeList = bmService.getTypeList();
        request.setAttribute("typeList", typeList);
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
        }
    }

    /**
     * 跳转
     * @param request
     * @param response
     */
    protected void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String page = request.getParameter("p");
        if (page == null || "".equals(page))
        {
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/bm/"
                +page +".jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * 获取Book分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBookPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String keyWord = request.getParameter("keyWord");
        String pageNoStr = request.getParameter("pageNo");
        int lineSize = 2;
        int columnSize = 1;
        //处理keyWord
        if (keyWord == null || keyWord.trim().length() == 0)
        {
            keyWord = "";
        }

        //判断pageNo 无效或为空则为首页
        int pageNo = -1;
        try {
            pageNo = Integer.parseInt(pageNoStr);
        }catch (NumberFormatException e){
            pageNo = -1;
        }
        if (pageNo < 0)
        {
            pageNo = 1;
        }

        keyWord = request.getParameter("keyWord");
        if (keyWord == null)
        {
            keyWord = "";
        }

        //2.根据keyWord(bookType+booksId)获取bookPage
        CriteriaBook criteriaBook = new CriteriaBook(pageNo, keyWord, lineSize, columnSize);
        Page<Book> bookPage = bmService.getBookPage(criteriaBook);
        if (bookPage == null || bookPage.getPageMap().size() == 0)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/bookPage.jsp").forward(request, response);
            return;
        }

        //3.跳转
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("bookPage", bookPage);
        request.getRequestDispatcher("/WEB-INF/bm/bookPage.jsp").forward(request, response);

    }

    /**
     * 获取Book信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBookContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookIdStr = request.getParameter("bookId");
        String keyWord = request.getParameter("keyWord");
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        }catch (NumberFormatException e){
            bookId = -1;
        }
        //有错
        if (bookId < 0)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/bookPage.jsp").forward(request, response);
            return;
        }
        if (keyWord == null)
        {
            keyWord = "";
        }
        Book book = new Book();
        book = bmService.getBookContent(bookId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/bookPage.jsp").forward(request, response);
            return;
        }
        request.setAttribute("book", book);
        request.setAttribute("keyWord", keyWord);
        request.getRequestDispatcher("/WEB-INF/bm/book.jsp").forward(request, response);

    }

    /**
     * 更新Book
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookIdStr = request.getParameter("bookId");
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        }catch (NumberFormatException e){
            bookId = -1;
        }
        if (bookId < 0)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        String keyWord = request.getParameter("keyWord");
        if (keyWord == null)
        {
            keyWord = "";
        }
        Book book = bmService.getBookContent(bookId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.setAttribute("keyWord", keyWord);
            request.getRequestDispatcher("/WEB-INF/bm/bookPage.jsp").forward(request, response);
            return;
        }
        //更新
        String useStateStr = request.getParameter("useState");
        String bookStateStr = request.getParameter("bookState");
        int useState = Integer.parseInt(useStateStr);
        int bookState = Integer.parseInt(bookStateStr);
        book.setUseState(useState);
        book.setBookState(bookState);
        bmService.updateBook(book);
        //跳转
        request.setAttribute("keyWord", keyWord);
        request.getRequestDispatcher("/WEB-INF/bm/result.jsp").forward(request, response);
    }

    /**
     * 删除Books
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String booksIdStr = request.getParameter("booksId");
        int booksId = -1;
        try {
            booksId = Integer.parseInt(booksIdStr);
        }catch (NumberFormatException e){
            booksId = -1;
        }
        if (booksId < 0)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        Books book = bmService.getBook(booksId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        //删除
        bmService.deleteBooks(booksId);
        //跳转
        request.getRequestDispatcher("/WEB-INF/bm/result.jsp").forward(request, response);

    }

    /**
     * 删除Book
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookIdStr = request.getParameter("bookId");
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        }catch (NumberFormatException e){
            bookId = -1;
        }
        if (bookId < 0)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        Book book = bmService.getBookContent(bookId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        String keyWord = request.getParameter("keyWord");
        if (keyWord == null || keyWord.trim().length() == 0)
        {
            keyWord = "";
        }
        //删除
        bmService.deleteBook(bookId);
        //跳转 调用getBookPage
        getBookPage(request, response);

    }


}
