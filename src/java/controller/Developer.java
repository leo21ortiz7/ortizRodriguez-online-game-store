/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import business.Game;
import business.Tag;
import business.User;
import data.GameDA;
import data.TagDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author leo21
 */
@MultipartConfig
public class Developer extends HttpServlet {

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

        String folder = "/developer";
        String url = "";
        String action = request.getParameter("action");

        HttpSession session = request.getSession();

        ArrayList errors = new ArrayList();

        if (action == null) {
            action = "viewDevHome";
        }

        switch (action) {
            case "viewDevHome": {
                url = "/developer/developerHome.jsp";
                break;
            }
            
            case "viewNewGame": {
                url = "/newGame.jsp";
                try {
                    request.setAttribute("tags", TagDA.selectAllTags());
                } catch (SQLException | NamingException ex) {
                    errors.add("Server down. Try again later.");
                    LOG.log(Level.SEVERE, "*** Server Error", ex);
                }
            }

            case "postNewGame": {
                url = "/newGame.jsp";

                User myUser = (User) session.getAttribute("myUser");

                try {
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    String priceString = request.getParameter("price");
                    String releaseDateString = request.getParameter("release_date");

                    // Checkbox will be null when unchecked
                    boolean released = request.getParameter("released") != null;

                    double price = Double.parseDouble(priceString);
                    LocalDate releaseDate = LocalDate.parse(releaseDateString);

                    // Get cover art
                    Part coverArtPart = request.getPart("coverart");
                    byte[] coverArt = null;

                    if (coverArtPart != null && coverArtPart.getSize() > 0) {
                        coverArt = coverArtPart.getInputStream().readAllBytes();
                    }

                    // Get game file
                    Part gameFilePart = request.getPart("game_filepath");

                    String gameFilePath = "";

                    if (gameFilePart != null && gameFilePart.getSize() > 0) {
                        gameFilePath = gameFilePart.getSubmittedFileName();
                    }

                    // Create Game
                    Game game = new Game(
                            myUser.getUserID(),
                            title,
                            description,
                            price,
                            releaseDate,
                            released,
                            coverArt,
                            gameFilePath
                    );

                    // Insert game
                    GameDA.insertGame(game);

                    // Retrieve the newly created game so we have its ID
                    game = GameDA.selectGame(game.getGameID());

                    /*
         * Gallery Images
                     */
                    List<Part> galleryImages = request.getParts()
                            .stream()
                            .filter(part -> part.getName().equals("galleryImages"))
                            .toList();

                    for (Part image : galleryImages) {
                        if (image.getSize() > 0) {
                            byte[] imageBytes = image.getInputStream().readAllBytes();

                            GameDA.insertGalleryImage(
                                    game.getGameID(),
                                    imageBytes
                            );
                        }
                    }

                    /*
                     * Tags
                     */
                    String[] selectedTags = request.getParameterValues("tags");

                    if (selectedTags != null) {
                        for (String tagID : selectedTags) {
                            TagDA.insertGameTag(game.getGameID(), Integer.parseInt(tagID));
                        }
                    }

                    request.setAttribute("message", "Game successfully created!");

                    url = "/Developer?action=viewGames";

                } catch (SQLException | NamingException ex) {
                    errors.add("Server down. Try again later.");
                    LOG.log(Level.SEVERE, "*** Server Error", ex);

                } catch (NumberFormatException ex) {
                    errors.add("Invalid number entered.");
                    LOG.log(Level.SEVERE, "*** Invalid Number", ex);

                } catch (DateTimeParseException ex) {
                    errors.add("Invalid release date.");
                    LOG.log(Level.SEVERE, "*** Invalid Date", ex);

                }

                break;
            }
        }
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
