/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import business.Game;
import java.util.LinkedHashMap;

/**
 *
 * @author leo21
 */
public final class gameSearch {
    
    public static LinkedHashMap<Integer, Game> search (LinkedHashMap<Integer, Game> gameList, String gameTerm, String devTerm) {
        
        LinkedHashMap<Integer, Game> newList = new LinkedHashMap<>();
        
        for (Game game : gameList.values()) {
            if (!gameTerm.isBlank() && !devTerm.isBlank()) {
                if (game.getGameTitle().contains(gameTerm) && game.getUser().getUsername().contains(devTerm)) {
                    newList.put(game.getGameID(), game);
                }
            } else if (!gameTerm.isBlank()) {
                if (game.getGameTitle().contains(gameTerm)) {
                    newList.put(game.getGameID(), game);
                }
            } else if (!devTerm.isBlank()) {
                if (game.getUser().getUsername().contains(devTerm)) {
                    newList.put(game.getGameID(), game);
                }
            }
        }
        
        return newList;
    }
    
}
