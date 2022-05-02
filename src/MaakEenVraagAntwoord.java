import java.util.*;
import static java.util.Arrays.asList;

// done. Vragen maken.
// done. Juiste antwoord zoeken
// done. De foute antwoorden opzoeken
// done. vragen, antwoorden en goede antwoord goe oproepbaar maken


public class MaakEenVraagAntwoord {
    String[] vragen_typen = {"volledige naam", "1-lettercode", "3-lettercode",
            "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
    String[] antwoorden_typen = {"volledige naam", "1-lettercode", "3-lettercode",
            "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};;
    String[] antwoorden;
    String vraag = "";
    String correct_antwoord;
    String ingesteld_vraag_type;
    String ingesteld_vraag_woord;
    String ingesteld_antwoord_type;
    String vraag_plaatje;
    String[][] amino_table;
    boolean wel_niet_vraag_type; // "hydro, lading, grootte en 3d voorkeur" hebben een wel/niet/geen/een optie
    boolean ingesteld_wel_niet;


    // constructor
    MaakEenVraagAntwoord(String [] vragen_typen, String[] antwoorden_typen) {
        this.vragen_typen = vragen_typen;
        this.antwoorden_typen = antwoorden_typen;
        amino_table = getTabel();
        defineVraagTypeAntwoordType();
        bepaalVraagWoord();
        maakVraagZin();
        bepaalJuisteAntwoord();
        getAnswerChoises();
    }

    // resultaat
    public String getVraag() {
        return vraag;
    }

    // resultaat als vraagtype "zijketen" is.
    public String getVraagPlaatje() {
        return vraag_plaatje;
    }

    // resultaat
    public String[] getAntwoorden() {
        return antwoorden;
    }


    // resultaat
    public String getCorrectAntwoord() {
        return correct_antwoord;
    }

    // om naar een file te schrijven
    public String getPrintableStringVraag() {
        return vraag+"@"+vraag_plaatje+"#"+String.join("@",antwoorden);
    }


    public String getPrintableStringAntwoord() {
        return correct_antwoord;
    }


    public void defineVraagTypeAntwoordType() {
        // defineren variabelen
        int y = 0;
        int x = 0;
        boolean go_forth = true;

        // kopie maken van vragen typen
        String[] v_t = vragen_typen.clone();
        String[] a_t = antwoorden_typen.clone();

        // kopie shuffelen
        Collections.shuffle(asList(v_t));
        Collections.shuffle(asList(a_t));

        // random vraag type met bijpassend antwoord type krijgen
        while (y < v_t.length && go_forth) {
            String[] mogelijke_a_t = antwoordMogelijkBijVraag(v_t[y]);
            Collections.shuffle(asList(a_t));
            x = 0;
            while (x < a_t.length && go_forth) {
                if (Arrays.asList(mogelijke_a_t).contains(a_t[x])) {
                    ingesteld_vraag_type = v_t[y];
                    ingesteld_antwoord_type = a_t[x];
                    go_forth = false;
                }
                x++;
            }
            y++;
        }

        // bepalen of je een extra wel/niet/geen/een bij de vraag kan krijgen
        String[] true_vragen_typen = {"hydrofobiciteit", "lading", "grootte", "3d voorkeur"};
        wel_niet_vraag_type = Arrays.asList(true_vragen_typen).contains(ingesteld_vraag_type);
        if (wel_niet_vraag_type) {
            // random wel (true) of niet (false) boolean maken
            Random rand = new Random();
            ingesteld_wel_niet = rand.nextInt(2) % 2 == 0;

        }
    }


    private String[] antwoordMogelijkBijVraag(String vraag_type) {
        ArrayList<String> antwoorden = new ArrayList<>(asList("volledige naam",
                "1-lettercode", "3-lettercode", "hydrofobiciteit", "lading", "grootte",
                "structuur", "3d voorkeur"));

        antwoorden.remove(vraag_type);
        if (!vraag_type.equals("volledige naam") && !vraag_type.equals("1-lettercode") &&
                !vraag_type.equals("3-lettercode") && !vraag_type.equals("structuur")) {
            antwoorden.remove("hydrofobiciteit");
            antwoorden.remove("lading");
            antwoorden.remove("grootte");
            antwoorden.remove("3d voorkeur");
        }

        String[] antwoorden_return = new String[antwoorden.size()];
        antwoorden_return = antwoorden.toArray(antwoorden_return);

        return antwoorden_return;
    }


    private void bepaalVraagWoord() {
        String[] opties = new String[1];
        switch (ingesteld_vraag_type) {
            case "volledige naam":
                opties = new String[]{"Glycine", "Alanine", "Valine", "Leucine", "Isoleucine", "Methionine", "Proline",
                        "Phenylalanine", "Tyrosine", "Tryptophan", "Serine" , "Threonine", "Cysteine", "Asparagine",
                        "Glutamine", "Lysine", "Arginine", "Histidine", "Aspartic acid", "Glutamic acid"};
                break;
                case "1-lettercode":
                    opties = new String[]{"G","A","V","L","I","M","P","F","Y","W","S",
                            "T","C","N","Q","K","R","H","D","E"};
                break;
            case "3-lettercode":
                opties = new String[]{"Gly","Ala","Val","Leu","Ile","Met","Pro","Phe","Tyr","Trp","Ser","Thr",
                        "Cys","Asn","Gln","Lys","Arg","His","Asp","Glu"};
                break;
            case "hydrofobiciteit":
                opties = new String[]{"neutraal","hydrofoob","neutraal","hydrofiel"};
                break;
            case "lading":
                opties = new String[]{"neutraal","positief","negatief"};
                break;
            case "grootte":
                opties = new String[]{"klein","middelgroot","groot"};
                break;
            case "structuur":
                opties = new String[]{"fig0","fig1","fig2","fig3","fig4","fig5","fig6","fig7","fig8","fig9",
                        "fig10","fig11","fig12","fig13","fig14","fig15","fig16","fig17","fig18","fig19"};
                break;
            case "3d voorkeur":
                opties = new String[]{"turn","helix","sheet","geen"};
                break;

            default:
                System.out.println();
                System.out.println("verkeeerde switch statement!!!!");
                System.out.println("#####"+ingesteld_vraag_type+"####");
                System.out.println();
                break;
        }
        Random rand = new Random();
        ingesteld_vraag_woord = opties[rand.nextInt(opties.length)];
    }

    // in een matrix van alle aminozuur data moet de juiste kolom (typen data)  worden bepaald, en dan de juiste rij (het aminozuur).
    private void bepaalJuisteAntwoord() {
        // todo. Dit werkt als je zoekt op wel neutraal, een lading, etc. maar werkt nog niet als als de vraag is welk .. is niet netraal, geen helix etc.
        // DONE >> Je moet voor elke instantie, bij initialisatie van de klasse de aminozuur tabel door elkaar gooien (1 keer) om er voor te zorgen dat je niet steeds dezelfde antwoorden krijgt
        // todo. door deze nieuwe toevoeging gan de "wel" vragen ook niet meer goed..
        if (wel_niet_vraag_type && !ingesteld_wel_niet) {
            // Dit stuk is voor het geval er een vraag is zoals "Welk aminozuur is NIET middelgroot?"

            // kolom van vraag type ophalen
            int col_index = findColumnIndex(ingesteld_vraag_type);

            // een rij index zoeken die ongelijk is aan vraag type woord
            int row_index = findDifferentRowIndex(col_index, ingesteld_vraag_woord);

            //
            col_index = findColumnIndex(ingesteld_antwoord_type);

            // antwoord verkrijgen
            correct_antwoord = amino_table[row_index][col_index];
        } else {
            // Dit stuk is voor het geval er een vraag is zoals "Welk aminozuur is WEL middelgroot?"

            // kolom index vinden voor vraag, om rijindex te bepalen
            int col_index = findColumnIndex(ingesteld_vraag_type);

            // rij index vinden voor antwoord
            int row_index = findRowIndex(col_index, ingesteld_vraag_woord);

            // col index vinden voor antwoord
            col_index = findColumnIndex(ingesteld_antwoord_type);

            // antwoord verkrijgen
            correct_antwoord = amino_table[row_index][col_index];
        }
    }


    private int findColumnIndex(String type) {
        String[] volgorde = new String[]{"volledige naam", "1-lettercode", "3-lettercode",
                "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
        int index = 10000;
        for (int i = 0; i < volgorde.length; i++) {
            if (type.equals(volgorde[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int findRowIndex(int column_index, String vraag_woord) {
        int index = 1000;
        // door alle rijen loopen
        for (int i = 0; i < amino_table.length; i++) {
            if (vraag_woord.equals(amino_table[i][column_index])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int findDifferentRowIndex(int column_index, String vraag_woord) {
        int index = 1000;
        // door alle rijen loopen
        for (int i = 0; i < amino_table.length; i++) {
            if (!vraag_woord.equals(amino_table[i][column_index])) {
                index = i;
                break;
            }
        }
        return index;
    }


    // Zet de verkregen variabelen om in een vraag
    private void maakVraagZin() {
        if (ingesteld_vraag_type.equals("volledige naam") || ingesteld_vraag_type.equals("1-lettercode") ||
                ingesteld_vraag_type.equals("3-lettercode") || ingesteld_vraag_type.equals("structuur")) {
            if (ingesteld_vraag_type.equals("structuur")) {
                vraag = "Wat is de " + ingesteld_antwoord_type + " van:";
                vraag_plaatje = ingesteld_vraag_woord;
            } else {
                vraag = "Wat is de " + ingesteld_antwoord_type + " van " + ingesteld_vraag_woord + "?";
            }

        } else if (ingesteld_vraag_type.equals("hydrofobiciteit") || ingesteld_vraag_type.equals("lading") || ingesteld_vraag_type.equals("grootte")) {
            String voorvoegsel = "<><><><><><><><>";
            if(ingesteld_wel_niet) {
                voorvoegsel = "wel";
            } else {
                voorvoegsel = "niet";
            }
            vraag = "Welk aminozuur is "+voorvoegsel+" "+ingesteld_vraag_woord+"?";
        } else if (ingesteld_vraag_type.equals("3d voorkeur")) {
            String voorvoegsel = "<><><><><><><><>";
            if(ingesteld_wel_niet) {
                voorvoegsel = "een";
            } else {
                voorvoegsel = "geen";
            }
            if (ingesteld_vraag_woord.equals("geen")) {
                vraag = "Welk aminozuur heeft "+voorvoegsel+" voorkeur voor geen 3d sctructuur";
            } else {
                vraag = "Welk aminozuur heeft " + voorvoegsel + " voorkeur voor een " + ingesteld_vraag_woord + "?";
            }
        } else {
            System.out.println("verkeerd ingesteld vraag type!!");
        }
    }


    private String[][] getTabel() {
        // opdracht heeft Selenocysteine (Sec/U) niet in de tabel!!!!
        String[][] amino = {
                {"Glycine"      ,"G","Gly","neutraal" ,"neutraal","klein" ,"fig0" ,"turn"},
                {"Alanine"      ,"A","Ala","hydrofoob","neutraal","klein" ,"fig1" ,"helix"},
                {"Valine"       ,"V","Val","hydrofoob","neutraal","klein" ,"fig2" ,"sheet"},
                {"Leucine"      ,"L","Leu","hydrofoob","neutraal","middelgroot","fig3" ,"helix"},
                {"Isoleucine"   ,"I","Ile","hydrofoob","neutraal","middelgroot","fig4" ,"sheet"},
                {"Methionine"   ,"M","Met","hydrofoob","neutraal","groot" ,"fig5" ,"helix"},
                {"Proline"      ,"P","Pro","hydrofoob","neutraal","klein" ,"fig6" ,"turn"},
                {"Phenylalanine","F","Phe","hydrofoob","neutraal","groot" ,"fig7" ,"sheet"},
                {"Tyrosine"     ,"Y","Tyr","hydrofoob","neutraal","groot" ,"fig8" ,"sheet"},
                {"Tryptophan"   ,"W","Trp","hydrofoob","neutraal","groot" ,"fig9" ,"sheet"},
                {"Serine"       ,"S","Ser","neutraal" ,"neutraal","klein" ,"fig10","turn"},
                {"Threonine"    ,"T","Thr","neutraal" ,"neutraal","klein" ,"fig11","sheet"},
                {"Cysteine"     ,"C","Cys","neutraal" ,"neutraal","middelgroot","fig12","sheet"},
                {"Asparagine"   ,"N","Asn","hydrofiel","neutraal","middelgroot","fig13","turn"},
                {"Glutamine"    ,"Q","Gln","hydrofiel","neutraal","groot" ,"fig14","geen"},
                {"Lysine"       ,"K","Lys","hydrofiel","positief","groot" ,"fig15","helix"},
                {"Arginine"     ,"R","Arg","hydrofiel","positief","groot" ,"fig16","geen"},
                {"Histidine"    ,"H","His","hydrofiel","positief","groot" ,"fig17","geen"},
                {"Aspartic acid","D","Asp","hydrofiel","negatief","middelgroot","fig18","helix"},
                {"Glutamic acid","E","Glu","hydrofiel","negatief","groot" ,"fig19","helix"}};

        // aminozuren mixen
        Collections.shuffle(asList(amino));

        return amino;
    }


    private void getAnswerChoises() {
        int type = bepaalTypeAntwoord();
        if (type!=2 ) {
            // hier moet je echt moeite gaan doen om de antwoorden te kiezen
            // het zijn wel altijd 3 antwoorden, dus je hoeft maar 2 foute antowoorden te vinden
            String[] two_answers = getTwoWrongAnswers(type);
            String[] three_answers = new String[]{two_answers[0], two_answers[1], correct_antwoord};
            Collections.shuffle(asList(three_answers));
            antwoorden = three_answers;

            // todo. als alles werkt goede antwoord nog op random plek van de 3 zetten
        } else {
            switch(ingesteld_antwoord_type) {
                case "lading":
                    antwoorden = new String[]{"positief", "negatief", "neutraal"};
                    break;
                case "hydrofobiciteit":
                    antwoorden = new String[]{"hydrofiel", "neutraal", "hydrofoob"};
                    break;
                case "grootte":
                    antwoorden = new String[]{"groot", "middelgroot", "klein"};
                    break;
                case "3d voorkeur":
                    antwoorden = new String[]{"helix", "turn", "sheet", "geen"};
                    break;
            }
        }
    }


    private String[] getTwoWrongAnswers(int type_vraag) {
        // kolom zoeken met de juiste soort antwoorden
        int col_index = findColumnIndex(ingesteld_antwoord_type);

        // alle waarden in de kolom ophalen
        String[] all_answers = new String[amino_table.length];
        for (int i = 0; i < amino_table.length; i++) {
            all_answers[i] = amino_table[i][col_index];
        }

        String[] wrong_answers = new String[2];

        if (type_vraag==3 || type_vraag==4) {
            if (ingesteld_wel_niet) {
                // "wel" of "een" vraag type
                // alles mogelijk wat ongelijk is aan het antwoord
                // indexen verkrijgen van alles dat ongelijk is aan lading/hydro/grootte verkrijgen van het antwoord

                int column_index = findColumnIndex(ingesteld_vraag_type);
                int teller = 0;
                for (int i = 0; i < amino_table.length; i++) {
                    if (teller >= 2) {
                        break;
                    }
                    if (!amino_table[i][column_index].equals(ingesteld_vraag_woord)) {
                        int col_index_ant = findColumnIndex(ingesteld_antwoord_type);
                        wrong_answers[teller] = amino_table[i][col_index_ant];
                        ++teller;
                    }
                }
            } else {
                // "niet" of "geen" vraag type
                // alles mogelijk wat gelijk is aan het antwoord als er geen "niet/geen" in de vraag zou zitten
                int column_index = findColumnIndex(ingesteld_vraag_type);
                int teller = 0;
                for (int i = 0; i < amino_table.length; i++) {
                    if (teller >= 2) {
                        break;
                    }
                    if (amino_table[i][column_index].equals(ingesteld_vraag_woord)) {
                        int col_index_ant = findColumnIndex(ingesteld_antwoord_type);
                        wrong_answers[teller] = amino_table[i][col_index_ant];
                        ++teller;
                    }
                }
            }
        } else {
            // type 1 vraag
            int teller = 0;
            for (int i = 0; i < all_answers.length; i++) {
                if (!all_answers[i].equals(correct_antwoord)) {
                    wrong_answers[teller] = all_answers[i];
                    ++teller;
                }
                if (teller >= 2) {
                    break;
                }
            }
        }
        return wrong_answers;
    }


    public int bepaalTypeAntwoord() {
        int type_antwoord = 100;

        Set<String> type134AntwoordenENtype12Vragen = new HashSet<String>(Arrays.asList(new String[]{"volledige naam", "1-lettercode", "3-lettercode", "structuur"}));
        Set<String> type2Antwoorden = new HashSet<String>(Arrays.asList(new String[]{"hydrofobiciteit", "lading", "grootte", "3d voorkeur"}));
        Set<String> type3Vragen = new HashSet<String>(Arrays.asList(new String[]{"hydrofobiciteit", "lading", "grootte"}));
        String type4Vragen = "3d voorkeur";

        if(type134AntwoordenENtype12Vragen.contains(ingesteld_antwoord_type) && type134AntwoordenENtype12Vragen.contains(ingesteld_vraag_type)) {
            // voor vragen en antwoorden type134AntwoordenENtype12Vragen voor type 1
            type_antwoord = 1;
        } else if (type2Antwoorden.contains(ingesteld_antwoord_type)) {
            // type2Antwoorden, type vragen heb je dan automatisch al
            type_antwoord = 2;

        } else if (type3Vragen.contains(ingesteld_vraag_type)) {
            // type3Vragen, type antwoorden heb je dan automatisch al
            type_antwoord = 3;
        } else if (type4Vragen.equals(ingesteld_vraag_type)) {
            // type4Vragen, type antwoorden heb je dan automatisch al
            type_antwoord = 4;
        }

        return type_antwoord;
    }


    public static void main(String[] args) {
//        String[] vragen_typen = {"volledige naam", "1-lettercode", "3-lettercode",
//                "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
//        String[] antwoorden_typen = {"volledige naam", "1-lettercode", "3-lettercode",
//                "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};

        String[] vragen_typen = {"volledige naam", "3-lettercode",
                "hydrofobiciteit", "lading", "grootte", "structuur"};
        String[] antwoorden_typen = {"1-lettercode", "3-lettercode",
                "grootte", "3d voorkeur"};


        for (int i = 0; i < 100; i++) {
//            MaakEenVraagAntwoord test = new MaakEenVraagAntwoord(vragen_typen, antwoorden_typen);
//            if (test.ingesteld_antwoord_type.equals("volledige naam") || test.ingesteld_antwoord_type.equals("1-lettercode") ||
//                    test.ingesteld_antwoord_type.equals("3-lettercode") || test.ingesteld_antwoord_type.equals("structuur")) {
//                System.out.println(test.vraag+"\t\t\t\t"+test.correct_antwoord);
//                System.out.println("###########################################################################");
//            }
            MaakEenVraagAntwoord test = new MaakEenVraagAntwoord(vragen_typen, antwoorden_typen);
            System.out.println(test.getVraag()+"\t\t"+Arrays.toString(test.getAntwoorden())+"\t\t"+test.getCorrectAntwoord());
            System.out.println("###########################################################################");


        }
    }



}
