package org.example.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String parameter = req.getParameter("timezone");
        if (parameter == null){
            chain.doFilter(req, res);
            return;
        }
        if (parameter.matches("UTC[-+]\\d") || parameter.matches("UTC[-+]\\d{2}")){
            if (Integer.parseInt(parameter.split("[-+]")[1]) <= 12){
                chain.doFilter(req, res);
                return;
            }
        }
        res.setContentType(TimeServlet.DEFAULT_CONTENT_TYPE);
        res.setStatus(400);
        PrintWriter writer = res.getWriter();
        writer.write("Invalid timezone");
        writer.close();
        chain.doFilter(req, res);
    }
}
