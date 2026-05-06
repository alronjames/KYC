package com.kyc.kyc;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ApplicantPanel extends VBox {
    private final ProgressPanel progressPanel;
    private final Stage stage;
    private final Label uploadStatus;
    private final TextField name;
    private final TextField birthday;
    private final TextField address;

    ApplicantPanel(Stage stage, ProgressPanel progressPanel) {
        super(12);
        this.stage = stage;
        this.progressPanel = progressPanel;

        Button ssoButton = new Button("Use SSO pre-fill");
        ssoButton.setMaxWidth(Double.MAX_VALUE);
        ssoButton.setStyle(UiFactory.primaryButtonStyle("#2563EB"));
        ssoButton.setOnAction(event -> prefillFromSso());

        name = UiFactory.field("Full legal name");
        birthday = UiFactory.field("Date of birth");
        address = UiFactory.field("Residential address");

        ComboBox<String> documentType = new ComboBox<String>();
        documentType.getItems().addAll("Passport", "Driver license", "National ID", "Residence permit");
        documentType.setValue("Passport");
        documentType.setMaxWidth(Double.MAX_VALUE);

        Button uploadPdf = new Button("Upload PDF ID");
        uploadPdf.setMaxWidth(Double.MAX_VALUE);
        uploadPdf.setOnAction(event -> choosePdf());

        uploadStatus = new Label("Optional: upload a PDF copy when camera capture is unavailable.");
        uploadStatus.setWrapText(true);
        uploadStatus.setTextFill(Color.web(KycTheme.MUTED));

        getChildren().addAll(
                UiFactory.sectionTitle("Applicant"),
                ssoButton,
                UiFactory.mutedLabel("Document type"),
                documentType,
                UiFactory.mutedLabel("Personal data"),
                name,
                birthday,
                address,
                uploadPdf,
                uploadStatus,
                buildTrustControls());

        setPrefWidth(300);
        setPadding(new Insets(22));
        setStyle("-fx-background-color: white; -fx-border-color: transparent transparent transparent " + KycTheme.BORDER + ";");
    }

    private void prefillFromSso() {
        name.setText("Raul Gwapo");
        birthday.setText("01-01-2001");
        address.setText("Cebu City");
        progressPanel.completeIdentityDetails();
    }

    private void choosePdf() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Upload PDF ID");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF documents", "*.pdf"));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            uploadStatus.setText("PDF attached: " + file.getName() + ". Camera capture remains recommended for fraud checks.");
            progressPanel.attachPdf();
        }
    }

    private VBox buildTrustControls() {
        VBox security = new VBox(8,
                UiFactory.sectionTitle("Trust controls"),
                UiFactory.trustedCheck("Consent recorded before capture"),
                UiFactory.trustedCheck("Data encrypted in transit"),
                UiFactory.trustedCheck("Files deleted after retention period"));
        security.setPadding(new Insets(14));
        security.setStyle("-fx-background-color: #ECFDF5; -fx-border-color: #A7F3D0; -fx-background-radius: 8; -fx-border-radius: 8;");
        return security;
    }
}
