package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.ComputerDbDAO;
import exception.DAOException;

@WebServlet("/deletecomputer")
public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteComputerServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                ComputerDbDAO dao = new ComputerDbDAO();
                dao.delete(id);
                System.out.println("🗑️ Компьютер удалён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/computers");
    }
}