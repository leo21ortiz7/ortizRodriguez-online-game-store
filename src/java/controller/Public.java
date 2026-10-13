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

        try {
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
                    session.setAttribute("catalog", GameDA.selectAllGames());
                    session.setAttribute("tags", TagDA.selectAllTags());

                    url = "/catalog.jsp";
                    break;
                }

                case "login": {
                    url = "/login.jsp";

                    String username = request.getParameter("username");
                    String password = request.getParameter("password");

                    User user = UserDA.selectUser(username);

                    if (user == null || !password.equals(user.getPassword())) {
                        request.setAttribute("message", "invalid credentials");
                    } else {
                        session.setAttribute("myUser", user);
                        request.setAttribute("message", "Successfull Login!");

                        url = "/Public?action=viewCatalog";

                        //this forwards to the private controller with an action value
                        //url = "/Private?action=gotoProfile";
                    }

                    break;
                }

                case "register": {
                    url = "/register.jsp";

                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");

                    User user = new User(username, email, password);

                    UserDA.insertUser(user);
                    user = UserDA.selectUser(user.getUsername());

                    Role userRole = new Role(user.getUserID(), role);
                    UserDA.insertRole(userRole);

                    break;
                }

                case "search": {
                    url = "/catalog.jsp";

                    String gameTerm = request.getParameter("gameTerm");
                    String devTerm = request.getParameter("devTerm");

                    LinkedHashMap<Integer, Game> searchedCatalog = GameSearch.search(gameTerm, devTerm);

                    session.setAttribute("gameTerm", gameTerm);
                    session.setAttribute("devTerm", devTerm);
                    session.setAttribute("catalog", searchedCatalog);

                    break;
                }

                case "singleTagFilter": {
                    url = "/catalog.jsp";

                    LinkedHashMap<Integer, Game> catalog = new LinkedHashMap<>();
                    catalog = (LinkedHashMap<Integer, Game>) session.getAttribute("catalog");

                    // Filter method only takes array of tags
                    LinkedHashMap<Integer, Tag> tagFilterList = new LinkedHashMap<>();
                    Tag tag = TagDA.selectTag(Integer.parseInt(request.getParameter("tagID")));
                    tagFilterList.put(tag.getTagID(), tag);

                    LinkedHashMap<Integer, Game> searchedCatalog = GameSearch.tagFilterExclusive(catalog, tagFilterList);

                    session.setAttribute("catalog", searchedCatalog);

                    break;
                }

                case "multiTagFilter": {
                    url = "/catalog.jsp";

                    LinkedHashMap<Integer, Game> catalog = new LinkedHashMap<>();
                    LinkedHashMap<Integer, Game> filteredCatalog = new LinkedHashMap<>();
                    LinkedHashMap<Integer, Tag> tagFilterList = new LinkedHashMap<>();
                    
                    catalog = (LinkedHashMap<Integer, Game>) session.getAttribute("catalog");
                    String[] selectedTags = request.getParameterValues("tags");
                    
                    String restriction = request.getParameter("filterRestriction");

                    if (selectedTags != null) {
                        for (String tagID : selectedTags) {
                            Tag tag = TagDA.selectTag(Integer.parseInt(tagID));
                            tagFilterList.put(tag.getTagID(), tag);
                        }
                        
                        if (restriction == null || restriction.equals("inclusive")) {
                            filteredCatalog = GameSearch.tagFilterInclusive(catalog, tagFilterList);
                        } else {
                            filteredCatalog = GameSearch.tagFilterExclusive(catalog, tagFilterList);
                        }
                        
                        session.setAttribute("catalog", filteredCatalog);
                    }
                    
                    break;
                }

            }
        } catch (SQLException | NamingException ex) {
            errors.add("Server down. Try again later.");
            LOG.log(Level.SEVERE, "*** Server Error", ex);
        }

        request.setAttribute("controller", "Public");

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
