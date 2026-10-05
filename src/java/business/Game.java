/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 *
 * @author leo21
 */
public class Game implements Serializable {
    private int gameID, userID;
    private String gameTitle;
    private String gameDescription;
    private Tag[] gameTags;
    private double gamePrice;
    private LocalDate releaseDate;
    private boolean released;
    private byte[] coverArt;
    private LinkedHashMap<Integer, byte[]> galleryImages;
    private String gameFilePath;

    public Game() {
    }
    
    // DB Insert
    public Game(int userID, 
                String gameTitle, 
                String gameDescription, 
                double gamePrice, 
                LocalDate releaseDate, 
                boolean released, 
                byte[] coverArt, 
                String gameFilePath) {
        this.userID = userID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
        this.coverArt = coverArt;
        this.gameFilePath = gameFilePath;
    }

    // DB selectAll
    public Game(int gameID, 
                int userID, 
                String gameTitle, 
                String gameDescription, 
                double gamePrice, 
                LocalDate releaseDate, 
                boolean released, 
                byte[] coverArt, 
                String gameFilePath) {
        this.gameID = gameID;
        this.userID = userID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
        this.coverArt = coverArt;
        this.gameFilePath = gameFilePath;
    }

    // Full
    public Game(int gameID, 
                int userID, 
                String gameTitle, 
                String gameDescription, 
                Tag[] gameTags, 
                double gamePrice, 
                LocalDate releaseDate, 
                boolean released, 
                byte[] coverArt, 
                LinkedHashMap<Integer, byte[]> galleryImages, 
                String gameFilePath) {
        this.gameID = gameID;
        this.userID = userID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameTags = gameTags;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
        this.coverArt = coverArt;
        this.galleryImages = galleryImages;
        this.gameFilePath = gameFilePath;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Tag[] getGameTags() {
        return gameTags;
    }

    public void setGameTags(Tag[] gameTags) {
        this.gameTags = gameTags;
    }
    
    

    public double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public byte[] getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(byte[] coverArt) {
        this.coverArt = coverArt;
    }

    public LinkedHashMap<Integer, byte[]> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImagePaths(LinkedHashMap<Integer, byte[]> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public String getGameFilePath() {
        return gameFilePath;
    }

    public void setGameFilePath(String gameFilePath) {
        this.gameFilePath = gameFilePath;
    }

    
}
