package controller.servlet;

import model.domain.BookType;
import model.domain.Books;
import model.domain.Users;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.BMService;
import service.impl.BMServiceImpl;
import utils.XMLUtils;
import utils.page.CriteriaBooks;
import utils.page.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BooksServlet extends HttpServlet
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
     * 获取Books分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //1.获取参数
        String keyWord = null;
        String pageNoStr = null;
        String bookType = null;
        int lineSize = 3;
        int columnSize = 1;
        //处理keyWord
        keyWord = request.getParameter("keyWord");
        if (keyWord == null || keyWord.trim().length() == 0)
        {
            keyWord = "";
        }
        //处理bookType
        bookType = request.getParameter("bookType");
        if (bookType != null)
        {
            bookType = bookType.toUpperCase();
        }
        else
        {
            bookType = "ALL";
        }

        //判断pageNo 无效或为空则为首页
        pageNoStr = request.getParameter("pageNo");
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

        //2.设置criteriaBooks
        CriteriaBooks criteriaBooks = new CriteriaBooks(pageNo, keyWord, lineSize, columnSize);

        criteriaBooks.setBookType(bookType);
        //3.获取分页
        Page<Books> booksPage = bmService.getBooks(criteriaBooks);

        System.out.print(pageNo +"  ");
        System.out.println(booksPage.getPageMap());

        //4.page的pageMap为空则显示信息
        if (booksPage.getPageMap() == null || booksPage.getPageMap().size() == 0)
        {
            request.setAttribute("errorMessage", "没有找到图书");
            request.getRequestDispatcher("/WEB-INF/bm/books.jsp").forward(request, response);
            return;

        }

        //5.放入request 跳转
        request.setAttribute("bookType", bookType);
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("booksPage", booksPage);
        request.getRequestDispatcher("/WEB-INF/bm/books.jsp").forward(request, response);
    }

    /**
     * 登记Books
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void registerBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookName = null;
        String author = null;
        String content = null;
        String bookType = null;
        String amountStr = null;
        String priceStr = null;
        File file = null;
        float price = 0;
        int totalAmount = 0;

        //读取数据
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  //检查输入请求是否为multipart表单数据
        if (isMultipart)
        {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();  //解析请求
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);  //解析器依赖于工厂
            List<FileItem> items = null;  //存储解析内容
            try {
                items = new ArrayList<>(upload.parseRequest(request));
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            //遍历容器 处理文件表单域
            for (FileItem item : items)
            {
                //文本域
                if (item.isFormField())
                {
                    switch (item.getFieldName())
                    {
                        case "bookName" : bookName = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "author" : author = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "content" : content = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "bookType" : bookType = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "price" : priceStr = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "totalAmount" : amountStr = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
                    }
                }
                else
                {
                    String fileName = item.getName();  //获取上传文件名
                    if (fileName != null && !"".equals(fileName))
                    {
                        //控制文件类型为图片
                        if (!item.getContentType().startsWith("image"))
                        {
                            //存放错误信息 跳转
                            request.setAttribute("errorMessage", "请上传图片类型文件!");
                            request.getRequestDispatcher("/WEB-INF/bm/registerBook.jsp").forward(request, response);
                            return;
                        }
                        String path = XMLUtils.getBookPicAdress() +"\\" + fileName;
                        file = new File(path);
                        try {
                            item.write(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        StringBuffer errorMessage = null;
        //1.检测空值 bookName author bookType priceStr amountStr file
        errorMessage = validateFormFiled(bookName, author, bookType,  priceStr, amountStr);
        if (file == null)
        {
            errorMessage.append("请上传图片<br>");
        }
        if (errorMessage.toString().length() == 0)
        {
            //2.检测格式(priceStr amountStr)
            errorMessage = validateFormat(amountStr, priceStr);
        }

        if (errorMessage.toString().length() != 0)
        {
            //放入错误信息 跳转
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("bookName", bookName);
            request.setAttribute("author", author);
            request.setAttribute("price", priceStr);
            request.setAttribute("totalAmount", amountStr);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/bm/registerBook.jsp").forward(request, response);
            return;
        }

        price = Float.parseFloat(priceStr);
        totalAmount = Integer.parseInt(amountStr);

        //3.创建Books对象 设置其他值
        Books book = new Books();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setContent(content);
        book.setBookType(bookType);
        book.setPrice(price);
        book.setTotalAmount(totalAmount);
        book.setBookPicture(file);
        Users user = (Users) request.getSession().getAttribute("user");
        book.setRegisterPerson(user.getUserName());
        book.setLeftAmount(totalAmount);

        //4.登记
        bmService.registerBook(book);

        //5.跳转
        request.getRequestDispatcher("/WEB-INF/bm/result.jsp").forward(request, response);

    }

    /**
     * 检验空值
     * @param bookName
     * @param author
     * @param priceStr
     * @param amountStr
     * @return
     */
    private StringBuffer validateFormFiled(String bookName, String author, String bookType,
                                           String priceStr, String amountStr)
    {
        StringBuffer errorMessage = new StringBuffer();
        if (bookName == null || bookName.trim().length() == 0)
        {
            errorMessage.append("书名不能为空<br>");
        }
        if (author == null || author.trim().length() == 0)
        {
            errorMessage.append("作者不能为空<br>");
        }
        if (bookType == null || bookType.trim().length() == 0)
        {
            errorMessage.append("图书类型不能为空<br>");
        }
        if (priceStr == null || priceStr.trim().length() == 0)
        {
            errorMessage.append("价格不能为空<br>");
        }
        if (amountStr == null || amountStr.trim().length() == 0)
        {
            errorMessage.append("数量不能为空<br>");
        }
        return errorMessage;
    }

    /**
     * 检验格式
     * @param amountStr
     * @param priceStr
     * @return
     */
    private StringBuffer validateFormat(String amountStr, String priceStr)
    {
        int amount = 0;
        float price = -1;
        StringBuffer errorMessage = new StringBuffer();
        try {
            amount = Integer.parseInt(amountStr);
        }catch (NumberFormatException e){
            amount = 0;
        }finally {
            try {
                price = Float.parseFloat(priceStr);
            }catch (NumberFormatException e){
                price = -1;
            }
        }
        if (amount == 0)
        {
            errorMessage.append("数量格式错误<br>");
        }
        if (price < 0)
        {
            errorMessage.append("价格格式错误<br>");
        }
        return errorMessage;
    }

    /**
     * 获取图书信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //检验 能否获取Books
        String booksIdStr = null;
        int booksId = -1;
        booksIdStr = request.getParameter("booksId");
        try {
            booksId = Integer.parseInt(booksIdStr);
        }catch (NumberFormatException e){
            booksId = -1;
        }
        if (booksId < 0)
        {
            request.setAttribute("errorMessage", "图书不存在");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        Books book = bmService.getBook(booksId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "图书不存在");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/bm/update.jsp").forward(request, response);
    }

    /**
     * 修改Books
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        //1.获取参数
        String booksIdStr = null;
        String bookName = null;
        String author = null;
        String content = null;
        String bookType = null;
        String priceStr = null;
        String amountStr = null;
        File file = null;
        int booksId = -1;
        int amount = 0;
        float price = 0;

        //3.通过fileUpLoader给参数复制
        if (ServletFileUpload.isMultipartContent(request))
        {
            //创建解析工厂
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建解析器
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            //获取表单信息
            List<FileItem> items = null;
            try {
                items = new ArrayList<>(upload.parseRequest(request));
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            //便利容器 给参数赋值 并进行转码
            for (FileItem item : items)
            {
                //普通表单域
                if (item.isFormField())
                {
                    switch (item.getFieldName())
                    {
                        case "booksId" : booksIdStr = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "bookName" : bookName = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "author" : author = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "content" : content = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "bookType" : bookType = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "price" : priceStr = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");break;
                        case "amount" : amountStr = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
                    }
                }
                else
                {
                    //获取文件名
                    String fileName = item.getName();
                    if (fileName != null && fileName.trim().length() != 0)
                    {
                        //控制文件类型为图片
                        if (item.getContentType().startsWith("image"))
                        {
                            String path = XMLUtils.getBookPicAdress() +"\\" +fileName;
                            file = new File(path);
                            //写入图片信息
                            try {
                                item.write(file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        //2.检查是否可以获取图书
        try {
            booksId = Integer.parseInt(booksIdStr);
        }catch (NumberFormatException e){
            booksId = -1;
        }
        if (booksId < 0)
        {
            request.setAttribute("errorMessage", "图书不存在");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        Books book = bmService.getBook(booksId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "图书不存在");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }

        StringBuffer errorMessage = null;
        //1.检测空值 bookName author bookType priceStr amountStr
        errorMessage = validateFormFiled(bookName, author, bookType,  priceStr, amountStr);
        if (errorMessage.toString().length() == 0)
        {
            //2.检测格式(priceStr amountStr)
            errorMessage = validateFormat(amountStr, priceStr);
        }

        if (errorMessage.toString().length() != 0)
        {
            //错误信息放入Book 跳转
            book.setBookName(bookName);
            book.setAuthor(author);
            book.setContent(content);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/bm/update.jsp").forward(request, response);
            return;
        }

        //3.创建Books对象 设置其他值
        amount = Integer.parseInt(amountStr);
        if (amount < 0)
        {
            if (book.getTotalAmount() +amount < 0)
            {
                amount = -book.getTotalAmount();
                book.setLeftAmount(0);
            }
        }
        else
        {
            book.setLeftAmount(book.getLeftAmount() +amount);
        }

        price = Float.parseFloat(priceStr);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setContent(content);
        book.setBookType(bookType);
        book.setPrice(price);
        book.setTotalAmount(book.getTotalAmount() +amount);
        //图片不为空时 设置图片
        if (file != null)
        {
            book.setBookPicture(file);
        }

        //4.修改
        bmService.updateBooks(book, amount);

        //5.跳转
        request.setAttribute("booksId", book.getBooksId());
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


}
