import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        int antwoord = 0;


        int voorwaardelijk = 0;
        int beginscherm = 9;
        int optiescherm = 29;
        int toetsscherm = 40;
        int speelscherm = 31;
        int eindscherm = 9;
        System.out.println("score = ");
        System.out.println(voorwaardelijk+
                beginscherm+optiescherm+toetsscherm+
                speelscherm+eindscherm);




        String[] test = antwoordMogelijkBijVraag("volledige naam");
        System.out.println(String.join("@", test));
        System.out.println(Arrays.toString(test));

        String[] test2 = test.clone();

        System.out.println(Arrays.toString(test));
        System.out.println(Arrays.toString(test2));

        test[0] = "koekoek";
        System.out.println("koekoek");
        System.out.println(Arrays.toString(test));
        System.out.println(Arrays.toString(test2));

        String[] rray = new String[]{"a", "b", "c", "b", "e", "a"};
        Set<String> mySet = new HashSet<String>(Arrays.asList(rray));

        System.out.println(mySet.contains("a"));
        System.out.println(mySet.contains("z"));


        String[][] antwoorden;
        antwoorden = new String[10][];
        String[] tests = new String[]{"a", "b"};
        antwoorden[0] = tests;

        System.out.println(Arrays.toString(antwoorden[0]));


    }


    private static String[] antwoordMogelijkBijVraag(String vraag_type) {
        ArrayList<String> antwoorden = new ArrayList<>(Arrays.asList("volledige naam",
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
}
