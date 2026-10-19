/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import business.Game;
import business.Role;
import business.Tag;
import business.User;
import data.GameDA;
import data.TagDA;
import data.UserDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.GameSearch;

/**
 *
 * @author leo21
 */
public class Private extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOG = Logger.getLogger(Public.class.getName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "";
        String action = request.getParameter("action");

        HttpSession session = request.getSession();

        ArrayList errors = new ArrayList();

        if (action == null) {
            action = "viewLogin";
        }

        try {
            switch (action) {
                case "viewGame": {
                    url = "/private/game.jsp";
                    
                    Game game = GameDA.selectGame(Integer.parseInt(request.getParameter("gameID")));
                    
                    request.setAttribute("title", game.getGameTitle());
                    break;
                }
                
                case "viewDev": {
                    url = "/private/developerProfile.jsp";
                    
                    User user = UserDA.selectUser(Integer.parseInt(request.getParameter("devID")));
                    
                    request.setAttribute("username", user.getUsername());
                    break;
                }

            }
        } catch (SQLException | NamingException ex) {
            errors.add("Server down. Try again later.");
            LOG.log(Level.SEVERE, "*** Server Error", ex);
        }

        request.setAttribute("controller", "Private");

        request.setAttribute("errors", errors);

        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
