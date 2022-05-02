import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * Created by dirk on 20-5-18.
 */
public class SpeelScherm extends Application {
    /** Globale variabelen voor de knoppen */
    Label naam = new Label("Dummie naam");
    Label vraag = new Label("vraag");
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stoppen");
    //Button volgendeButton = new Button("Volgende");
    Button cheatButton = new Button("naar eindscherm");
    Button vorigeVraag = new Button("vorige vraag");
    Button volgendeVraag = new Button("volgende vraag");
    RadioButton rButtonA = new RadioButton("antwoord A");
    RadioButton rButtonB = new RadioButton("antwoord B");
    RadioButton rButtonC = new RadioButton("antwoord C");
    RadioButton rButtonD = new RadioButton("antwoord D");
    Label[] ABCD = {new Label("A:"), new Label("B:"),new Label("C:"), new Label("D:")};
    ImageView vraag_afbeelding = new ImageView();

    /** Maken van het scherm. */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(finalPane(), 800, 800);
        stage.setScene(scene);
        setVraagAfbeelding("hide");
        stage.show();
    }


    private BorderPane finalPane() {
        HBox topBox = new HBox(naam, startButton);
        topBox.setAlignment(Pos.CENTER_LEFT);
        HBox bottomBox = new HBox(cheatButton, stopButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane bPane = new BorderPane();
        bPane.setTop(topBox);



        VBox vraagBox = new VBox(vraag, vraag_afbeelding);
        vraagBox.setAlignment(Pos.CENTER);
        vraagBox.setPadding(new Insets(30,0,30,30));
        vraagBox.setMaxSize(800, 100);

        bPane.setCenter(new VBox(vraagBox,toetsBox()));
        bPane.setBottom(bottomBox);

        return bPane;
    }


    private HBox toetsBox() {
        final ToggleGroup group = new ToggleGroup();
        rButtonA.setToggleGroup(group);
        rButtonB.setToggleGroup(group);
        rButtonC.setToggleGroup(group);
        rButtonD.setToggleGroup(group);

        GridPane antwoordenPane = new GridPane();

        GridPane.setConstraints(ABCD[0], 0, 0);
        GridPane.setConstraints(ABCD[1], 0, 1);
        GridPane.setConstraints(ABCD[2], 0, 2);
        GridPane.setConstraints(ABCD[3], 0, 3);
        GridPane.setConstraints(rButtonA, 1, 0);
        GridPane.setConstraints(rButtonB, 1, 1);
        GridPane.setConstraints(rButtonC, 1, 2);
        GridPane.setConstraints(rButtonD, 1, 3);

        antwoordenPane.getChildren().addAll(
                ABCD[0], ABCD[1], ABCD[2], ABCD[3], rButtonA, rButtonB, rButtonC, rButtonD);

        antwoordenPane.setHgap(20);
        antwoordenPane.setVgap(20);
        antwoordenPane.setPadding(new Insets(20, 20, 20, 20));

        HBox antwoordenBox = new HBox(vorigeVraag, antwoordenPane, volgendeVraag);
        antwoordenBox.setAlignment(Pos.CENTER);

        return antwoordenBox;
    }


    public void setVragenAntwoorden(String vraagText, String vraag_figuur, String[] antwoordenText) {
        vraag.setText(vraagText);


        setVraagAfbeelding(vraag_figuur);



        rButtonA.setText(antwoordenText[0]);
        rButtonB.setText(antwoordenText[1]);
        rButtonC.setText(antwoordenText[2]);

        if(antwoordenText.length==4) {
            rButtonD.setVisible(true);
            rButtonD.setText(antwoordenText[3]);
            ABCD[3].setVisible(true);
        } else {
            rButtonD.setVisible(false);
            ABCD[3].setVisible(false);
        }



    }


    public void setPlayFieldVisibility(boolean visibility) {
        vraag.setVisible(visibility);
        vorigeVraag.setVisible(visibility);
        volgendeVraag.setVisible(visibility);
        rButtonA.setVisible(visibility);
        rButtonB.setVisible(visibility);
        rButtonC.setVisible(visibility);
        rButtonD.setVisible(visibility);
        for (int x=0; x<4; x++) {
            ABCD[x].setVisible(visibility);
        }
    }


    public void setVraagAfbeelding(String path) {
        System.out.println("path = "+path);
        if (path.equals("hide")) {
            vraag_afbeelding.setVisible(false);
        } else {
            Image afbeelding = new Image(path, 150, 220, false, false);
            vraag_afbeelding.setImage(afbeelding);

            vraag_afbeelding.setVisible(true);
        }
    }
}
