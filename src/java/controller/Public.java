/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import business.Role;
import business.User;
import data.UserDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo21
 */
public class Public extends HttpServlet {

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

        switch (action) {
            case "viewLogin": {
                url = "/login.jsp";
                break;
            }
            case "viewRegister": {
                url = "/register.jsp";
                break;
            }
            case "viewCatalog": {
                url = "/catalog.jsp";
                break;
            }

            case "login": {
                url = "/login.jsp";

                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                try {
                    User user = UserDA.selectUser(username);

                    if (user == null || !password.equals(user.getPassword())) {
                        request.setAttribute("message", "invalid credentials");
                    } else {
                        session.setAttribute("myUser", user);
                        request.setAttribute("message", "Successfull Login!");
                        //this forwards to the private controller with an action value
                        //url = "/Private?action=gotoProfile";
                    }

                } catch (NamingException | SQLException ex) {
                    errors.add("Server down. Try again later.");
                    LOG.log(Level.SEVERE, "*** Server down", ex);
                }
                
                break;
            }
            case "register": {
                url = "/register.jsp";

                try {
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");
                    
                    User user = new User(username, email, password);
                    
                    UserDA.insertUser(user);
                    user = UserDA.selectUser(user.getUsername());
                    
                    Role userRole = new Role(user.getUserID(), role);
                    UserDA.insertRole(userRole);
                } 
                catch (SQLException | NamingException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
                break;
            }

        }
        
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
