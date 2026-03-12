package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.ComputerStatusDbDAO;
import exception.DAOException;

@WebServlet("/deletestatus")
public class DeleteStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteStatusServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
                dao.delete(id);
                System.out.println("🗑️ Статус удалён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/statuses");
    }
}