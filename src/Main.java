import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oracle.jrockit.jfr.StringConstantPool;

import java.io.File;
import java.util.Arrays;

import static javafx.stage.StageStyle.UNDECORATED;

// todo. Speelscherm werkend met de nieuwe vragen en antwoorden methode.
// todo. Er voor zorgen dat ingevoerde vragen en antwoorden werken.
// todo. controleren op errors

/**
 * Created by dirk on 8-5-18.
 */
public class Main extends Application {
    /** Aanmaken schermen en variabelen */
    BeginScherm beginScherm = new BeginScherm();
    OptieScherm optieScherm = new OptieScherm();
    ToetsScherm toetsScherm = new ToetsScherm();
    SpeelScherm speelScherm = new SpeelScherm();
    EindScherm eindScherm = new EindScherm();
    MaakEenVraagAntwoord maakVraagSet;
    SchrijfVragenAntwoorden schrijven = new SchrijfVragenAntwoorden();
    String[] actieveVragen;
    String[] actieveAntwoorden;
    int actieveAantal;
    boolean tijdAan = true;
    String vragenBestandNaam = "";
    String antwoordenBestandNaam;
    String vragenPath;
    String antwoordenPath;
    String laatsteInvoer = "";
    Boolean vragenAntwoordenGeimporteerd = false;
    VragenAntwoorden vrAnt;
    VragenAntwoorden test;
    int huidigeVraag = 0;
    int aantalVragen;
    boolean vragenAntwoordenGemaakt = false;
    boolean vragenAntwoordenHandmatigGemaakt = false;
    boolean vragenIngevoerd;
    boolean antwoordenIngevoerd;
    final FileChooser fileChooser = new FileChooser();
    File vragenFile;
    File antwoordFile;
    int beslisTijd;



    String[] gebruikte_correcte_antwoorden;
    String[] gebruikte_vragen;
    String[][] gebruikte_antwoorden;
    String[] gebruikte_figuren;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(UNDECORATED);
        buttonsBinden(primaryStage);
        beginScherm.start(primaryStage);
    }

    private void buttonsBinden(Stage primaryStage) {
        beginScherm.naarOptSch.setOnAction(e -> {
            //optieScherm.setVragenSelectieActief(!vragenAntwoordenGeimporteerd);
            optieScherm.antwoordTijd.setVisible(false);
            optieScherm.tijdButton.setText("Zet tijd aan");
            tijdAan = false;

            optieScherm.start(primaryStage);
//            System.out.println(vragenBestandNaam);
//            System.out.println(antwoordenBestandNaam);
        });
        beginScherm.afsluiten.setOnAction(e -> primaryStage.close());
        beginScherm.naarToetsSch.setOnAction(e -> {
            toetsScherm.start(primaryStage);
            toetsScherm.vragen_veld.setText("");
            toetsScherm.antwoorden_veld.setText("");

            gebruikte_correcte_antwoorden = null;
            gebruikte_vragen = null;
            gebruikte_vragen = null;
            gebruikte_antwoorden = null;
            gebruikte_figuren = null;
        });

        //speelScherm.rButtonA.setText("tekst kan overal worden veranderd");

        optieScherm.afsluiten.setOnAction(e -> primaryStage.close());
        optieScherm.terug.setOnAction(e -> beginScherm.start(primaryStage));
        optieScherm.tijdButton.setOnAction( e -> {
            tijdAan = !tijdAan;
            if (tijdAan) {
                optieScherm.tijdButton.setText("Zet tijd uit");
            } else {
                optieScherm.tijdButton.setText("Zet tijd aan");
            }
            optieScherm.antwoordTijd.setVisible(tijdAan);
        });
        optieScherm.speel.setOnAction(e -> {
            if(doorNaarSpeel()){
                //System.out.println("actieve vragen en atwoorden methode uitvoeren");
                speelScherm.startButton.setVisible(true);

                actieveVragen = optieScherm.getActief("vragen");
                actieveAntwoorden = optieScherm.getActief("antwoorden");
                actieveAantal = optieScherm.getAantalVragen();
                aantalVragen = actieveAantal;
                beslisTijd = Integer.parseInt(optieScherm.antwoordTijd.getValue().toString());

                System.out.println("Aantal vragen:");
                System.out.println(aantalVragen);
                System.out.println("Tijd aan:");
                System.out.println(tijdAan);
                System.out.println("beslis tijd:");
                System.out.println(beslisTijd);


                // Als er geen vragen zijn ingevoerd, dan nieuwe vragen maken.
                if(gebruikte_correcte_antwoorden==null || gebruikte_vragen==null
                        || gebruikte_antwoorden==null || gebruikte_figuren==null) {
                    gebruikte_vragen = new String[aantalVragen];
                    gebruikte_figuren  = new String[aantalVragen];
                    gebruikte_correcte_antwoorden = new String[aantalVragen];
                    gebruikte_antwoorden = new String[aantalVragen][];
                    for (int i = 0; i < aantalVragen; i++) {
                        maakVraagSet = new MaakEenVraagAntwoord(actieveVragen, actieveAntwoorden);
                        gebruikte_vragen[i] = maakVraagSet.getVraag();
                        gebruikte_figuren[i] = maakVraagSet.getVraagPlaatje();
                        gebruikte_antwoorden[i] = maakVraagSet.getAntwoorden();
                        gebruikte_correcte_antwoorden[i] = maakVraagSet.getCorrectAntwoord();
                        System.out.println(">>>>>>>>>>>>>>>>>>   "+ i);
                    }
                } else {
                    // als je iets hebt ingeladen dan wordt aantal vragen ingesteld om aantal dat wordt gevonden in file
                    aantalVragen = gebruikte_vragen.length;
                    System.out.println(">>file gebruiken aantal vragen gelijk gezet aan aantal vragen in file:");
                    System.out.println(aantalVragen);
                    System.out.println();
                    System.out.println();
                }

//                  Zou niet meer nodig moeten zijn?
//                //System.out.println("maken vragen antwoorden");
//                //System.out.println(Arrays.asList(actieveVragen));
//                //System.out.println(Arrays.asList(actieveAntwoorden));
//                // todo.soms loopt t programma vast en doet t niks.. komt door bepaalde input selectie
//                // kan zijn dat alles weer werkt.
//                // als optie 2 werkt, dan een voor een alle opties uitproberen.
//
//                if(!laatsteInvoer.equals("ingeladen") && !laatsteInvoer.equals("gemaakt")) {
//                    vragenAntwoordenMaken(20);
//                }
//                laatsteInvoer = "";
//
//                test = new VragenAntwoorden(actieveVragen, actieveAntwoorden, actieveAantal);
//
//
//                System.out.println("bestand dat hij zou moeten gebruiken");
//                System.out.println(vragenBestandNaam);
//                System.out.println(antwoordenBestandNaam);
//                ImportExportVragenAntwoorden geimporteerd = new ImportExportVragenAntwoorden();
//                int[][] vragenAntwoordenZinloos = geimporteerd.ImportVragenAntwoorden(vragenBestandNaam, antwoordenBestandNaam);
//
//                for (int y=0; y<vragenAntwoordenZinloos.length; y++) {
//                    for (int x = 0; x < vragenAntwoordenZinloos[y].length; x++) {
//                        System.out.print(vragenAntwoordenZinloos[y][x]+"  ");
//                    }
//                    System.out.println();
//                }



//                // Oude meuk die wel gebruikt moet worden, maar op nieuwe manier
//                speelScherm.setVragenAntwoorden(test.maakVraagAlsString(0),
//                        test.maakStringAntwoorden(0));

//                // figuur moet nog worden meegegeven (overloading gebruiken om figuur er in te zetten?)
//                // deze moet ergens anders
//                speelScherm.setVragenAntwoorden(gebruikte_vragen[0], gebruikte_antwoorden[0]);

                speelScherm.naam.setText(optieScherm.naamSpeler.getText());

                speelScherm.setPlayFieldVisibility(false);


                speelScherm.start(primaryStage);

            }
        });


        toetsScherm.afsluiten.setOnAction(e -> primaryStage.close());


        toetsScherm.terug.setOnAction(e -> {
            //todo. Normaal van scherm wisselen, zonder variabelen aan te passen of wat dan ook.
//            if (toetsScherm.tabPane.getSelectionModel().selectedIndexProperty().intValue() == 1) {
//                // file inlaad scherm
//                // als beiden ingevoerd zijn:
//                if (vragenIngevoerd && antwoordenIngevoerd) {
//                    // checken of ze wel dezelfde datum hebben.
//                    String vragenDatum = vragenFile.getAbsolutePath();
//                    String antwoordenDatum = antwoordFile.getAbsolutePath();
//                    String[] vragenDatumList = vragenDatum.split("_");
//                    String[] antwoordenDatumList = antwoordenDatum.split("_");
//                    String[] vragenArray = Arrays.copyOfRange(vragenDatumList, 0, vragenDatumList.length - 1);
//                    String[] antwoordenArray = Arrays.copyOfRange(vragenDatumList, 0, antwoordenDatumList.length - 1);
//
//                    // als ze geldig zijn
//                    if (Arrays.equals(vragenArray, antwoordenArray) && vragenDatumList[
//                            vragenDatumList.length - 1].equals("Aatest.txt") && antwoordenDatumList[
//                            antwoordenDatumList.length - 1].equals("Aaant.txt")) {
//                        //System.out.println("vragen en antwoorden namen horen bij elkaar");
//                        vragenBestandNaam = vragenFile.getAbsolutePath();
//                        antwoordenBestandNaam = antwoordFile.getAbsolutePath();
//                        laatsteInvoer = "ingeladen";
//                    }
////                    else {
////                        // standaard files gebruien
////                        vragenAntwoordenMaken(20);
////                    }
//                }
////                else {
//                    // standaard files gebruiken
////                    vragenAntwoordenMaken(20);
////                }
//            }

            // als er een file mist dan niks invoeren.
            if(gebruikte_correcte_antwoorden==null || gebruikte_vragen==null) {
                gebruikte_correcte_antwoorden = null;
                gebruikte_vragen = null;
                gebruikte_antwoorden = null;
                gebruikte_figuren = null;
            } else if (!checkFiles()) {
                // als file namen of aantal vragen en antwoorden niet overeenkomen
                gebruikte_correcte_antwoorden = null;
                gebruikte_vragen = null;
                gebruikte_antwoorden = null;
                gebruikte_figuren = null;
            }

            beginScherm.start(primaryStage);
        });


        toetsScherm.openButton1.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser("Selecteer vragen", fileChooser);

                    vragenFile = fileChooser.showOpenDialog(primaryStage);
                    vragenIngevoerd = false;
                    //System.out.println("vragen file");
                    // todo. vragenFile geeft al error als er direct wordt getest of variabele een file is.
                    if (vragenFile == null) {
                        //System.out.println("vragenfile bestaat niet");
                        // niks doen
                    } else {
                        //System.out.println("vragenfile bestaat");
                        //System.out.println(vragenFile.getAbsolutePath().toString());
                        if(vragenFile.getAbsolutePath().toString().contains("Aatest")) {
                            //System.out.println("vragen ingevoerd");

                            String vragenPathTemp = vragenFile.getAbsolutePath();
//                            String antwoordenTemp = antwoordenBestandNaam = antwoordFile.getAbsolutePath();
//                            ImportExportVragenAntwoorden geimporteerd = new ImportExportVragenAntwoorden();
//                            int[][] vragenAntwoordenZinloos = geimporteerd.ImportVragenAntwoorden(vragenPathTemp, antwoordenTemp);
                            System.out.println(vragenPathTemp);

                            LeesVragenAntwoorden test2 = new LeesVragenAntwoorden();
                            test2.leesVragen(vragenPathTemp);
                            String[] vragen = test2.getVraag();
                            String[][] antwoorden = test2.getAntwoorden();
                            String[] figuren = test2.getFiguur();

                            String text = "";
                            int max = 15;
                            if (antwoorden.length < 15)
                                max = antwoorden.length;

                            for (int i = 0; i < max; i++) {
                                text+=" "+(i+1)+": "+vragen[i]+" # "+figuren[i]+" # "+Arrays.toString(antwoorden[i])+"\n";
                            }

                            toetsScherm.vragen_veld.setText(text);



                            gebruikte_vragen = vragen;
                            gebruikte_antwoorden = antwoorden;
                            gebruikte_figuren = figuren;


                            vragenIngevoerd = true;

                        } else {
                            vragenIngevoerd = false;
                            gebruikte_vragen = null;
                            gebruikte_antwoorden = null;
                            gebruikte_figuren = null;
                        }

                    }
                }
            });


        toetsScherm.openButton2.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser("Selecteer antwoorden", fileChooser);
                    antwoordFile = fileChooser.showOpenDialog(primaryStage);
                    antwoordenIngevoerd = false;
                    if (antwoordFile == null) {
                        //System.out.println("antwoordenfile bestaat niet");
                    } else {
                        //System.out.println("antwoordenfile bestaat");
                        //System.out.println(antwoordFile.getAbsolutePath().toString());
                        if(antwoordFile.getAbsolutePath().toString().contains("Aaant")) {
                            String antwoordenPathTemp = antwoordFile.getAbsolutePath();

                            LeesVragenAntwoorden test3 = new LeesVragenAntwoorden();
                            test3.leesAntwoorden(antwoordenPathTemp);
                            String[] antwoorden = test3.getCorrectAntwoord();

                            String text = "";
                            int max = 15;
                            if (antwoorden.length < 15)
                                max = antwoorden.length;

                            for (int i = 0; i < max; i++) {
                                text+=" "+(i+1)+": "+antwoorden[i]+"\n";
                            }

                            toetsScherm.antwoorden_veld.setText(text);

                            gebruikte_correcte_antwoorden = antwoorden;

                            antwoordenIngevoerd = true;
                            //System.out.println("antwoord ingevoerd");
                        } else {
                            antwoordenIngevoerd = false;
                            gebruikte_correcte_antwoorden = null;
                        }
                    }
                }
            });





        toetsScherm.maakVragen.setOnAction(e -> {
            // bestanden zie je pas in Intellij als je weer naar een ander scherm gaat.

            int aantal_vragen = toetsScherm.aantalVragen.getValue();
            String[] vragen_antwoorden_typen = {"volledige naam", "1-lettercode", "3-lettercode",
                    "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
            String[] schrijfbare_vragen = new String[aantal_vragen];
            String[] schrijfbare_antwoorden = new String[aantal_vragen];

            for (int i = 0; i < aantal_vragen; i++) {
                maakVraagSet = new MaakEenVraagAntwoord(vragen_antwoorden_typen, vragen_antwoorden_typen);
                schrijfbare_vragen[i] = maakVraagSet.getPrintableStringVraag();
                schrijfbare_antwoorden[i] = maakVraagSet.getPrintableStringAntwoord();
            }
            String eerste_woord = schrijfbare_vragen[0].split(" ")[0];
            schrijven.Schrijf(eerste_woord, schrijfbare_vragen, schrijfbare_antwoorden);

            vragenAntwoordenGemaakt = true;
            vragenAntwoordenHandmatigGemaakt = true;
            laatsteInvoer = "gemaakt";
        });



        speelScherm.startButton.setOnAction(e -> {
            //todo Hier er voor zorgen dat vragen opeens tevoorscein komen
            speelScherm.setPlayFieldVisibility(true);
            speelScherm.startButton.setVisible(false);
            // Als je een speeltijd hebt gekozen kan je niet meer een vraag terug.
            speelScherm.vorigeVraag.setVisible(!optieScherm.antwoordTijd.isVisible());
            System.out.println(huidigeVraag);
            speelScherm.setVragenAntwoorden(
                    gebruikte_vragen[0],
                    figureNameToPath(gebruikte_figuren[0]),
                    gebruikte_antwoorden[0]);

        });

        speelScherm.volgendeVraag.setOnAction(e -> {
            if(huidigeVraag<aantalVragen-1) {
                ++huidigeVraag;
                System.out.println(huidigeVraag);
                speelScherm.setVragenAntwoorden(
                        gebruikte_vragen[huidigeVraag],
                        figureNameToPath(gebruikte_figuren[huidigeVraag]),
                        gebruikte_antwoorden[huidigeVraag]);
            }
        });

        speelScherm.vorigeVraag.setOnAction(e -> {
            if(huidigeVraag>=1) {
                --huidigeVraag;
                System.out.println(huidigeVraag);
                speelScherm.setVragenAntwoorden(
                        gebruikte_vragen[huidigeVraag],
                        figureNameToPath(gebruikte_figuren[huidigeVraag]),
                        gebruikte_antwoorden[huidigeVraag]);
            }
        });



        speelScherm.cheatButton.setOnAction(e -> {
            eindScherm.spelerNaam.setText(optieScherm.naamSpeler.getText().toUpperCase());
            eindScherm.start(primaryStage);
            // alles weer op standaard voor het geval er weer een spel wordt gesart
            gebruikte_correcte_antwoorden = null;
            gebruikte_vragen = null;
            gebruikte_antwoorden = null;
            gebruikte_figuren = null;
        });
        speelScherm.stopButton.setOnAction(e -> {
            beginScherm.start(primaryStage);
            gebruikte_correcte_antwoorden = null;
            gebruikte_vragen = null;
            gebruikte_antwoorden = null;
            gebruikte_figuren = null;
        });

        eindScherm.afsluiten.setOnAction(e -> primaryStage.close());
        eindScherm.naarBeginSch.setOnAction(e -> {
            beginScherm.start(primaryStage);
            gebruikte_correcte_antwoorden = null;
            gebruikte_vragen = null;
            gebruikte_antwoorden = null;
            gebruikte_figuren = null;
        });

    }


    public boolean doorNaarSpeel() {
        String[] vragen = optieScherm.getActief("vragen");
        String[] antwoorden = optieScherm.getActief("antwoorden");

        boolean lengteCheck = vragen.length==0 || antwoorden.length==0;
        boolean eenGelijkCheck = (vragen.length==1&&antwoorden.length==1)&&(Arrays.equals(vragen,antwoorden));
        boolean naamNietIngevoerd = optieScherm.naamSpeler.getText().isEmpty();
        boolean volledig = Arrays.asList(vragen).contains("volledige naam");
        boolean code1 = Arrays.asList(vragen).contains("1-lettercode");
        boolean code3 = Arrays.asList(vragen).contains("3-lettercode");
        boolean minimaalEenTypeAminozuur = volledig || code1 || code3;

        System.out.println("VRAGEN");
        System.out.println(Arrays.toString(vragen));
        System.out.println("ANTWOORDEN");
        System.out.println(Arrays.toString(antwoorden));
        return !lengteCheck && !eenGelijkCheck && !naamNietIngevoerd && minimaalEenTypeAminozuur;
    }


    private void vragenAntwoordenMaken(int aantalVragen) {
        ImportExportVragenAntwoorden test = new ImportExportVragenAntwoorden();
        vrAnt = new VragenAntwoorden(aantalVragen);
        String eersteWoord = vrAnt.maakVraagAlsString(0).split(" ")[0];
        test.ExportVragenAntwoorden(eersteWoord, vrAnt.vragenAntwoorden);
        vragenBestandNaam = test.gemaakteVragenBestandPath;
        antwoordenBestandNaam = test.gemaakteVragenBestandPath;
//        System.out.println("######### 1 dit zou hij moeten maken: ");
//        System.out.println(vragenBestandNaam);
//        System.out.println(antwoordenBestandNaam);
//        System.out.println("######### 2 dit zou hij moeten maken: ");
    }


    private static void configureFileChooser(String text, final FileChooser fileChooser) {
        fileChooser.setTitle(text);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
    }

    private String figureNameToPath(String name) {
        char[] codes = new char[]{'G','A','V','L','I','M','P','F','Y','W','S','T','C','N','Q','K','R','H','D','E'};
        String path = "hide";

        if (name!=null) {
            int nummer = Integer.parseInt(name.replace("fig", ""));
            path = "aminos/"+codes[nummer]+".png";
        }

        System.out.println("!@#$% figuur naam: "+name);


        return path;
    }


    private boolean checkFiles(){
        boolean goede_uiteinden = false;
        boolean gelijke_lengte = false;
        boolean gelijke_datum = false;

        // datums moeten gelijk zijn en aantal vragen oeten ook gelijk zijn.
        String vragenDatum = vragenFile.getAbsolutePath();
        String antwoordenDatum = antwoordFile.getAbsolutePath();
        String[] vragenDatumList = vragenDatum.split("_");
        String[] antwoordenDatumList = antwoordenDatum.split("_");
        String[] vragenArray = Arrays.copyOfRange(vragenDatumList, 0, vragenDatumList.length - 1);
        String[] antwoordenArray = Arrays.copyOfRange(antwoordenDatumList, 0, antwoordenDatumList.length - 1);

        // als datums gelijk zijn en uiteinden kloppen
        if (Arrays.equals(vragenArray, antwoordenArray)) {
            //System.out.println("vragen en antwoorden namen horen bij elkaar");
            vragenBestandNaam = vragenFile.getAbsolutePath();
            antwoordenBestandNaam = antwoordFile.getAbsolutePath();
            gelijke_datum = true;
            System.out.println("datums zijn gelijk");
        } else {
            gelijke_datum = false;
            System.out.println("datums zijn niet gelijk");
        }

        // als files verkeerde uiteinden hebben
        if(vragenDatumList[vragenDatumList.length - 1].equals("Aatest.txt") && antwoordenDatumList[
                antwoordenDatumList.length - 1].equals("Aaant.txt")) {
            goede_uiteinden = true;
            System.out.println("uiteinden zijn goed");
        } else {
            goede_uiteinden = false;
            System.out.println("uiteinden zijn niet goed");
        }

        // als lijsten enven lang zijn
        if (gebruikte_correcte_antwoorden.length==gebruikte_vragen.length) {
            gelijke_lengte = true;
            System.out.println("lengten zijn gelijk");
        } else {
            System.out.println("lengten zijn niet gelijk");
            gelijke_lengte = false;
        }

        // laatsteInvoer = "ingeladen";
        return gelijke_lengte && goede_uiteinden && gelijke_datum;
    }
}
