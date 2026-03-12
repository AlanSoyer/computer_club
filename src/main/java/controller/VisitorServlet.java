package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.VisitorDbDAO;
import domain.Visitor;
import exception.DAOException;

@WebServlet("/visitors")
public class VisitorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisitorServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VisitorDbDAO dao = new VisitorDbDAO();
            List<Visitor> visitors = dao.findAll();
            request.setAttribute("visitors", visitors);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/visitors.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymic = request.getParameter("patronymic");
        String identityDocument = request.getParameter("identityDocument");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        Visitor visitor = new Visitor(firstName, lastName, patronymic,
                                       identityDocument, address, phone);

        try {
            VisitorDbDAO dao = new VisitorDbDAO();
            Long id = dao.insert(visitor);
            System.out.println("Добавлен посетитель с id: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/visitors");
    }
}