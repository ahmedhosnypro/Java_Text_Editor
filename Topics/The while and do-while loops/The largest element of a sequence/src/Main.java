import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int largestNum = 0;
        while (true) {
            int num = scanner.nextInt();
            if (num == 0) {
                break;
            } else if (num > largestNum) {
                largestNum = num;
            }
        }
        System.out.println(largestNum);
    }
}