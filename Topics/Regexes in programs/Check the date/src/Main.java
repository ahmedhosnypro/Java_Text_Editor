import java.util.*;

class Date {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String date = scn.nextLine();
        String dateRegex = "(19|20)\\d{2}[-/.](0\\d|1[0-2])[-/.]([0-2]\\d|3[01])" +
                "|([0-2]\\d|3[01])[-/.](0\\d|1[0-2])[-/.](19|20)\\d{2}";

        if (date.matches(dateRegex)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}