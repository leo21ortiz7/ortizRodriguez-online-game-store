/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author leo21
 */
public class Game implements Serializable {
    private int gameID;
    private String gameTitle;
    private String gameDescription;
    private String[] gameTags;
    private double gamePrice;
    private LocalDate releaseDate;
    private boolean released;
    private String coverArtPath;
    private String[] galleryImagePaths;
    private String gameFilePath;

    public Game() {
    }

    public Game(String gameTitle, String gameDescription, String[] gameTags, double gamePrice, LocalDate releaseDate, boolean released, String coverArtPath, String[] galleryImagePaths, String gameFilePath) {
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameTags = gameTags;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
        this.coverArtPath = coverArtPath;
        this.galleryImagePaths = galleryImagePaths;
        this.gameFilePath = gameFilePath;
    }
    
    public Game(int gameID, String gameTitle, String gameDescription, String[] gameTags, double gamePrice, LocalDate releaseDate, boolean released, String coverArtPath, String[] galleryImagePaths, String gameFilePath) {
        this.gameID = gameID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameTags = gameTags;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
        this.coverArtPath = coverArtPath;
        this.galleryImagePaths = galleryImagePaths;
        this.gameFilePath = gameFilePath;
    }
    
    public Game(String gameTitle, String gameDescription, String[] gameTags, double gamePrice, LocalDate releaseDate, boolean released) {
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameTags = gameTags;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
    }
    
    public Game(int gameID, String gameTitle, String gameDescription, String[] gameTags, double gamePrice, LocalDate releaseDate, boolean released) {
        this.gameID = gameID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameTags = gameTags;
        this.gamePrice = gamePrice;
        this.releaseDate = releaseDate;
        this.released = released;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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

    public String[] getGameTags() {
        return gameTags;
    }

    public void setGameTags(String[] gameTags) {
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

    public String getCoverArtPath() {
        return coverArtPath;
    }

    public void setCoverArtPath(String coverArtPath) {
        this.coverArtPath = coverArtPath;
    }

    public String[] getGalleryImagePaths() {
        return galleryImagePaths;
    }

    public void setGalleryImagePaths(String[] galleryImagePaths) {
        this.galleryImagePaths = galleryImagePaths;
    }

    public String getGameFilePath() {
        return gameFilePath;
    }

    public void setGameFilePath(String gameFilePath) {
        this.gameFilePath = gameFilePath;
    }

    
    
    
    
}
