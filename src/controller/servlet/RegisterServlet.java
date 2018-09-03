package controller.servlet;


import model.domain.Users;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.RegisterService;
import service.impl.RegisterServiceImpl;
import utils.XMLUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterServlet extends javax.servlet.http.HttpServlet
{
    /**
     * Service
     */
    RegisterService registerService = new RegisterServiceImpl();

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * 反射执行
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException
    {
        String methodName = request.getParameter("m");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
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
            request.getRequestDispatcher("/WEB-INF/user/registerError.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/user/"
              +page +".jsp").forward(request, response);
    }


    /**
     * 注册账号
     * @param request
     * @param response
     */
    protected void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        /**
         * 获取参数
         */
        String userName = null;
        String password = null;
        String email = null;
        File file = null;  //图片
        //form 表单域属性必须为enctype="multipart/form-data"
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
                        case "userName" : userName = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "password" : password = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");break;
                        case "email" : email = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
                    }
                }
                else
                {
                    String fileName = item.getName();  //获取上传文件名

                    //没有文件或者文件名错误 跳转
                    if (fileName == null || fileName.trim().length() == 0 || !item.getContentType().startsWith("image"))
                    {
                        /**
                         * 存放错误信息 跳转
                         */
                        request.setAttribute("errorMessage", "请上传图片类型文件!");
                        request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
                        return;
                    }

                    String path = XMLUtils.getUserPicAdress() +"\\" + fileName;
                    file = new File(path);
                    try {
                        item.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 校验文本信息
         * errorMessage为空则没有错误
         */
        StringBuffer errorMessage = validateFormField(userName, password, email);
        //1.检验空值
        if ("".equals(errorMessage.toString()))
        {
            //2.检验格式
            errorMessage = validateFormat(userName, password, email);
            if ("".equals(errorMessage.toString()))
            {
                //3.检验邮箱是否已经注册过
                errorMessage = validateRegistered(email);
            }
        }

        //有错误返回注册页
        if (!"".equals(errorMessage.toString()))
        {
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
            return;
        }

        /**
         * 生成Users对象
         */
        Users user = new Users();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserType(0);
        user.setEmailState(0);
        user.setBookNumber(0);
        user.setUserPicture(file);

        //注册
        registerService.register(user);
        request.setAttribute("registerResult", "请去邮箱激活邮件!");
        request.getRequestDispatcher("/WEB-INF/user/registerResult.jsp").forward(request, response);

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

    /**
     * 检验是否已经注册过
     * @param email
     * @return
     */
    private StringBuffer validateRegistered(String email)
    {
        StringBuffer errorMessage = new StringBuffer();
        Users users = registerService.getUser(email);
        if (users != null)
        {
            errorMessage.append("邮箱已经注册过");
        }
        return errorMessage;
    }

    /**
     * 检测账户和密码是否匹配
     * @param email
     * @param password
     * @return
     */
    private StringBuffer validateMatched(String email, String password)
    {
        StringBuffer errorMessage = new StringBuffer();
        Users user = registerService.getUser(email);
        if (!password.equals(user.getPassword()))
        {
            errorMessage.append("账户密码不匹配");
        }
        return errorMessage;
    }

    /**
     * 检测激活
     * @param email
     * @return
     */
    private StringBuffer validateActive(String email)
    {
        StringBuffer errorMessage = new StringBuffer();
        Users user = registerService.getUser(email);
        if (user.getEmailState() != 1)
        {
            errorMessage.append("账户未激活");
        }
        return errorMessage;
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void active(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException
    {
        String userIdStr = request.getParameter("userId");
        int userId = -1;
        //有错跳首页
        if (userIdStr == null || "".equals(userIdStr))
        {
            response.sendRedirect(request.getContextPath() +"/user/registerServlet?m=forward&p=register");
            return;
        }
        try {
            userId = Integer.parseInt(userIdStr);
        }catch (NumberFormatException e){
            userId = -1;
        }
        //有错跳首页
        if (userId < 0)
        {
            response.sendRedirect(request.getContextPath() +"/user/registerServlet?m=forward&p=register");
            return;
        }
        boolean state = registerService.activeUser(userId);
        //账户不存在 跳首页
        if (!state)
        {
            response.sendRedirect(request.getContextPath() +"/user/registerServlet?m=forward&p=register");
            return;
        }
        request.setAttribute("registerResult", "激活成功!");
        request.getRequestDispatcher("/WEB-INF/user/registerResult.jsp").forward(request, response);
    }

    /**
     * 登陆
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void login(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException
    {
        String email = null;
        String password = null;
        StringBuffer errorMessage = new StringBuffer();
        email = request.getParameter("email");
        password = request.getParameter("password");

        //1.检测空
        errorMessage = validateFormField("abcd123", password, email);
        if ("".equals(errorMessage.toString()))
        {
            //2.检测格式
            errorMessage = validateFormat("abcd123", password, email);
            if ("".equals(errorMessage.toString()))
            {
                //3.检测账号存在
                errorMessage = validateRegistered(email);
                if (!"".equals(errorMessage.toString()))
                {
                    //4.检测账号密码匹配
                    errorMessage = validateMatched(email, password);
                    if ("".equals(errorMessage.toString()))
                    {
                        //5.检测账号是否激活
                        errorMessage = validateActive(email);
                    }
                }
            }

        }

        //有错误跳转
        if (!"".equals(errorMessage.toString()))
        {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
            return;
        }

        //获取user放入session
        Users user = registerService.getUser(email);
        request.getSession().setAttribute("user", user);

        //根据身份跳转
        switch (user.getUserType())
        {
            case 1:{
                //跳bm
                request.getRequestDispatcher("/bm/bsServlet?m=getBooks").forward(request, response);
            }break;
            case 2:{
                //跳sm
                request.getRequestDispatcher("/sm/uiServlet?m=getUsers").forward(request, response);
            }break;
            default:{
                //跳user
                request.getRequestDispatcher("/user/ubServlet?m=getBooks").forward(request, response);
            }
        }

    }

    /**
     * 找回密码
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void password(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException
    {
        StringBuffer errorMessage = new StringBuffer();
        String email = null;
        email = request.getParameter("email");

        errorMessage = validateFormField("abcd1234", "abcd1234", email);
        //1.检验空
        if ("".equals(errorMessage.toString()))
        {
            //2.检验格式
            errorMessage = validateFormat("abcd1234", "abcd1234", email);
            if ("".equals(errorMessage.toString()))
            {
                //3.检验存在
                errorMessage = validateRegistered(email);
                if (!"".equals(errorMessage.toString()))
                {
                    //4.发送邮件
                    registerService.password(email);
                    //跳转
                    request.setAttribute("registerResult", "发送成功");
                    request.getRequestDispatcher("/WEB-INF/user/registerResult.jsp").forward(request, response);
                    return;
                }
            }

        }

        //有错误跳转
        if (!"".equals(errorMessage.toString()))
        {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/user/password.jsp").forward(request, response);
        }

    }


}
