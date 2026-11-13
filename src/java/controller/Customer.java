/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import business.Game;
import business.Order;
import business.Payment;
import business.User;
import data.GameDA;
import data.LibraryDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import util.Checkout;

/**
 *
 * @author leo21
 */
public class Customer extends HttpServlet {

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

        User myUser = (User) session.getAttribute("myUser");

        ArrayList errors = new ArrayList();

        if (action == null) {
            action = "viewCustomerHome";
        }

        try {
            switch (action) {
                case "viewCustomerHome": {
                    url = "/customer/customerHome.jsp";
                    break;
                }
                
                case"viewLibrary": {
                    LinkedHashMap<Integer, Game> libraryGames = LibraryDA.selectLibrary(myUser.getUserID());
                    session.setAttribute("libraryGames", libraryGames);
                    url = "/customer/library.jsp";
                    break;
                }
                
                case "checkoutGame": {
                    String gameID = request.getParameter("gameID");
                    Game game = GameDA.selectGame(Integer.parseInt(gameID));
                    //game.setGameTags(null);
                    
                    
                    LinkedHashMap<Integer, Game> checkoutGames = new LinkedHashMap();
                    checkoutGames.put(game.getGameID(), game);
                    
                    request.setAttribute("showTags", false);
                    session.setAttribute("checkoutGames", checkoutGames);
                    session.setAttribute("totalPrice", game.getGamePrice());
                    url = "/customer/checkout.jsp";
                    break;
                }
                
                case "completePurchase": {
                    Order order = new Order();
                    order.setUserID(myUser.getUserID());
                    order.setOrderDate(LocalDateTime.now());
                    Payment payment = new Payment();
                    payment.setPaymentDate(LocalDateTime.now());
                    
                    LinkedHashMap<Integer, Game> purchaseGames 
                            = (LinkedHashMap<Integer, Game>) session.getAttribute("checkoutGames");
                    
                    int orderID = Checkout.checkout(order, payment, purchaseGames);
                    int itemCount = purchaseGames.size();
                    
                    request.setAttribute("orderID", orderID);
                    request.setAttribute("itemCount", itemCount);
                    url = "/customer/receipt.jsp";
                }

            }
        } catch (SQLException | NamingException ex) {
            errors.add("Server down. Try again later.");
            LOG.log(Level.SEVERE, "*** Server Error", ex);
        } catch (Error ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        request.setAttribute("controller", "Customer");

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
