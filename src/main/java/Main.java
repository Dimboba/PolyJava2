public class Main {

    public static void main(String[] args) {
        String cmd = "src/test/resources/test1.txt";
        String word = "check";
        String[] arg = {word, cmd};
        GrepLauncher grepLauncher = new GrepLauncher();
        //GrepLauncher.main(arg);
        String[] arg2 = {"-v", word, cmd};
        GrepLauncher.main(arg2);
    }
}
