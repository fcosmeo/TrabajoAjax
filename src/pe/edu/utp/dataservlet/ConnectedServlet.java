package pe.edu.utp.dataservlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "ConnectedServlet",urlPatterns = "/connected")
public class ConnectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType( "text/html; charset=iso-8859-1" );
        PrintWriter out = null;
        out = response.getWriter();
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");

        Connection con;
        Statement stmt;
        PreparedStatement psInsertar;

        //JDBC DataSource Connection Test
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            con = ds.getConnection();
            stmt = con.createStatement();
            psInsertar=con.prepareStatement("insert into usuarios(first_name,last_name,email) values (?,?,?)");
            psInsertar.setString(1,nombre);
            psInsertar.setString(2,apellido);
            psInsertar.setString(3,email);

            psInsertar.executeUpdate();

            ResultSet rs = stmt.executeQuery("SELECT first_name,last_name,email FROM usuarios");

            if(rs != null){

                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<td>Nombre</td>");
                out.println("<td>Apellido</td>");
                out.println("<td>Email</td>");
                out.println("</tr>");

                while(rs.next()){

                    out.println("<tr>");
                    out.println("<td >"+rs.getString("first_name")+"</td>");
                    out.println("<td >"+rs.getString("last_name")+"</td>");
                    out.println("<td >"+rs.getString("email")+"</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

            }else{
                out.println("Not lucky this time");
            }
        }catch(NamingException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

