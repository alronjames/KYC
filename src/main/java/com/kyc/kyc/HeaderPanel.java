package com.kyc.kyc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderPanel extends VBox {
    HeaderPanel() {
        super(8);

        Label title = new Label("Identity verification");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web(KycTheme.INK));

        Label subtitle = new Label("Complete your KYC check with guided document capture, real-time feedback, and encrypted review.");
        subtitle.setTextFill(Color.web(KycTheme.MUTED));
        subtitle.setFont(Font.font(14));

        HBox badges = new HBox(10,
                UiFactory.trustBadge("Encrypted session"),
                UiFactory.trustBadge("Bank-grade TLS"),
                UiFactory.trustBadge("Manual review ready"));
        badges.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(title, subtitle, badges);
        setPadding(new Insets(24, 28, 16, 28));
        setStyle("-fx-background-color: white; -fx-border-color: transparent transparent " + KycTheme.BORDER + " transparent;");
    }
}
