package controller.filter;
import javax.servlet.*;
import java.io.IOException;
public class EncodingFilter implements Filter
{
    private FilterConfig filterConfig = null;
    public void init(FilterConfig config)
            throws ServletException
    {
        this.filterConfig = config;
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException
    {
        String encoding = filterConfig.getInitParameter("encoding");
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }
    public void destroy()
    {
    }
}