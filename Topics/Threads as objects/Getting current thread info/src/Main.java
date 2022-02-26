
class Info {

    public static void printCurrentThreadInfo() {
        Thread currentThread = Thread.currentThread();

        System.out.println("name: " + currentThread.getName() + "\n" +
                "priority: " + currentThread.getPriority());
    }
}
