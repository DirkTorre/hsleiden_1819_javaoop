import java.util.*;

public class VragenAntwoorden {
    String[] titels = {"volledige naam", "1-lettercode", "3-lettercode",
            "hydrofobiciteit", "lading", "grootte", "structuur", "3d voorkeur"};
    String[][] aminoData = getTabel();
    ArrayList<int[]> possibleQuestions;
    String[] vragenTypen;
    String[] antwoordenTypen;
    int aantalVragen;
    int[][] vragenAntwoorden;
    Random random = new Random();

    // Constructor
    VragenAntwoorden(String[] vragenTypen, String[] antwoordenTypen, int aantalVragen) {
        // voor het maken van een bepaald aantal vragen in het opteischerm
        // 0) globale variabelen vaststellen
        this.vragenTypen = vragenTypen;
        this.antwoordenTypen = antwoordenTypen;
        this.aantalVragen = aantalVragen;

        // 1) indexen verkrijgen van vragen kolommen en antwoorden kolommen
        int[] vragenKolomIndices = getTableColumnIndices(this.vragenTypen);

        int[] temp = new int[10];
        int teller = 0;

        // todo De twee loops hieronder geven een error (index mag niet -1 zijn?)
        // Ik denk dat je hier de vraagkolom indexen eruit haalt die problemen kunnen geven.
        for (int i = 0; i < vragenKolomIndices.length; i++) {
            if(vragenKolomIndices[i]!=3&&vragenKolomIndices[i]!=4&&vragenKolomIndices[i]!=5&&vragenKolomIndices[i]!=7) {
                temp[teller++] = vragenKolomIndices[i];
            }
        }

        // todo Als de teller 0 is geeft hij een error (logiscg, want je kan niet een array hebben met grootte -1)
        // in oude code had teller een waarde van --teller
        // Maar ik denk dat dat niet nodig is, omdat de teller nu altijd goed is.
        // ik denk dat de bedoeling was om een lijstje te maken van cijfers waar 3, 4, 5 en 7 niet in zitten
        vragenKolomIndices = new int[teller];
        for (int i = 0; i < teller; i++) {
            vragenKolomIndices[i] = temp[i];
        }
        int[] antwoordenKolomIndices = getTableColumnIndices(this.antwoordenTypen);

        // 2) vragen maken
        makeQuestions(true, new int[]{0,1,2,3,4,5,6,7}, new int[]{0,1,2,3,4,5,6,7});
    }


    // 2e Constructor
    VragenAntwoorden(int aantalVragen) {
        // voor het maken van een bepaald aantal vragen in het toetsscherm
        // 0) globale variabelen vaststellen
        this.aantalVragen = aantalVragen;

        // 2) vragen maken
        makeQuestions(false, new int[]{0,1,2,3,4,5,6,7}, new int[]{0,1,2,3,4,5,6,7});
    }


    // Ophalen nummers van kolommen
    private int[] getTableColumnIndices(String[] columnNames) {
        int[] columnIndices = new int[columnNames.length];
        for(int x=0; x<columnNames.length; x++) {
            columnIndices[x] = Arrays.asList(titels).indexOf(columnNames[x]);
        }
        return columnIndices;
    }


    // tabel met info maken
    private String[][] getTabel() {
        // deze mist nog 1 aminozuur
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
        return amino;
    }

    // volgens mij gebruk in dit helemaal niet...
    // deze matrix kan worden gebruikt om te kijken in optieschem of selectie wel mogelijk is
    // moet nog wel rekeing houden met dat 1 vraag selectie kan gelden voor meerde antwoord selecties en andersom.
    public boolean[][] getPossibleMatrix() {
        //boolean[antwoord][vraag]
        boolean[][] matrix = new boolean[8][8]; // alle items zijn standaard false
        for(int antwoord=0; antwoord<8; antwoord++){
            for(int vraag=0; vraag<8; vraag++){
                if(!((vraag==0&&antwoord==0||vraag==1&&antwoord==1||vraag==2&&antwoord==2)||((antwoord>2&&antwoord!=7)&&(vraag>2&&vraag!=7)))){
                    matrix[antwoord][vraag]=true;
                }
            }
        }
        return matrix;
    }


    // maakt vragen
    private void makeQuestions(boolean filter, int[] vragen, int[] antwoorden) {
        vragenAntwoorden = new int[aantalVragen][];
        int[][] mogelijkheden = getPossibleColumns(filter, vragen, antwoorden);
        int vraagIndex = 0;

        //boolean type1Mogelijk = checkMinimalQuestionPosibility(mogelijkheden[0], mogelijkheden[1], 1);
        //boolean type2Mogelijk = checkMinimalQuestionPosibility(mogelijkheden[2], mogelijkheden[3], 1);


        while (vraagIndex<aantalVragen) {
            // er zijn meer type 1 vragen (dacht ik), daarom moeten er dus ook meer van worden gemaakt.
            if(random.nextInt(5)<3){
                // als de vragen en of antwoorden leeg zijn voor het type vraag dan wordt hij niet gemaakt

                if(mogelijkheden[0].length!=0&&mogelijkheden[1].length!=0) {
                    makeType1Question(vraagIndex, mogelijkheden[0], mogelijkheden[1]);
                    vraagIndex++;
                } else {
//                    System.out.println("geen goede type1");
                }
            } else{
                if(mogelijkheden[2].length!=0&&mogelijkheden[3].length!=0) {
                    //vragenAntwoorden[vraagIndex] = new int[]{666,666,666,666,666,666};
                    makeType2Question(vraagIndex, mogelijkheden[2], mogelijkheden[3]);
                    vraagIndex++;
                } else {
                    System.out.println("geen goede type2");
                }
            }
        }
    }


    private int[][] getPossibleColumns(boolean filter, int[] vragen, int[] antwoorden){
        int[][] mogelijkheden = new int[4][];

        if(!filter) {
            // structuren mogen niet als de gebruiker
            // handmatig de vragen maakt (=zijketen=index6)
            // dus geen 6 als er geen filter is
            mogelijkheden[0] = new int[]{0, 1, 2, 3, 4, 5, 7}; // type1antwoord
            mogelijkheden[1] = new int[]{0, 1, 2}; // type1vraag

            mogelijkheden[2] = new int[]{0, 1, 2}; // type2antwoord
            mogelijkheden[3] = new int[]{3, 4, 5, 7}; // type2vraag
        } else {
            // union verkrijgen
            mogelijkheden[0] = getIntersect(antwoorden, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
            mogelijkheden[1] = getIntersect(vragen, new int[]{0, 1, 2, 6});

            mogelijkheden[2] = getIntersect(antwoorden, new int[]{0, 1, 2, 6});
            mogelijkheden[3] = getIntersect(vragen, new int[]{3, 4, 5, 7});
        }

        return mogelijkheden;
    }


    private void makeType1Question(int vraagIndex, int[] antwoorden, int[] vragen){
        int rAKI;//randomAntwoordKolomIndex;
        int rVKI;//randomVraagKolomIndex;
        int rGRI;//randomGoedRijIndex;
        int rFRI1;//randomFoutRijIndex1;
        int rFRI2;//randomFoutRijIndex2;

        // random Antwoord Kolom Index halen uit de indexen van de opgegeven antwoorden.
        rAKI = antwoorden[random.nextInt(antwoorden.length)];
        // random Vraag Kolom Index halen uit de indexen van de opgegeven vragen
        // (en mag niet gelijk zijn aan random Antwoord Kolom Index).
        rVKI = rAKI;
        while(rVKI==rAKI){
            rVKI = vragen[random.nextInt(vragen.length)];
        }
        // random rij ophalen voor goede antwoord
        rGRI = random.nextInt(aminoData.length);

        // Bij index 0,1,2,6 zijn er dan kan de de speler niet kiezen uit standaard antwoorden
        // (0,1,2,6=volledige naam, 1-letter code, 3-letter code, zijketen).
        if(rAKI==0||rAKI==1||rAKI==2||rAKI==6){
            rFRI1=rGRI;
            while(rFRI1==rGRI){
                rFRI1=random.nextInt(aminoData.length);
            }
            rFRI2=rGRI;
            while(rFRI2==rGRI||rFRI2==rFRI1){
                rFRI2=random.nextInt(aminoData.length);
            }


            List<Integer> shuffleAntwoorden = new ArrayList<>();
            shuffleAntwoorden.add(rGRI);
            shuffleAntwoorden.add(rFRI1);
            shuffleAntwoorden.add(rFRI2);
            Collections.shuffle(shuffleAntwoorden);

            int[] shuffled = new int[]{shuffleAntwoorden.get(0), shuffleAntwoorden.get(1), shuffleAntwoorden.get(2)};

            vragenAntwoorden[vraagIndex] = new int[]{1, rAKI, rVKI, rGRI, shuffled[0], shuffled[1], shuffled[2]};
        } else{
            // Omdat alle keuze antwoorden op dit soort vraag altijd hetzelfde zijn,
            // hoef je dus geen foute antwoorden te zoeken.
            // we nog even de goede antwoord index uitzoeken.
            int goedeStandaardAntwoord = goedeAntwoordVindenType1StandaardAntwoorden(rAKI, rGRI);
            vragenAntwoorden[vraagIndex] = new int[]{1, rAKI, rVKI, rGRI, goedeStandaardAntwoord};

        }
    }


    private String[] getDefaultAnswers(int antwoordKolom){
        String[] antwoord = new String[]{};
        if(antwoordKolom==3) {
            antwoord = new String[]{"hydrofiel", "neutraal", "hydrofoob"};
        } else if(antwoordKolom==4) {
            antwoord = new String[]{"positief", "negatief", "neutraal"};
        } else if(antwoordKolom==5) {
            antwoord = new String[]{"groot", "middelgroot", "klein"};
        } else if(antwoordKolom==7) {
            antwoord = new String[]{"helix", "turn", "sheet", "geen"};
        }
        return antwoord;
    }


    private int goedeAntwoordVindenType1StandaardAntwoorden(int kolom, int rij) {
        int goedeAntwoordInt = 1000;
        String goedeAntwoord = aminoData[rij][kolom];
        String[] antwoorden = getDefaultAnswers(kolom);
        for (int x=0; x<antwoorden.length; x++) {
            if(antwoorden[x].equals(goedeAntwoord)){
                goedeAntwoordInt=x;
            }
        }
        return goedeAntwoordInt;
    }


    private void makeType2Question(int vraagIndex, int[] antwoorden, int[] vragen) {
        int rWNI; // random Wel Niet Index
        int rVKI; // random Vraag Kolom Index rWNI,
        int rAKI; // random Antwoord Kolom Index;
        int rGRI; // randomGoedRijIndex;

        rWNI = random.nextInt(2);
        rVKI = vragen[random.nextInt(vragen.length)];
        rAKI = antwoorden[random.nextInt(antwoorden.length)];
        rGRI = random.nextInt(aminoData.length);

        vragenAntwoorden[vraagIndex] = new int[]{2, rVKI, rAKI,  rWNI, rGRI};
        int[] gemaakteAntwoorden = makeType2Answer(vraagIndex);

        int[] array1and2 = new int[vragenAntwoorden[vraagIndex].length + gemaakteAntwoorden.length];
//        System.arraycopy(vragenAntwoorden[vraagIndex], 0, array1and2, 0, vragenAntwoorden[vraagIndex].length);
//        System.arraycopy(gemaakteAntwoorden, 0, array1and2, vragenAntwoorden[vraagIndex].length, gemaakteAntwoorden.length);
        vragenAntwoorden[vraagIndex] = new int[array1and2.length];
        vragenAntwoorden[vraagIndex] = array1and2;

        // {2, rVKI, rAKI,  rWNI, rGRI, ECHTE GOEDE RIJ INDEX, 3 * random rij indexen incl. goede};
        // PS: bij een "niet" vraag is de random rij met het goede antwoord, het foute antwoord.
        // De oude "goede rij" (rGRI) moet worden gebruikt voor het maken van de vraag.
        // Maar de ECHTE GOEDE RIJ INDEX moet worden gebruikt om het antwoord te checken
    }


    private int[] makeType2Answer(int vraagIndex) {
        ArrayList<Integer> welOvereenkomst = new ArrayList<>(); // indexen gelijk aan waarde van de vraag
        ArrayList<Integer> geenOvereenkomst = new ArrayList<>(); // indexen ongelijk aan waarde van de vraag
//        System.out.println(vragenAntwoorden[vraagIndex][4]);
//        System.out.println(vragenAntwoorden[vraagIndex][1]);
        String waardeVraag = aminoData[vragenAntwoorden[vraagIndex][4]][vragenAntwoorden[vraagIndex][1]];

        // {2, rVKI, rAKI,  rWNI, rGRI, ECHTE GOEDE RIJ INDEX, 3 * random rij indexen incl. goede};
        // PS: bij een "niet" vraag is de random rij met het goede antwoord, het foute antwoord.
        // De oude "goede rij" (rGRI) moet worden gebruikt voor het maken van de vraag.
        // Maar de ECHTE GOEDE RIJ INDEX moet worden gebruikt om het antwoord te checken

        boolean overeenkomst = false;
        if(vragenAntwoorden[vraagIndex][3]==0) {
            overeenkomst = true;
        }

//        System.out.println("waardeVraag: "+waardeVraag+" "+vragenAntwoorden[vraagIndex][1]);

        for (int x = 0; x < 20; x++) {
            if(aminoData[x][vragenAntwoorden[vraagIndex][1]].equals(waardeVraag)) {
                welOvereenkomst.add(x);
//                System.out.println("welOvereenkomst: "+x+" "+aminoData[x][vragenAntwoorden[vraagIndex][1]]);
            } else {
                geenOvereenkomst.add(x);
//                System.out.println("geenOvereenkomst: "+x+" "+aminoData[x][vragenAntwoorden[vraagIndex][1]]);
            }
        }

        // bepalen welke van de twee arraylists je de foute antwoorden moet halen
        int[] fouteIndexen = new int[3];
        ArrayList<Integer> goedeOvereenkomst = new ArrayList<>();
        // todo. Het onderstaande slaat nergens op, maar ik laat het even zo, voordat ik nog meer bugs krijg
        if(overeenkomst) {
            goedeOvereenkomst = new ArrayList(geenOvereenkomst);
        } else {
            goedeOvereenkomst = new ArrayList(welOvereenkomst);
        }

        // goede antwoord uit overeenkomst halen
        int juisteAntwoordRij;
        if(overeenkomst) {
            goedeOvereenkomst.remove(Integer.valueOf(vragenAntwoorden[vraagIndex][4]));
            juisteAntwoordRij = vragenAntwoorden[vraagIndex][4];
        } else {
            // als er geen overeenkomst moet zijn dan, moet er een goed antwoord worden
            // gehaald uit de "geen" overeenkomst.
            juisteAntwoordRij = geenOvereenkomst.get(random.nextInt(geenOvereenkomst.size()));
        }


        // 2 fouten uit overeenkomst halen (type 2 vragen altijd in totaal 3 antwoorden)
        fouteIndexen[0] = goedeOvereenkomst.get(random.nextInt(goedeOvereenkomst.size()));
//        System.out.println("fucking test 4");
        goedeOvereenkomst.remove(Integer.valueOf(fouteIndexen[0]));
//        System.out.println("fucking test 5");
        fouteIndexen[1] = goedeOvereenkomst.get(random.nextInt(goedeOvereenkomst.size()));
//        System.out.println("fucking test 6");

        List<Integer> shuffleAntwoorden = new ArrayList<>();
        shuffleAntwoorden.add(juisteAntwoordRij);
        shuffleAntwoorden.add(fouteIndexen[0]);
        shuffleAntwoorden.add(fouteIndexen[1]);
        Collections.shuffle(shuffleAntwoorden);

        // ttt = random antwoord uit de 3 goede en foute antwoorden
        // {goede antwoord, ttt, ttt, ttt}
//        int[] antwoorden = new int[]{vragenAntwoorden[vraagIndex][4], shuffleAntwoorden.get(0),
//                shuffleAntwoorden.get(1), shuffleAntwoorden.get(2)};
        int[] antwoorden = new int[]{shuffleAntwoorden.get(0), shuffleAntwoorden.get(1), shuffleAntwoorden.get(2)};

//        System.out.println("goede = "+aminoData[juisteAntwoordRij][vragenAntwoorden[vraagIndex][1]]);
//        System.out.println("ding1 = "+aminoData[antwoorden[0]][vragenAntwoorden[vraagIndex][1]]);
//        System.out.println("ding2 = "+aminoData[antwoorden[1]][vragenAntwoorden[vraagIndex][1]]);
//        System.out.println("ding3 = "+aminoData[antwoorden[2]][vragenAntwoorden[vraagIndex][1]]);

        return new int[]{juisteAntwoordRij, antwoorden[0], antwoorden[1], antwoorden[2]};
    }



    public static int[] getIntersect(int[] array1, int[] array2) {
        // werkt alleen als er geen duplicaten zijn in array
        int grootste = array1.length;
        if(array2.length>grootste){
            grootste=array2.length;
        }
        int[] intersectTemp = new int[grootste];
        int aantal = 0;

        for (int y = 0; y < array1.length; y++) {
            for (int x = 0; x < array2.length; x++) {
                if(array1[y]==array2[x]){
                    intersectTemp[aantal++] = array1[y];
                }
            }
        }

        int[] intersect = new int[aantal];

        for (int x = 0; x < aantal; x++) {
            intersect[x] = intersectTemp[x];
        }

        return intersect;
    }


    public String maakVraagAlsString(int vraagIndex){
        String vraag = "";
        if(vragenAntwoorden[vraagIndex][0]==1) {
            vraag = "Wat is de "+titels[vragenAntwoorden[vraagIndex][1]]+" van ";
            int kiv = vragenAntwoorden[vraagIndex][2]; // kolom index vraag
            int gri = vragenAntwoorden[vraagIndex][3]; // goede rij antwoord
            if(kiv==1||kiv==2){
                vraag += "aminozuur "+aminoData[gri][kiv] + "?";
            } else if(kiv==6) {
                vraag += "van het aminozuur met de volgende structuur?"+aminoData[gri][kiv];
            } else {
                vraag += aminoData[gri][kiv] + "?";
            }
        } else {
            vraag = "Welk aminozuur";
            if(vragenAntwoorden[vraagIndex][1]==7){
                if(vragenAntwoorden[vraagIndex][3]==0){
                    vraag += " heeft een ";
                } else {
                    vraag += " heeft geen ";
                }
            } else {
                if(vragenAntwoorden[vraagIndex][3]==1){
                    vraag += " is niet ";
                } else {
                    vraag += " is ";
                }
            }
            int kiv = vragenAntwoorden[vraagIndex][1]; // kolom index vraag
            int gri = vragenAntwoorden[vraagIndex][4]; // goede rij antwoord
            vraag += aminoData[gri][kiv] + "?";
            // {2, rVKI, rAKI,  rWNI, rGRI, ECHTE GOEDE RIJ INDEX, 3 * random rij indexen incl. goede};
            // PS: bij een "niet" vraag is de random rij met het goede antwoord, het foute antwoord.
            // De oude "goede rij" (rGRI) moet worden gebruikt voor het maken van de vraag.
            // Maar de ECHTE GOEDE RIJ INDEX moet worden gebruikt om het antwoord te checken
        }

        return vraag;
    }


    public String[] maakStringAntwoorden(int vraagIndex) {
        String[] antwoorden = new String[]{"","","",""};
        if(vragenAntwoorden[vraagIndex][0]==2) {
            // tabel gebruiken
            antwoorden[0] = aminoData[vragenAntwoorden[vraagIndex][6]][vragenAntwoorden[vraagIndex][2]];
            antwoorden[1] = aminoData[vragenAntwoorden[vraagIndex][7]][vragenAntwoorden[vraagIndex][2]];
            antwoorden[2] = aminoData[vragenAntwoorden[vraagIndex][8]][vragenAntwoorden[vraagIndex][2]];
            if(vragenAntwoorden[vraagIndex].length==10) {
                antwoorden[3] = aminoData[vragenAntwoorden[vraagIndex][9]][vragenAntwoorden[vraagIndex][2]];
            }
        } else if (vragenAntwoorden[vraagIndex].length==5){
            // standaard antwoorden
            antwoorden[0] = getDefaultAnswers(vragenAntwoorden[vraagIndex][1])[0];
            antwoorden[1] = getDefaultAnswers(vragenAntwoorden[vraagIndex][1])[1];
            antwoorden[2] = getDefaultAnswers(vragenAntwoorden[vraagIndex][1])[2];
            if(getDefaultAnswers(vragenAntwoorden[vraagIndex][1]).length==4){
                antwoorden[3] = getDefaultAnswers(vragenAntwoorden[vraagIndex][1])[3];
            }
        } else if (vragenAntwoorden[vraagIndex].length==7){
            // tabel gebruiken
            antwoorden[0] = aminoData[vragenAntwoorden[vraagIndex][4]][vragenAntwoorden[vraagIndex][1]];
            antwoorden[1] = aminoData[vragenAntwoorden[vraagIndex][5]][vragenAntwoorden[vraagIndex][1]];
            antwoorden[2] = aminoData[vragenAntwoorden[vraagIndex][6]][vragenAntwoorden[vraagIndex][1]];
        }
        return antwoorden;
    }

}
