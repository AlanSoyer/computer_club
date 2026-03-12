package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.VisitDbDAO;
import exception.DAOException;

@WebServlet("/deletevisit")
public class DeleteVisitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteVisitServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                VisitDbDAO dao = new VisitDbDAO();
                dao.delete(id);
                System.out.println("🗑️ Посещение удалено: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/visits");
    }
}