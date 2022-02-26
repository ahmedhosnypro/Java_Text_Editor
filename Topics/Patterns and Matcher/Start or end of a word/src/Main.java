import java.util.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String part = scanner.nextLine();
        String line = scanner.nextLine();

        Pattern pattern = Pattern.compile("^" + part + "|" + part + "$",
                Pattern.CASE_INSENSITIVE);

        boolean found = false;
        for (String str : line.split("\\W")) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                System.out.println("YES");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("NO");
        }
    }
}