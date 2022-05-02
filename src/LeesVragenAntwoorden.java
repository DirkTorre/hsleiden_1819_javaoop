import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LeesVragenAntwoorden {
    private String[] vragen_ruw;
    private String[] antwoorden_ruw;

    private String[] vragen;
    private String[][] antwoorden;
    private String[] correct_antwoord;
    private String[] vraag_figuur;

    LeesVragenAntwoorden() {

    }

    public void lees(String vragenBestand, String antwoordenBestand) {
        vragen_ruw = leesBestandGlobaal(vragenBestand);
        antwoorden_ruw = leesBestandGlobaal(antwoordenBestand);
        rawToNeat();
    }


    private String[] leesBestandGlobaal(String bestand) {
        String[] regels_ruw = new String[1];

        // This will reference one line at a time
        String line = null;
        int[][] vragenAntwoorden = new int[1][1];

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(bestand);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);


            String[] regels = new String[100];
            int teller = 0;
            while((line = bufferedReader.readLine()) != null) {
                regels[teller] = line.replace("\n", "").replace("\r", "");
                teller++;
            }

            regels_ruw = new String[teller];

            for (int i = 0; i < teller; i++) {
                regels_ruw[i] = regels[i];
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Een van de bestanden bestaat niet");
        }
        catch(IOException ex) {
            System.out.println("Een file kan niet worden geopend");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return regels_ruw;
    }


    private void rawToNeat() {
        vragen = new String[vragen_ruw.length];
        antwoorden = new String[vragen_ruw.length][];
        correct_antwoord = new String[vragen_ruw.length];
        vraag_figuur = new String[vragen_ruw.length];

        // string omzetten in arrays
        for (int x = 0; x < vragen_ruw.length; x++) {
            vragen[x] = vragen_ruw[x].split("#")[0].split("@")[0];

            vraag_figuur[x] = vragen_ruw[x].split("#")[0].split("@")[1];
            if (vraag_figuur[x].equals("null")) {
                vraag_figuur[x] = null;
            }

            //antwoorden[x] = new String[vragen_ruw[x].split("#")[1].split("@").length];
            antwoorden[x] = vragen_ruw[x].split("#")[1].split("@");

            correct_antwoord[x] = antwoorden_ruw[x];
        }
    }


    public void leesVragen(String vragenBestand) {
        vragen_ruw = leesBestandGlobaal(vragenBestand);
        vragen = new String[vragen_ruw.length];
        antwoorden = new String[vragen_ruw.length][];
        vraag_figuur = new String[vragen_ruw.length];

        // string omzetten in arrays
        for (int x = 0; x < vragen_ruw.length; x++) {
            vragen[x] = vragen_ruw[x].split("#")[0].split("@")[0];

            vraag_figuur[x] = vragen_ruw[x].split("#")[0].split("@")[1];
            if (vraag_figuur[x].equals("null")) {
                vraag_figuur[x] = null;
            }

            //antwoorden[x] = new String[vragen_ruw[x].split("#")[1].split("@").length];
            antwoorden[x] = vragen_ruw[x].split("#")[1].split("@");
        }

    }


    public void leesAntwoorden(String antwoordenBestand) {
        antwoorden_ruw = leesBestandGlobaal(antwoordenBestand);
        correct_antwoord = new String[antwoorden_ruw.length];

        // string omzetten in arrays
        for (int x = 0; x < antwoorden_ruw.length; x++) {
            //antwoorden[x] = new String[vragen_ruw[x].split("#")[1].split("@").length];
            correct_antwoord[x] = antwoorden_ruw[x];
        }

    }




    public String[] getVraag() {
        return vragen;
    }


    public String getVraag(int index) {
        return vragen[index];
    }


    public String[][] getAntwoorden() {
        return antwoorden;
    }


    public String[] getAntwoorden(int index) {
        return antwoorden[index];
    }


    public String[] getFiguur() {
        return vraag_figuur;
    }


    public String getFiguur(int index) {
        return vraag_figuur[index];
    }


    public String[] getCorrectAntwoord() {
        return correct_antwoord;
    }


    public String getCorrectAntwoord(int index) {
        return correct_antwoord[index];
    }


    public static void main(String[] args) {
        LeesVragenAntwoorden test = new LeesVragenAntwoorden();
        test.lees("/home/user/IntelliJProjects/bstopJDK8GIT/Welk_2019-7-11-18-30-42_Aatest.txt", "/home/user/IntelliJProjects/bstopJDK8GIT/Welk_2019-7-11-18-30-42_Aaant.txt");
        System.out.println(test.getVraag(0));
        System.out.println(Arrays.toString(test.getAntwoorden(0)));
        System.out.println(test.getFiguur(0));
        System.out.println(test.getCorrectAntwoord(0));

        System.out.println();

        LeesVragenAntwoorden test2 = new LeesVragenAntwoorden();
        test2.leesVragen("/home/user/IntelliJProjects/bstopJDK8GIT/Welk_2019-7-11-18-30-42_Aatest.txt");
        System.out.println(test2.getVraag(0));
        System.out.println(Arrays.toString(test2.getAntwoorden(0)));
        System.out.println(test2.getFiguur(0));

        System.out.println();

        LeesVragenAntwoorden test3 = new LeesVragenAntwoorden();
        test3.leesAntwoorden("/home/user/IntelliJProjects/bstopJDK8GIT/Welk_2019-7-11-18-30-42_Aaant.txt");
        System.out.println(test3.getCorrectAntwoord(0));
    }
}
