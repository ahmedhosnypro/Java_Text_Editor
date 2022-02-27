import java.util.Scanner;
import java.util.regex.Pattern;


class CheckTheEssay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        System.out.println(text.replace("Franse", "France")
                .replace("Eifel tower", "Eiffel Tower")
                .replace("19th", "XIXth")
                .replace("20th", "XXth")
                .replace("21st", "XXIst"));
    }
}