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

@WebServlet("/editstatus")
public class EditStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditStatusServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
            List<ComputerStatus> statuses = dao.findAll();
            request.setAttribute("statuses", statuses);
            
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                ComputerStatus editStatus = dao.findById(id);
                request.setAttribute("editStatus", editStatus);
            }
            
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/editstatus.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            String statusName = request.getParameter("statusName");
            
            if (strId != null && statusName != null) {
                Long id = Long.parseLong(strId);
                ComputerStatus status = new ComputerStatus(id, statusName);
                
                ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
                dao.update(status);
                System.out.println("✅ Статус обновлён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/statuses");
    }
}