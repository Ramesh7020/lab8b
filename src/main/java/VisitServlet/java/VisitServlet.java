package VisitServlet.java;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/VisitServlet")   // Annotation replaces web.xml
public class VisitServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");
        int visitCount = 1;

        // Read existing cookies
        Cookie cookies[] = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitCount")) {
                    visitCount = Integer.parseInt(c.getValue());
                    visitCount++;
                }
            }
        }

        // Create cookies with values
        Cookie nameCookie = new Cookie("username", name);
        Cookie countCookie = new Cookie("visitCount", String.valueOf(visitCount));

        // Set expiry time (30 seconds)
        nameCookie.setMaxAge(30);
        countCookie.setMaxAge(30);

        // Add cookies to response
        response.addCookie(nameCookie);
        response.addCookie(countCookie);

        // Output
        out.println("<html><body>");

        out.println("<h2>Welcome back " + name + "!</h2>");
        out.println("<p>You have visited this page " + visitCount + " times.</p>");

        out.println("<h3>Cookies and their Values:</h3>");

        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("Name: " + c.getName() +
                            " | Value: " + c.getValue() + "<br>");
            }
        }

        out.println("<br><b>Note:</b> Cookies will expire in 30 seconds.");
        out.println("</body></html>");
    }
}