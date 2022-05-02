import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by dirk on 25-4-18.
 */
public class OptieScherm extends Application {
    /** Globale variabelen voor de knoppen */
    TextField naamSpeler = new TextField("Random");
    ComboBox<Integer> aantalVragen = new ComboBox();
    Button tijdButton = new Button("Zet beslis tijd aan");
    ComboBox antwoordTijd = new ComboBox();
    Button afsluiten = new Button("Stoppen");
    Button terug = new Button("Terug");
    Button speel = new Button("Speel");
    Label labelWaarschuwing = new Label("1 van de 3 typen aminozuur " +
            "namen en 1 van\nde mogelijke antwoorden moet zijn geselecteerd!");

    CheckBox[][] gekozenVragenAntwoorden = new CheckBox[2][8];

    // todo.er is een select all  optie nodig

    /** Maken van het scherm. */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(finalPane(), 400, 700);
        stage.setScene(scene);
        stage.show();
    }


    public BorderPane finalPane() {
        HBox topBox = new HBox(afsluiten);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        HBox bottomBox = new HBox(terug, speel);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane bPane = new BorderPane();
        bPane.setTop(topBox);
        bPane.setCenter(settingsPane());
        bPane.setBottom(bottomBox);

        return bPane;
    }


    public VBox settingsPane() {
        GridPane grid = new GridPane();

        Label labelNaam = new Label("Naam speler:");
        Label labelAantal = new Label("Aantal vragen:");
        GridPane.setConstraints(labelNaam, 0, 0);
        GridPane.setConstraints(labelAantal, 0, 1);
        GridPane.setConstraints(tijdButton, 0, 2);


        aantalVragen.getItems().setAll(10, 15, 20, 25, 30);
        aantalVragen.setValue(20);
        antwoordTijd.getItems().setAll(15, 20, 25, 30);
        antwoordTijd.setValue(20);
        antwoordTijd.setVisible(false);




        String[] labels = {"volledige naam", "1-lettercode", "3-lettercode",
                "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
        GridPane vraagAntwoordGrid = new GridPane();
        //combis.setGridLinesVisible(true

        for(int y=0; y<2; y++) {
            for (int x = 0; x < 8; x++) {
                gekozenVragenAntwoorden[y][x] = new CheckBox(labels[x]);
                GridPane.setConstraints(gekozenVragenAntwoorden[y][x], (y+1)%2, x+1);
                vraagAntwoordGrid.getChildren().add(gekozenVragenAntwoorden[y][x]);
            }
        }



        //System.out.println(Font.getFamilies());
        Label vraagLabel = new Label("Vraag");
        Label antwoordLabel = new Label("Antwoord");
        antwoordLabel.setFont(Font.font ("Serif", 17));
        vraagLabel.setFont(Font.font ("Serif", 17));

        GridPane.setConstraints(vraagLabel, 0, 0);
        GridPane.setConstraints(antwoordLabel, 1, 0);
        vraagAntwoordGrid.getChildren().addAll(vraagLabel, antwoordLabel);
        vraagAntwoordGrid.setHgap(20);
        vraagAntwoordGrid.setVgap(20);
        vraagAntwoordGrid.setAlignment(Pos.CENTER);

        GridPane.setConstraints(naamSpeler, 1, 0);
        GridPane.setConstraints(aantalVragen, 1, 1);
        GridPane.setConstraints(antwoordTijd, 1, 2);

        grid.getChildren().addAll(labelNaam, labelAantal,
                naamSpeler, aantalVragen, antwoordTijd, tijdButton);

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));


        labelWaarschuwing.setPadding(new Insets(20,20,20,20));

        return new VBox(grid, labelWaarschuwing, vraagAntwoordGrid);
    }


    public String[] getActief(String VragenAntwoorden) {
        int y;
        if(VragenAntwoorden=="vragen") {
            y=1;
        } else{
            y=0;
        }
        ArrayList<String> actief = new ArrayList<String>();
        for (int x = 0; x < 8; x++) {
            if (gekozenVragenAntwoorden[y][x].isSelected()) {
                actief.add(gekozenVragenAntwoorden[y][x].getText());
            }
        }
        String stuur[] = actief.toArray(new String[actief.size()]);
        return stuur;
    }


    public int getAantalVragen() {
        //return Integer.parseInt(aantalVragen.getValue().toString());
        //System.out.println("optiescherm: "+aantalVragen.getValue());
        return aantalVragen.getValue();
    }


    public void setVragenSelectieActief(boolean actief){
        aantalVragen.setVisible(actief);
    }
}
