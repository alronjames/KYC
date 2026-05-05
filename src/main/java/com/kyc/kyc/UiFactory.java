package com.kyc.kyc;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UiFactory {
    private UiFactory() {
    }

    static Label sectionTitle(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web(KycTheme.INK));
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        return label;
    }

    static Label mutedLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web(KycTheme.MUTED));
        return label;
    }

    static Label trustBadge(String text) {
        Label badge = new Label(text);
        badge.setTextFill(Color.web(KycTheme.TRUST));
        badge.setStyle("-fx-background-color: #ECFDF5; -fx-border-color: #A7F3D0; -fx-padding: 6 10; -fx-background-radius: 999; -fx-border-radius: 999;");
        return badge;
    }

    static TextField field(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    static CheckBox trustedCheck(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setSelected(true);
        checkBox.setTextFill(Color.web(KycTheme.INK));
        return checkBox;
    }

    static Region expandingSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    static String primaryButtonStyle(String color) {
        return "-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 16;";
    }
}
