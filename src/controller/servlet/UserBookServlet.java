package controller.servlet;

import model.domain.BookType;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.domain.Users;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.UserInfoService;
import service.impl.UserInfoServiceImpl;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBookServlet extends HttpServlet
{
    /**
     * service
     */
    UserInfoService userInfoService = new UserInfoServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null)
        {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        List<BookType> typeList = userInfoService.getBookTypeList();
        request.setAttribute("typeList", typeList);
        String methodName = request.getParameter("m");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "找不到页面");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * 获取用户图书列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String pageNoStr = request.getParameter("pageNo");
        String keyWord = request.getParameter("keyWord");
        String bookType = request.getParameter("bookType");
        System.out.println(bookType);
        int pageNo = 1;
        if (keyWord == null || keyWord.trim().length() == 0)
        {
            keyWord = "";
        }
        if (bookType == null || bookType.trim().length() == 0)
        {
            bookType = "ALL";
        }
        try {
            pageNo = Integer.parseInt(pageNoStr);
        }catch (NumberFormatException e){
            pageNo = 1;
        }
        int lineSize = 3;
        CriteriaBooks criteriaBooks = new CriteriaBooks(pageNo, keyWord, lineSize, 1);
        criteriaBooks.setBookType(bookType);
        Page<Books> booksPage = userInfoService.getBooks(criteriaBooks);
        if (booksPage == null || booksPage.getPageMap().size() == 0)
        {
            request.setAttribute("errorMessage", "没有找到图书");
            request.getRequestDispatcher("/WEB-INF/user/books.jsp").forward(request, response);
            return;
        }
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("bookType", bookType);
        request.setAttribute("booksPage", booksPage);
        request.getRequestDispatcher("/WEB-INF/user/books.jsp").forward(request, response);
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
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        Books book = userInfoService.getBook(booksId);
        if (book == null)
        {
            request.setAttribute("errorMessage", "找不到此书");
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/user/book.jsp").forward(request, response);

    }

    /**
     * 获取用户详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userIdStr = request.getParameter("userId");
        int userId = -1;
        try {
            userId = Integer.parseInt(userIdStr);
        }catch (NumberFormatException e){
            userId = -1;
        }
        if (userId < 0)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);
        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/user/user.jsp").forward(request, response);

    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userIdStr = null;
        String userName = null;
        String password = null;
        String email = null;
        File file = null;  //图片
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
                        case "userId" : userIdStr = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "userName" : userName = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "password" : password = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "email" : email = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
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
                            String path = XMLUtils.getUserPicAdress() +"\\" +fileName +".jpg";
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

        //是否可以获取user
        int userId = -1;
        try {
            userId = Integer.parseInt(userIdStr);
        }catch (NumberFormatException e){
            userId = -1;
        }
        if (userId < 0)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);

        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }

        StringBuffer errorMessage = null;
        //检错 格式 空值
        errorMessage = validateFormField(userName, password, email);
        if (errorMessage.toString().length() == 0)
        {
            //2.检测格式(priceStr amountStr)
            errorMessage = validateFormat(userName, password, email);
        }

        if (errorMessage.toString().length() != 0)
        {
            //错误信息放入User 跳转
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/user/user.jsp").forward(request, response);
            return;
        }
        //设置信息
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);

        //图片不为空时 设置图片
        if (file != null)
        {
            user.setUserPicture(file);
        }

        //4.修改
        userInfoService.updateUser(user);

        //5.跳转
        request.getRequestDispatcher("/WEB-INF/user/result.jsp").forward(request, response);

    }

    /**
     * 获取借书记录列表(全部)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getRecords(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userIdStr = request.getParameter("userId");
        int userId = -1;
        try {
            userId = Integer.parseInt(userIdStr);
        }catch (NumberFormatException e){
            userId = -1;
        }
        if (userId < 0)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);

        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }

        List<BorrowedRecord> records = userInfoService.getRecords(userId);
        request.setAttribute("records", records);
        request.getRequestDispatcher("/WEB-INF/user/records.jsp").forward(request, response);

    }

    /**
     * 获取借书记录列表(未还的)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBorrowedRecords(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userIdStr = request.getParameter("userId");
        int userId = -1;
        try {
            userId = Integer.parseInt(userIdStr);
        }catch (NumberFormatException e){
            userId = -1;
        }
        if (userId < 0)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);

        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
            return;
        }

        List<BorrowedRecord> records = userInfoService.getBorrowedRecord(userId);
        request.setAttribute("records", records);
        request.getRequestDispatcher("/WEB-INF/user/borrowedRecords.jsp").forward(request, response);

    }

    /**
     * 检验空值
     * @param userName
     * @param password
     * @param email
     * @return
     */
    private StringBuffer validateFormField(String userName, String password, String email)
    {
        StringBuffer errorMessage = new StringBuffer();
        if (userName == null || "".equals(userName))
        {
            errorMessage.append("账号不能为空<br>");
        }
        if (password == null || "".equals(password))
        {
            errorMessage.append("密码不能为空<br>");
        }
        if (email == null || "".equals(email))
        {
            errorMessage.append("邮箱不能为空");
        }
        return errorMessage;
    }

    /**
     * 检验格式
     * @param userName
     * @param password
     * @param email
     * @return
     */
    private StringBuffer validateFormat(String userName, String password, String email)
    {
        Pattern pattern = null;
        Matcher matcher = null;
        String reg = null;
        StringBuffer errorMessage = new StringBuffer();  //错误信息
        //1.userName格式
        reg = "[a-zA-Z0-9]{3,8}";
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(userName);
        if (!matcher.matches())
        {
            errorMessage.append("用户名格式错误<br>");
        }

        //2.password格式
        reg = "[a-zA-Z0-9]{6,10}";
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(password);
        if (!matcher.matches())
        {
            errorMessage.append("密码格式错误<br>");
        }

        //3.email格式
        reg = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(email);
        if (!matcher.matches())
        {
            errorMessage.append("邮箱格式错误");
        }
        return errorMessage;
    }
}
