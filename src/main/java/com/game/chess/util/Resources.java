package com.game.chess.util;

import com.game.chess.view.ChessPane;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.net.URL;
import java.util.Objects;

/**
 * @author Jordan
 */
public class Resources {
    public static Image imageFromPath(String path) {
        return new Image(fromPath(path).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    }

    public static Media mediaFromPath(String path) {
        return new Media(fromPath(path).toString());
    }

    public static URL fromPath(String path) {
       return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(path));
    }
}
