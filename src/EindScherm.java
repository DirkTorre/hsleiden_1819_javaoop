import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by dirk on 24-4-18.
 */
public class EindScherm extends Application {
    /** Globale variabelen voor de knoppen */
    Button naarBeginSch = new Button("naar beginscherm");
    Button afsluiten = new Button("Afsluiten");
    Label spelerNaam = new Label("spelernaam");
    String score = "score: whatever";

    /** Maken van het scherm. */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(maakPane(), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    private Pane maakPane() {
        //naarBeginSch.setMinSize(,30);
        //afsluiten.setMinSize(100,30);








        Text spelerScore = new Text(score);
        spelerScore.setStyle("-fx-font: 40 arial;");

        HBox naamBox = new HBox(spelerNaam);
        HBox scoreBox = new HBox(spelerScore);
        HBox onderBox = new HBox(afsluiten,naarBeginSch);

        naamBox.setPadding(new Insets(50,40,25,40));
        scoreBox.setPadding(new Insets(25,40,25,40));
        onderBox.setPadding(new Insets(25,40,50,40));

        naamBox.setAlignment(Pos.CENTER);

        VBox ultraBox = new VBox(naamBox, scoreBox, onderBox);
        ultraBox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane(ultraBox);
        return pane;
    }
}
