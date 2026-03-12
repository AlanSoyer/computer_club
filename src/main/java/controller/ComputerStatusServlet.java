package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ComputerStatusDbDAO;
import domain.ComputerStatus;
import exception.DAOException;

@WebServlet("/statuses")
public class ComputerStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ComputerStatusServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
            List<ComputerStatus> statuses = dao.findAll();
            request.setAttribute("statuses", statuses);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/computerStatuses.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String statusName = request.getParameter("statusName");

        ComputerStatus status = new ComputerStatus();
        status.setStatusName(statusName);

        try {
            ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
            Long id = dao.insert(status);
            System.out.println("Добавлен статус с id: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/statuses");
    }
}