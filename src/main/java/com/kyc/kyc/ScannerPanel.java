package com.kyc.kyc;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class ScannerPanel extends VBox {
    private final Label feedbackTitle;
    private final Label feedbackDetail;
    private final Label lightingValue;
    private final Label alignmentValue;
    private final Label glareValue;
    private final Label focusValue;
    private final Label captureHint;
    private Rectangle documentFrame;
    private Arc scanArc;
    private Line scanLine;
    private int scanTick;

    ScannerPanel() {
        super(14);

        StackPane camera = buildCameraSurface();
        feedbackTitle = new Label("Move document closer");
        feedbackTitle.setTextFill(Color.web(KycTheme.ALERT));
        feedbackTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        feedbackDetail = new Label("The ID edges are visible, but the text is too small. Move the card 10-15 cm closer until the frame turns green.");
        feedbackDetail.setWrapText(true);
        feedbackDetail.setTextFill(Color.web(KycTheme.INK));

        GridPane metrics = new GridPane();
        metrics.setHgap(12);
        metrics.setVgap(10);
        lightingValue = metric(metrics, 0, "Lighting", "Needs more light");
        alignmentValue = metric(metrics, 1, "Alignment", "Tilt left");
        glareValue = metric(metrics, 2, "Glare", "Clear");
        focusValue = metric(metrics, 3, "Focus", "Improving");

        captureHint = new Label("Auto-capture will start when all checks are clear for 2 seconds.");
        captureHint.setTextFill(Color.web(KycTheme.MUTED));

        VBox feedback = new VBox(10, feedbackTitle, feedbackDetail, metrics, captureHint);
        feedback.setPadding(new Insets(16));
        feedback.setStyle("-fx-background-color: white; -fx-border-color: " + KycTheme.BORDER + "; -fx-background-radius: 8; -fx-border-radius: 8;");

        getChildren().addAll(UiFactory.sectionTitle("Document camera"), camera, feedback);
        setPadding(new Insets(22));
        VBox.setVgrow(camera, Priority.ALWAYS);
    }

    void startFeedback() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(450), event -> {
            scanTick++;
            animateScanOverlay();

            int mode = scanTick % 12;
            if (mode < 4) {
                showWarning("Move document closer",
                        "The ID edges are visible, but the text is too small. Move the card 10-15 cm closer until the frame turns green.",
                        "Needs more light", "Center card", "Clear", "Improving");
            } else if (mode < 8) {
                showWarning("Reduce glare",
                        "A reflection is hiding the document number. Tilt the top edge down or move away from direct light.",
                        "Good", "Aligned", "Glare detected", "Sharp");
            } else {
                showReady();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void completeDocumentCapture() {
        feedbackTitle.setText("Document captured");
        feedbackTitle.setTextFill(Color.web(KycTheme.TRUST));
        feedbackDetail.setText("The document image passed quality checks. Continue to selfie match, or retake if details look incorrect.");
        captureHint.setText("Capture stored in the encrypted verification session.");
    }

    void resetDocumentCapture() {
        captureHint.setText("Auto-capture will start when all checks are clear for 2 seconds.");
        showWarning("Move document closer",
                "The ID edges are visible, but the text is too small. Move the card 10-15 cm closer until the frame turns green.",
                "Needs more light", "Center card", "Clear", "Improving");
    }

    void submitForReview() {
        captureHint.setText("Submitted securely. A reviewer can audit the checks and consent trail.");
    }

    private StackPane buildCameraSurface() {
        StackPane camera = new StackPane();
        camera.setMinHeight(450);
        camera.setStyle("-fx-background-color: #111827; -fx-background-radius: 8;");

        Rectangle videoSurface = new Rectangle(600, 420);
        videoSurface.setArcWidth(16);
        videoSurface.setArcHeight(16);
        videoSurface.setFill(Color.web("#1F2937"));
        videoSurface.widthProperty().bind(camera.widthProperty().subtract(36));
        videoSurface.heightProperty().bind(camera.heightProperty().subtract(36));

        documentFrame = new Rectangle(390, 245);
        documentFrame.setArcWidth(18);
        documentFrame.setArcHeight(18);
        documentFrame.setFill(Color.TRANSPARENT);
        documentFrame.setStroke(Color.web(KycTheme.TRUST));
        documentFrame.setStrokeWidth(4);

        scanLine = new Line(-185, -85, 185, -85);
        scanLine.setStroke(Color.web("#6EE7B7"));
        scanLine.setStrokeWidth(3);
        scanLine.setOpacity(0.9);

        scanArc = new Arc(0, 0, 250, 160, 35, 260);
        scanArc.setType(ArcType.OPEN);
        scanArc.setFill(Color.TRANSPARENT);
        scanArc.setStroke(Color.web("#38BDF8"));
        scanArc.setStrokeWidth(3);
        scanArc.setOpacity(0.75);

        Label overlayLabel = new Label("Align ID inside frame");
        overlayLabel.setTextFill(Color.WHITE);
        overlayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        StackPane.setAlignment(overlayLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(overlayLabel, new Insets(0, 0, 34, 0));

        Label liveBadge = new Label("LIVE CAMERA FEEDBACK");
        liveBadge.setTextFill(Color.WHITE);
        liveBadge.setStyle("-fx-background-color: rgba(15,118,110,0.92); -fx-background-radius: 999; -fx-padding: 7 12;");
        StackPane.setAlignment(liveBadge, Pos.TOP_LEFT);
        StackPane.setMargin(liveBadge, new Insets(24, 0, 0, 28));

        camera.getChildren().addAll(videoSurface, scanArc, documentFrame, scanLine, overlayLabel, liveBadge);
        return camera;
    }

    private void animateScanOverlay() {
        double y = -88 + (scanTick % 10) * 20;
        scanLine.setStartY(y);
        scanLine.setEndY(y);
        scanArc.setRotate(scanTick * 14);
    }

    private void showWarning(String title, String detail, String lighting, String alignment, String glare, String focus) {
        feedbackTitle.setText(title);
        feedbackTitle.setTextFill(Color.web(KycTheme.ALERT));
        feedbackDetail.setText(detail);
        lightingValue.setText(lighting);
        alignmentValue.setText(alignment);
        glareValue.setText(glare);
        focusValue.setText(focus);
        captureHint.setText("Auto-capture is paused until the issue is fixed.");
        documentFrame.setStroke(Color.web(KycTheme.WARNING));
    }

    private void showReady() {
        feedbackTitle.setText("Ready for auto-capture");
        feedbackTitle.setTextFill(Color.web(KycTheme.TRUST));
        feedbackDetail.setText("Lighting, alignment, glare, and focus are all acceptable. Hold still while the secure capture completes.");
        lightingValue.setText("Good");
        alignmentValue.setText("Aligned");
        glareValue.setText("Clear");
        focusValue.setText("Sharp");
        captureHint.setText("Auto-capture armed. Manual capture is still available.");
        documentFrame.setStroke(Color.web(KycTheme.TRUST));
    }

    private Label metric(GridPane grid, int row, String name, String value) {
        Label key = new Label(name);
        key.setTextFill(Color.web(KycTheme.MUTED));
        Label val = new Label(value);
        val.setTextFill(Color.web(KycTheme.INK));
        val.setFont(Font.font("System", FontWeight.BOLD, 13));
        grid.add(key, 0, row);
        grid.add(val, 1, row);
        return val;
    }
}
