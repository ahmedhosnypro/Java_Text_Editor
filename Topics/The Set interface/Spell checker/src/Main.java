import java.util.*;
import java.util.stream.Collectors;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int records = Integer.parseInt(scanner.nextLine());

        Set<String> knownWords = new HashSet<String>();
        Set<String> unKnownWords = new HashSet<String>();

        for (int i = 0; i < records; i++) {
            String input = scanner.nextLine().toLowerCase().trim();
            knownWords.add(input);
        }

        int inputLines = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < inputLines; i++) {
            String line = scanner.nextLine();
            unKnownWords.addAll(Arrays
                    .stream(line.split(" "))
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toCollection(HashSet::new)));
        }

        unKnownWords.removeAll(knownWords);
        unKnownWords.forEach(System.out::println);
    }
}