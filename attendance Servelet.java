import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdbname";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "yourpassword";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("studentName");
        String subject = request.getParameter("subject");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement pst = con.prepareStatement("INSERT INTO attendance (student_name, subject, date, status) VALUES (?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setString(2, subject);
            pst.setString(3, date);
            pst.setString(4, status);
            int result = pst.executeUpdate();

            out.println("<html><body>");
            if (result > 0) {
                out.println("<h3>Attendance saved successfully!</h3>");
            } else {
                out.println("<h3>Failed to save attendance.</h3>");
            }
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
