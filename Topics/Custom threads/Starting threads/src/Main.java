public class Main {

    public static void main(String[] args) {
        Thread fstThread = new Thread(new RunnableWorker(), "worker-X");
        Thread sndThread = new Thread(new RunnableWorker(), "worker-Y");
        Thread lstThread = new Thread(new RunnableWorker(), "worker-Z");

        fstThread.start();
        sndThread.start();
        lstThread.start();
    }
}

// Don't change the code below       
class RunnableWorker implements Runnable {

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        if (name.startsWith("worker-")) {
            System.out.println("too hard calculations...");
        }
    }
}