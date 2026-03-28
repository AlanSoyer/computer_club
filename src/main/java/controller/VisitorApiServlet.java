package controller;

import com.google.gson.Gson;
import dao.VisitorDbDAO;
import domain.Visitor;
import exception.DAOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/visitorapi")
public class VisitorApiServlet extends HttpServlet {
    
    private final Gson gson = new Gson();
    
    // GET - получение списка
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            VisitorDbDAO dao = new VisitorDbDAO();
            List<Visitor> visitors = dao.findAll();
            String json = gson.toJson(visitors);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    // POST - добавление нового
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Читаем JSON из тела запроса
            BufferedReader reader = request.getReader();
            Visitor visitor = gson.fromJson(reader, Visitor.class);
            
            VisitorDbDAO dao = new VisitorDbDAO();
            Long id = dao.insert(visitor);
            visitor.setId(id);
            
            String json = gson.toJson(visitor);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    // PUT - обновление
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Получаем ID из URL
            String pathInfo = request.getPathInfo();
            Long id = null;
            if (pathInfo != null && pathInfo.length() > 1) {
                id = Long.parseLong(pathInfo.substring(1));
            }
            
            // Читаем JSON из тела запроса
            BufferedReader reader = request.getReader();
            Visitor visitor = gson.fromJson(reader, Visitor.class);
            visitor.setId(id);
            
            VisitorDbDAO dao = new VisitorDbDAO();
            dao.update(visitor);
            
            String json = gson.toJson(visitor);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Invalid ID\"}");
        }
    }
    
    // DELETE - удаление
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Получаем ID из URL
            String pathInfo = request.getPathInfo();
            Long id = null;
            if (pathInfo != null && pathInfo.length() > 1) {
                id = Long.parseLong(pathInfo.substring(1));
            }
            
            if (id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"ID required\"}");
                return;
            }
            
            VisitorDbDAO dao = new VisitorDbDAO();
            dao.delete(id);
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print("{\"success\": true}");
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Invalid ID\"}");
        }
    }
}