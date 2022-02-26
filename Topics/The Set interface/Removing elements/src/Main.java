import java.util.*;
import java.util.stream.Collectors;

class SetUtils {

    public static Set<Integer> getSetFromString(String str) {
        return Arrays
                .stream(str.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static void removeAllNumbersGreaterThan10(Set<Integer> set) {
        set.retainAll(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

}

/* Do not change code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numbers = scanner.nextLine();
        Set<Integer> set = SetUtils.getSetFromString(numbers);
        SetUtils.removeAllNumbersGreaterThan10(set);
        set.forEach(e -> System.out.print(e + " "));
    }
}