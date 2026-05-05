package com.kyc.kyc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ProgressPanel progressPanel = new ProgressPanel();
        ScannerPanel scannerPanel = new ScannerPanel();
        ApplicantPanel applicantPanel = new ApplicantPanel(stage, progressPanel);
        KycActionBar actionBar = new KycActionBar(
                () -> {
                    progressPanel.resetDocumentCapture();
                    scannerPanel.resetDocumentCapture();
                },
                () -> {
                    progressPanel.completeDocumentCapture();
                    scannerPanel.completeDocumentCapture();
                },
                () -> {
                    progressPanel.submitForReview();
                    scannerPanel.submitForReview();
                });

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + KycTheme.SURFACE + ";");
        root.setTop(new HeaderPanel());
        root.setLeft(progressPanel);
        root.setCenter(scannerPanel);
        root.setRight(applicantPanel);
        root.setBottom(actionBar);

        Scene scene = new Scene(root, 1180, 760);
        stage.setMinWidth(1060);
        stage.setMinHeight(680);
        stage.setTitle("KYC Verification");
        stage.setScene(scene);
        stage.show();

        scannerPanel.startFeedback();
    }
}
