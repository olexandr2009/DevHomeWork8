package org.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    public static final String DEFAULT_CONTENT_TYPE = "text/html; charset=utf-8";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Instant now = Instant.now();

        resp.setContentType(DEFAULT_CONTENT_TYPE);

        String parameter = req.getParameter("timezone");
        String UTCTimeZone = parameter == null ? "UTC" : parameter;
        //GMT == UTC
        TimeZone timeZone = TimeZone.getTimeZone(UTCTimeZone.replace("UTC", "GMT"));

        // Format the date and time as a string
        String dateTime = now.atZone(timeZone.toZoneId()).format(formatter);
        //write string
        PrintWriter writer = resp.getWriter();
        writer.write(String.format("%s %s", dateTime, UTCTimeZone));
        writer.close();

    }
}
