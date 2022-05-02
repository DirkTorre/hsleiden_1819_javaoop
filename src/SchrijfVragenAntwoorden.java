import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SchrijfVragenAntwoorden {
    String vragenBestandPath;
    String antwoordenBestandPath;

    SchrijfVragenAntwoorden() {
        // niks
    }

    public void Schrijf(String eersteWoord, String[] vragen, String[] antwoorden) {
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

            for (int x=0; x<vragen.length; x++) {
                testBufferedWriter.write(vragen[x]);
                testBufferedWriter.newLine();
            }

            for (int x=0; x<antwoorden.length; x++) {
                antwoordBufferedWriter.write(antwoorden[x]);
                antwoordBufferedWriter.newLine();
            }

            // Always close files.
            testBufferedWriter.close();
            antwoordBufferedWriter.close();

            vragenBestandPath = voorVoegsel+"_Aatest.txt";
            antwoordenBestandPath = voorVoegsel+"_Aaant.txt";

        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file"+ voorVoegsel + "_Aa<test/ant>.txt");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
}
