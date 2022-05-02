import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImportExportVragenAntwoorden {
    String gemaakteVragenBestandPath;
    String gemaakteAntwoordenBestandPath;
    public void ExportVragenAntwoorden(String eersteWoord, int[][] vragenAntwoorden) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();


        String voorVoegsel = eersteWoord+"_"+year+"-"+month+"-"+day+"-"+hour+"-"+minute+"-"+second;



        try {
            // Assume default encoding.
            FileWriter testWriter = new FileWriter(voorVoegsel+"_Aatest.txt");
            FileWriter antwoordWriter = new FileWriter(voorVoegsel+"_Aaant.txt");

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter testBufferedWriter = new BufferedWriter(testWriter);
            BufferedWriter antwoordBufferedWriter = new BufferedWriter(antwoordWriter);

            // Note that write() does not automatically
            // append a newline character.

            for (int x=0; x<vragenAntwoorden.length; x++) {
                String[] printString = printStringMaken(vragenAntwoorden[x]);
                testBufferedWriter.write(printString[0]);
                testBufferedWriter.newLine();
                antwoordBufferedWriter.write(printString[1]);
                antwoordBufferedWriter.newLine();
            }

            // Always close files.
            testBufferedWriter.close();
            antwoordBufferedWriter.close();

            gemaakteVragenBestandPath = voorVoegsel+"_Aatest.txt";
            gemaakteAntwoordenBestandPath = voorVoegsel+"_Aaant.txt";

        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file"+ voorVoegsel + "_Aa<test/ant>.txt");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }


    private String[] printStringMaken(int[] vragenAntwoorden) {
        String vragen = "";
        String antwoorden  = "";

        for (int i = 0; i < 4; i++) {
            vragen+=vragenAntwoorden[i]+";";
        }

        for (int i = 4; i < vragenAntwoorden.length; i++) {
            antwoorden+=vragenAntwoorden[i]+";";
        }

        return new String[]{vragen,antwoorden};
    }


    public int[][] ImportVragenAntwoorden(String vragenBestand, String antwoordenBestand) {
        // The name of the file to open.
        //String fileName = "temp.txt";

        // This will reference one line at a time
        String line = null;
        int[][] vragenAntwoorden = new int[1][1];

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReaderVragen =
                    new FileReader(vragenBestand);
            FileReader fileReaderAntwoorden =
                    new FileReader(antwoordenBestand);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReaderVragen =
                    new BufferedReader(fileReaderVragen);
            BufferedReader bufferedReaderAntwoorden =
                    new BufferedReader(fileReaderAntwoorden);

            String[] vragen = new String[100];
            int teller = 0;
            while((line = bufferedReaderVragen.readLine()) != null) {
                vragen[teller] = line.replace("\n", "").replace("\r", "");
                teller++;
            }

            String[] antwoorden = new String[100];
            teller = 0;
            while((line = bufferedReaderAntwoorden.readLine()) != null) {
                antwoorden[teller] = line.replace("\n", "").replace("\r", "");
                teller++;
            }

            String[] vragenKleiner = new String[teller];
            String[] antwoordenKleiner = new String[teller];

            for (int i = 0; i < teller; i++) {
                vragenKleiner[i] = vragen[i];
                antwoordenKleiner[i] = antwoorden[i];
            }

            bufferedReaderAntwoorden.close();
            bufferedReaderVragen.close();

            vragenAntwoorden = stringArrayNaarIntArray(vragenKleiner, antwoordenKleiner);



        }
        catch(FileNotFoundException ex) {
            System.out.println("Een van de bestanden bestaat niet");
        }
        catch(IOException ex) {
            System.out.println("Een file kan niet worden geopend");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return vragenAntwoorden;
    }


    // Deze meethode moet uiteindelijk aleen de vragen inalden en omzetten in een string
    public int[][] ImportVragen(String vragenBestand) {
        // The name of the file to open.
        //String fileName = "temp.txt";

        // This will reference one line at a time
        String line = null;
        int[][] vragenAntwoorden = new int[1][1];

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReaderVragen =
                    new FileReader(vragenBestand);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReaderVragen =
                    new BufferedReader(fileReaderVragen);

            String[] vragen = new String[100];
            int teller = 0;
            while((line = bufferedReaderVragen.readLine()) != null) {
                vragen[teller] = line.replace("\n", "").replace("\r", "");
                teller++;
            }


            String[] vragenKleiner = new String[teller];

            for (int i = 0; i < teller; i++) {
                vragenKleiner[i] = vragen[i];
            }
            bufferedReaderVragen.close();

//            vragen = stringArrayNaarIntArray(vragenKleiner);



        }
        catch(FileNotFoundException ex) {
            System.out.println("Een van de bestanden bestaat niet");
        }
        catch(IOException ex) {
            System.out.println("Een file kan niet worden geopend");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return vragenAntwoorden;
    }



    int[][] stringArrayNaarIntArray(String[] vragen, String[] antwoorden) {
        int[][] vragenAntwoorden = new int[vragen.length][];

        for (int x = 0; x < vragen.length; x++) {
            ArrayList<Integer> temp = new ArrayList<>();
            String[] vragenTemp = vragen[x].split(";");
            String[] antwoordenTemp = antwoorden[x].split(";");
            for (int i = 0; i < vragenTemp.length; i++) {
                temp.add(Integer.parseInt(vragenTemp[i]));
            }
            for (int i = 0; i < antwoordenTemp.length; i++) {
                temp.add(Integer.parseInt(antwoordenTemp[i]));
            }
            vragenAntwoorden[x] = new int[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                vragenAntwoorden[x][i] = temp.get(i);
            }
        }
        return vragenAntwoorden;
    }


    public String[] checkFile(String vragenBestand, String antwoordenBestand) {
        String line = null;
        String[] fout = new String[]{"antwoord file goed", "vraag file goed"};

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReaderVragen =
                    new FileReader(vragenBestand);
            FileReader fileReaderAntwoorden =
                    new FileReader(antwoordenBestand);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReaderVragen =
                    new BufferedReader(fileReaderVragen);
            BufferedReader bufferedReaderAntwoorden =
                    new BufferedReader(fileReaderAntwoorden);

            String[] vragen = new String[100];
            int teller1 = 0;
            while ((line = bufferedReaderVragen.readLine()) != null) {
                vragen[teller1] = line.replace("\n", "").replace("\r", "");
                teller1++;
            }

            String[] antwoorden = new String[100];
            int teller2 = 0;
            while ((line = bufferedReaderAntwoorden.readLine()) != null) {
                antwoorden[teller2] = line.replace("\n", "").replace("\r", "");
                teller2++;
            }

            if(teller1!=teller2){
                fout = new String[]{"files ongelijke lengte", "files ongelijke lengte"};
            }

            String[] vragenKleiner = new String[teller1];
            String[] antwoordenKleiner = new String[teller2];

            int kleinste;
            if(teller1 < teller2) {
                kleinste = teller1;
            } else {
                kleinste = teller2;
            }


            for (int i = 0; i < kleinste; i++) {
                vragenKleiner[i] = vragen[i];
                antwoordenKleiner[i] = antwoorden[i];
            }

            bufferedReaderAntwoorden.close();
            bufferedReaderVragen.close();


            if(!checkPuntCommas(vragenKleiner, 0)) {
                fout[0] = "vragen file klopt niet";
            }
            if(!checkPuntCommas(antwoordenKleiner, 1)) {
                fout[1] = "antwoorden file klopt niet";
            }


        } catch (FileNotFoundException ex) {
            System.out.println("Een van de bestanden bestaat niet");
        } catch (IOException ex) {
            System.out.println("Een file kan niet worden geopend");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return fout;
    }

    private boolean checkPuntCommas(String[] va, int vragenofAntwoorden) {
        boolean lengteGoed = true;
        if(vragenofAntwoorden==0) {
            for (int i = 0; i < va.length; i++) {
                int aantal = getOccurance(va[i], ';');
                if(aantal!=4) {
                    lengteGoed = false;
                }
            }
        } else {
            for (int i = 0; i < va.length; i++) {
                int aantal = getOccurance(va[i], ';');
                if(aantal!=1&&aantal!=3&&aantal!=5) {
                    lengteGoed = false;
                }
            }
        }
        return lengteGoed;
    }

    public static int getOccurance(String woord, char letter) {
        String s = woord;
        int counter = 0;
        for( int i=0; i<s.length(); i++ ) {
            if(s.charAt(i)==letter) {
                counter++;
            }
        }
        return counter;
    }

}
