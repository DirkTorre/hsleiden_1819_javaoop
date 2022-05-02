import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.io.File;

//import java.awt.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Created by dirk on 19-5-18.
 */
public class ToetsScherm extends Application {
    /** Globale variabelen voor de knoppen */
    Button afsluiten = new Button("Afsluiten");
    Button terug = new Button("Terug");
    Button maakVragen = new Button("Maak vragen");
    ComboBox<Integer> aantalVragen = new ComboBox();
//    private Desktop desktop = Desktop.getDesktop();
    File vragenFile;
    File antwoordFile;
    boolean vragenIngevoerd = false;
    boolean antwoordenIngevoerd = false;
    Label selectie = new Label("Niks geselecteerd.\n50 nieuwe vragen worden gemaakt.\nToets wordt niet opgeslagen.");
    Label error1 = new Label("");
    Label error2 = new Label("");
    final Button openButton1 = new Button("Selecteer vragen");
    final Button openButton2 = new Button("Selecteer antwoorden");
    TabPane tabPane = new TabPane();
    TextArea vragen_veld = new TextArea();
    TextArea antwoorden_veld = new TextArea();



    /** Maken van het scherm. */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(finalPane(stage), 700, 700);
        stage.setScene(scene);
        stage.show();
    }


    private BorderPane finalPane(Stage stage) {
        HBox bottomBox = new HBox(terug, afsluiten);
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        Pane selectiePane = new Pane(selectie);
        selectiePane.setStyle("-fx-background-color: #DDDDDD");

        BorderPane bPane = new BorderPane();
        bPane.setTop(selectiePane);
        bPane.setCenter(toetsPane(stage));
        bPane.setBottom(bottomBox);

        return bPane;
    }


    private TabPane toetsPane(Stage stage) {
        tabPane = new TabPane();
        Tab tabMaken = new Tab("Vragen maken");
        Tab tabInvoeren = new Tab("Vragen invoeren");

        tabMaken.setContent(maakVragenBox());
        tabInvoeren.setContent(invoerVragenBox(stage));

        tabPane.getTabs().addAll(tabMaken, tabInvoeren);

//        System.out.println("##### TAB GESELECTEERD #####");
//        System.out.println(tabPane.getSelectionModel().selectedIndexProperty());
//        System.out.println("##### TAB GESELECTEERD #####");

        return tabPane;
    }


    private VBox maakVragenBox() {
        Label label = new Label("Aantal vragen voor de toets:");
        aantalVragen.getItems().setAll(10, 15, 20, 25, 30);
        aantalVragen.setValue(20);

        return new VBox(label, aantalVragen, maakVragen);
    }


    private VBox invoerVragenBox(Stage stage) {
        // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
        Label label = new Label("Importeren van vragen en antwoorden");

//        ScrollPane vragen_scroll = new ScrollPane();
//        vragen_scroll.setPrefSize(120, 120);
//        vragen_scroll.setContent(vragen_veld);

        return new VBox(label, openButton1, vragen_veld, openButton2, antwoorden_veld, error1, error2);
    }




    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("selecteer bestand");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
    }

//    private void openFile(File file) {
//        try {
//            desktop.open(file);
//        } catch (IOException ex) {
//            Logger.getLogger(FileChooserSample.class.getName()).log(
//                    Level.SEVERE, null, ex
//            );
//        }
//    }

    public int getAantalVragen() {
        return aantalVragen.getValue().intValue();
    }
}
