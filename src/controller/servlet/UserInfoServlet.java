package controller.servlet;

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
import utils.page.CriteriaUsers;
import utils.page.Page;

import javax.servlet.ServletException;
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

public class UserInfoServlet extends HttpServlet
{
    /**
     * Service
     */
    UserInfoService userInfoService = new UserInfoServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null || user.getUserType() != 2)
        {
            request.setAttribute("errorMessage", "页面不存在!");
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        String methodName = request.getParameter("m");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
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
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/sm/"
                +page +".jsp").forward(request, response);
    }

    /**
     * 获取用户分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String keyWord = request.getParameter("keyWord");  //关键词
        if (keyWord == null || keyWord.trim().length() == 0)
        {
            keyWord = "";
        }
        String pageNoStr = request.getParameter("pageNo");  //页号
        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(pageNoStr);
        }catch (NumberFormatException e){
            pageNo = 1;
        }

        int lineSize = 5;  //一页显示十条记录

        CriteriaUsers criteriaUsers = new CriteriaUsers(pageNo, keyWord,
                lineSize, 1);  //获取分页条件
        Page<Users> userPage = userInfoService.getUsers(criteriaUsers);
        //出错跳转
        if (userPage == null || userPage.getPageMap().size() == 0)
        {
            request.setAttribute("errorMessage", "找不到用户");
            request.getRequestDispatcher("/WEB-INF/sm/users.jsp").forward(request, response);
            return;
        }
        //正常跳转
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("userPage", userPage);
        request.getRequestDispatcher("/WEB-INF/sm/users.jsp").forward(request, response);

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
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);
        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/sm/user.jsp").forward(request, response);

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
        String userIdStr = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        int userType = Integer.parseInt(request.getParameter("userType"));

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
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);

        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }

        StringBuffer errorMessage = null;
        //检错 格式 空值
        errorMessage = validateFormField(userName, "ass456", email);
        if (errorMessage.toString().length() == 0)
        {
            //2.检测格式(priceStr amountStr)
            errorMessage = validateFormat(userName, "ass456", email);
        }

        if (errorMessage.toString().length() != 0)
        {
            //错误信息放入User 跳转
            user.setUserName(userName);
            user.setEmail(email);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/sm/user.jsp").forward(request, response);
            return;
        }
        //设置信息
        user.setUserName(userName);
        user.setEmail(email);
        user.setUserType(userType);

        //4.修改
        userInfoService.updateUser(user);

        //5.跳转
        request.getRequestDispatcher("/WEB-INF/sm/result.jsp").forward(request, response);

    }

    /**
     * 删除User
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userIdStr = request.getParameter("userId");
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
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        Users user = userInfoService.getUser(userId);

        if (user == null)
        {
            request.setAttribute("errorMessage", "用户不存在!<br>");
            request.getRequestDispatcher("/WEB-INF/sm/error.jsp").forward(request, response);
            return;
        }
        userInfoService.deleteUser(userId);
        request.getRequestDispatcher("/WEB-INF/user/result.jsp").forward(request, response);
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
