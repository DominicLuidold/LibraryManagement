package at.fhv.teamg.librarymanagement.webfrontend;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/", "/ejbServlet"})
public class EjbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    HelloStatelessBean statelessBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        PrintWriter writer = response.getWriter();

        // Call hello method on a stateless session bean
        String message = statelessBean.sayHello();
        writer.println(message);
    }
}
