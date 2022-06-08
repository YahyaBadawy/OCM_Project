/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

/**
 *
 * @author Ahmed Faisal
 */
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns={"/*"})
public class LoggedInFilter
implements Filter {
    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            HttpSession ses = req.getSession(false);
            String reqURI = req.getRequestURI();
            if (ses != null && ses.getAttribute("LoggedUser") != null || reqURI.matches(".*(css|jpg|png|gif|js)") || reqURI.contains("javax.faces.resource") || reqURI.contains("login.xhtml") || reqURI.contains("/services/")) {
                ((HttpServletResponse)response).setHeader("X-UA-Compatible", "IE=edge");
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        }
        catch (Throwable t) {
            System.out.println(t.getMessage());
        }
    }

    public void destroy() {
    }
}