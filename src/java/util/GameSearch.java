/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import business.Game;
import business.Tag;
import data.GameDA;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author leo21
 */
public final class GameSearch {
    
    public static LinkedHashMap<Integer, Game> search (String gameTerm, String devTerm)
            throws NamingException, SQLException {
        
        LinkedHashMap<Integer, Game> gameList = GameDA.selectAllGames();
        LinkedHashMap<Integer, Game> newList = new LinkedHashMap<>();
        
        String lowerGameTerm = gameTerm.toLowerCase();
        String lowerDevTerm = devTerm.toLowerCase();
        
        for (Game game : gameList.values()) {
            if (!lowerGameTerm.isBlank() && !lowerDevTerm.isBlank()) {
                if (game.getGameTitle().toLowerCase().contains(lowerGameTerm) 
                        && game.getUser().getUsername().toLowerCase().contains(lowerDevTerm)) {
                    newList.put(game.getGameID(), game);
                }
            } else if (!lowerGameTerm.isBlank()) {
                if (game.getGameTitle().toLowerCase().contains(lowerGameTerm)) {
                    newList.put(game.getGameID(), game);
                }
            } else if (!lowerDevTerm.isBlank()) {
                if (game.getUser().getUsername().toLowerCase().contains(lowerDevTerm)) {
                    newList.put(game.getGameID(), game);
                }
            } else {
                newList = GameDA.selectAllGames();
            }
        }
        
        return newList;
    }
    
    public static LinkedHashMap<Integer, Game> tagFilterInclusive (LinkedHashMap<Integer, Game> gameList, LinkedHashMap<Integer, Tag> filterTags)
            throws NamingException, SQLException {
        
        LinkedHashMap<Integer, Game> newList = new LinkedHashMap<>();
        
        for (Game game : gameList.values()) {
            for (Tag filterTag : filterTags.values()) {
                if (game.getGameTags().containsKey(filterTag.getTagID())) {
                    newList.put(game.getGameID(), game);
                }
            }
        }
        
        return newList;
    }
    
    public static LinkedHashMap<Integer, Game> tagFilterExclusive (LinkedHashMap<Integer, Game> gameList, LinkedHashMap<Integer, Tag> filterTags)
            throws NamingException, SQLException {
        
        LinkedHashMap<Integer, Game> newList = new LinkedHashMap<>();
        
        for (Game game : gameList.values()) {
            int matchCount = 0;
            for (Tag filterTag : filterTags.values()) {
                if (game.getGameTags().containsKey(filterTag.getTagID())) {
                   matchCount++;
                }
            }
            if (matchCount == filterTags.size()) {
                newList.put(game.getGameID(), game);
            }
        }
        
        return newList;
    }
    
}
