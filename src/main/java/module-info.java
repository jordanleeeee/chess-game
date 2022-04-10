module com.game.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.jetbrains.annotations;

    opens com.game.chess to javafx.fxml;
    exports com.game.chess;
}
