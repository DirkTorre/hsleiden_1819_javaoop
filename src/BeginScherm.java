import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by dirk on 24-4-18.
 */
public class BeginScherm extends Application {
    /** Globale variabelen voor de knoppen */
    Button naarOptSch = new Button("Opties instellen en spelen");
    Button naarToetsSch = new Button("Toets");
    Button afsluiten = new Button("Afsluiten");

    /** Maken van het scherm. */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(maakPane(), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    private Pane maakPane() {
        naarToetsSch.setMinSize(100,30);
        naarOptSch.setMinSize(100,30);
        afsluiten.setMinSize(100,30);
        Text welkom = new Text("Welkom bij het spel");
        welkom.setStyle("-fx-font: 15 arial;");

        HBox titelBox = new HBox(welkom);
        HBox toetsBox = new HBox(naarToetsSch);
        HBox optiesBox = new HBox(naarOptSch);
        HBox afsluitenBox = new HBox(afsluiten);

        titelBox.setPadding(new Insets(20));
        optiesBox.setPadding(new Insets(10,40,25,40));
        toetsBox.setPadding(new Insets(25,40,25,40));
        afsluitenBox.setPadding(new Insets(25,40,50,40));

        titelBox.setAlignment(Pos.CENTER);
        toetsBox.setAlignment(Pos.CENTER);
        optiesBox.setAlignment(Pos.CENTER);
        afsluitenBox.setAlignment(Pos.CENTER);

        VBox ultraBox = new VBox(titelBox, optiesBox, toetsBox, afsluitenBox);
        ultraBox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane(ultraBox);
        return pane;
    }
}
