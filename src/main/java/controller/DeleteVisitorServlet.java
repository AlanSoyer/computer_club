package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.VisitorDbDAO;
import exception.DAOException;

@WebServlet("/deletevisitor")
public class DeleteVisitorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteVisitorServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                VisitorDbDAO dao = new VisitorDbDAO();
                dao.delete(id);
                System.out.println("🗑️ Посетитель удалён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/visitors");
    }
}