package sst.licences.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import sst.common.file.output.OutputFile;
import sst.licences.container.LicencesContainer;
import sst.licences.control.MainController;
import sst.licences.report.PaymentsReport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting ...");
        LicencesContainer.load();

        FXMLLoader loader = new FXMLLoader();
        URL fxml = Main.class.getResource("/licenses.fxml");
        loader.setLocation(fxml);

        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Berger Club Arlonais - Licences");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setMaximized(true);
        InputStream icon = Main.class.getResourceAsStream("/icon.png");
        if (icon != null) {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(icon)));
        }
        primaryStage.setOnCloseRequest(event -> onClose());
        primaryStage.show();
    }

    private void onClose() {
        String report = new PaymentsReport()
                .input(LicencesContainer.me().membres()
                        .stream()
                        .filter(m-> Strings.isNotEmpty(LicencesContainer.me().payments(m)))
                        .collect(Collectors.toList()))
                        .format()
                                .output();

        String filename = "report.html";
        try(OutputFile outputFile = new OutputFile(filename)) {
            outputFile.print(report);
        } catch (IOException e) {
            log.error("Cannot open "+ filename, e);
        }
        log.info("... Leaving");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
