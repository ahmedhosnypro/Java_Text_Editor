import java.util.*;

public class Main {

    public static void processIterator(String[] array) {
        LinkedList<String> list = new LinkedList<>(List.of(array));

        list.removeIf(current -> !current.startsWith("J"));

        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            String current = iterator.next();
            if (current.startsWith("J")) {
                String str = current.substring(1);
                list.set(index, str);
            }
        }

        iterator = list.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processIterator(scanner.nextLine().split(" "));
    }
}