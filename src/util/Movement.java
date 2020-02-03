package util;

import chess.Chess;
import config.Player;

public class Movement {

    private Player player;
    private Chess chess;
    private Coordinate from;
    private Coordinate to;
    private boolean isKilling;

    public Movement(Player player, Chess chess, Coordinate from, Coordinate to, boolean isKilling) {
        this.player = player;
        this.chess = chess;
        this.from = from;
        this.to = to;
        this.isKilling = isKilling;
    }

    @Override
    public String toString(){
        String message = player.getName() + " move " + chess.toString() + " form " + from.toString()+
                " to " + to.toString();
        return message;
    }
}
