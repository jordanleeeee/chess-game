package view;

import javafx.scene.control.Button;

class BigButton extends Button {

    BigButton(String text) {
        super(text);
    }

    {
        super.setStyle("-fx-font-size: 15; -fx-font-family: sans-serif;");
        String normalStyle = "-fx-font-size: 15; -fx-font-family: sans-serif; -fx-pref-width: 200; -fx-pref-height: 40; -fx-background-radius: 16px;";
        String hoverStyle = "-fx-background-color: rgb(120, 120, 120); -fx-text-fill: white;";
        setStyle(normalStyle);
        setOnMouseEntered(e -> setStyle(hoverStyle+normalStyle));
        setOnMouseExited(e -> setStyle(normalStyle));
    }
}
