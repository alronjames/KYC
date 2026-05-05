package com.kyc.kyc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class KycActionBar extends HBox {
    KycActionBar(Runnable retakeAction, Runnable captureAction, Runnable submitAction) {
        super(12);

        Button retake = new Button("Retake");
        retake.setOnAction(event -> retakeAction.run());

        Button capture = new Button("Manual capture");
        capture.setStyle(UiFactory.primaryButtonStyle(KycTheme.TRUST));
        capture.setOnAction(event -> captureAction.run());

        Button submit = new Button("Submit for review");
        submit.setStyle(UiFactory.primaryButtonStyle(KycTheme.INK));
        submit.setOnAction(event -> submitAction.run());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(retake, spacer, capture, submit);
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(14, 28, 18, 28));
        setStyle("-fx-background-color: white; -fx-border-color: " + KycTheme.BORDER + " transparent transparent transparent;");
    }
}
