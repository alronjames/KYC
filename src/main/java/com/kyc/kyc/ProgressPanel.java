package com.kyc.kyc;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ProgressPanel extends VBox {
    private final ProgressBar progressBar;
    private final Label progressLabel;
    private final Label stepIdentity;
    private final Label stepDocument;
    private final Label stepSelfie;
    private final Label stepReview;

    ProgressPanel() {
        super(16);

        stepIdentity = stepLabel("1  Identity details", true);
        stepDocument = stepLabel("2  Document scan  Current", true);
        stepSelfie = stepLabel("3  Selfie match", false);
        stepReview = stepLabel("4  Secure review", false);

        progressBar = new ProgressBar(0.46);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setStyle("-fx-accent: " + KycTheme.TRUST + ";");

        progressLabel = new Label("46% complete");
        progressLabel.setTextFill(Color.web(KycTheme.INK));
        progressLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

        getChildren().addAll(
                UiFactory.sectionTitle("Progress"),
                progressLabel,
                progressBar,
                stepIdentity,
                stepDocument,
                stepSelfie,
                stepReview,
                UiFactory.expandingSpacer(),
                UiFactory.sectionTitle("Priority"),
                priorityRow("Must", "Actionable error feedback"),
                priorityRow("Must", "Visual progress tracker"),
                priorityRow("Should", "Auto-capture overlay"),
                priorityRow("Should", "Security badges"));

        setPrefWidth(250);
        setPadding(new Insets(22));
        setStyle("-fx-background-color: white; -fx-border-color: transparent " + KycTheme.BORDER + " transparent transparent;");
    }

    void completeIdentityDetails() {
        updateProgress(0.58, "58% complete");
        stepIdentity.setText("1  Identity details  Complete");
    }

    void attachPdf() {
        updateProgress(0.62, "62% complete");
    }

    void completeDocumentCapture() {
        updateProgress(0.72, "72% complete");
        stepDocument.setText("2  Document scan  Captured");
        stepSelfie.setText("3  Selfie match  Current");
    }

    void resetDocumentCapture() {
        updateProgress(0.46, "46% complete");
        stepDocument.setText("2  Document scan  Current");
        stepSelfie.setText("3  Selfie match");
    }

    void submitForReview() {
        updateProgress(1.0, "100% complete");
        stepReview.setText("4  Secure review  Submitted");
    }

    private void updateProgress(double value, String text) {
        progressBar.setProgress(value);
        progressLabel.setText(text);
    }

    private Label stepLabel(String text, boolean active) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setTextFill(Color.web(active ? KycTheme.INK : KycTheme.MUTED));
        label.setStyle("-fx-background-color: " + (active ? "#E6F4F1" : "#F8FAFC") + "; -fx-padding: 11 12; -fx-background-radius: 8;");
        return label;
    }

    private Label priorityRow(String priority, String text) {
        Label label = new Label(priority + "  " + text);
        label.setWrapText(true);
        label.setTextFill(Color.web(KycTheme.INK));
        label.setStyle("-fx-padding: 8 10; -fx-background-color: #F8FAFC; -fx-background-radius: 8;");
        return label;
    }
}
