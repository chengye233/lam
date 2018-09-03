package controller.servlet;

import com.sun.deploy.net.HttpResponse;
import model.domain.BookType;
import model.domain.Users;
import service.BMService;
import service.impl.BMServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookTypeServlet extends HttpServlet
{
    /**
     * Services
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
        try {
            System.out.println(methodName);
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e){
            request.setAttribute("errorMessage", "找不到页面");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
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
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/bm/"
                +page +".jsp").forward(request, response);
    }


    /**
     * 获得类别列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        List<BookType> typeList = bmService.getTypeList();
        if (typeList == null)
        {
            request.setAttribute("errorMessage", "没有此类别");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("typeList", typeList);
        request.getRequestDispatcher("/WEB-INF/bm/bookTypes.jsp").forward(request, response);
    }

    /**
     * 添加图书类别
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookTypeStr = request.getParameter("bookType");
        String typeName = request.getParameter("typeName");
        BookType bookType = null;

        //1.检测空
        StringBuffer errorMessage = validateNull(bookTypeStr, typeName);
        if ("".equals(errorMessage.toString()))
        {
            //2.检测格式
            errorMessage = validateFormat(bookTypeStr);
            if ("".equals(errorMessage.toString()))
            {
                //3.检测已有
                bookType = bmService.getBookType(bookTypeStr);
                if (bookType != null)
                {
                    errorMessage.append("该类别编码已经存在<br>");
                }
            }
        }

        //出错跳转
        if (!"".equals(errorMessage.toString()))
        {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/bm/addType.jsp").forward(request, response);
            return;
        }

        //添加
        bookType = new BookType();
        bookType.setBookType(bookTypeStr);
        bookType.setTypeName(typeName);
        bmService.addType(bookType);
        request.getRequestDispatcher("/WEB-INF/bm/result.jsp").forward(request, response);
    }

    /**
     * 检测空
     * @param bookType
     * @param typeName
     * @return
     */
    private StringBuffer validateNull(String bookType, String typeName)
    {
        StringBuffer errorMessage = new StringBuffer();
        if (bookType == null || bookType.trim().length() == 0)
        {
            errorMessage.append("类别代码不能为空<br>");
        }
        if (typeName == null || typeName.trim().length() == 0)
        {
            errorMessage.append("类别名称不能为空<br>");
        }
        return errorMessage;
    }

    /**
     * 检测格式
     * @param bookType
     * @return
     */
    private StringBuffer validateFormat(String bookType)
    {
        StringBuffer errorMessage = new StringBuffer();
        String reg = "[a-zA-Z]{1}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(bookType);
        if (!matcher.matches())
        {
            errorMessage.append("类别代码格式错误,请输入1为字母<br>");
        }
        return errorMessage;
    }


    /**
     * 获取bookType
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookTypeStr = request.getParameter("bookType");
        BookType bookType = bmService.getBookType(bookTypeStr);
        if (bookType == null)
        {
            request.setAttribute("errorMessage", "没有此类别");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("bookType", bookType);
        request.getRequestDispatcher("/WEB-INF/bm/bookType.jsp").forward(request, response);
    }

    /**
     * 修改图书类别
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String bookTypeStr = request.getParameter("bookType");
        String typeName = request.getParameter("typeName");
        BookType bookType = bmService.getBookType(bookTypeStr);
        if (bookType == null)
        {
            request.setAttribute("errorMessage", "找不到此类别");
            request.getRequestDispatcher("/WEB-INF/bm/error.jsp").forward(request, response);
            return;
        }
        bookType.setTypeName(typeName);
        bmService.updateType(bookType);
        request.getRequestDispatcher("/WEB-INF/bm/result.jsp").forward(request, response);
    }


}
