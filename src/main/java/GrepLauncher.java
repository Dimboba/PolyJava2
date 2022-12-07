import org.kohsuke.args4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GrepLauncher {
    @Option(name = "-r", required = false, usage = "Phrase instead of word", metaVar = "regex")
    private static boolean r = false;

    @Option(name = "-v", required = false, usage = "Invert filtration's condition", metaVar = "reverse")
    private static boolean v = false;

    @Option(name = "-i", required = false, usage = "Ignore register of letters", metaVar = "ignore")
    private static boolean i = false;

    @Argument(required = true, metaVar = "Word", usage = "Word to find", index = 0)
    private String word;

    @Argument(required = true, metaVar = "Name of file", usage = "Input file name", index = 1)
    private String inputFileName;

    static public void main(String[] args){
        new GrepLauncher().launch(args);
    }

    private List<String> readFile(InputStream inputStream){
        List<String> strings= new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while((s = bufferedReader.readLine()) != null) {
                strings.add(s);
            }
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
            return null;
        }
        return strings;
    }

    private List<String> readFile(String inputStreamName) {
        try {
            FileInputStream inputStream = new FileInputStream(inputStreamName);
            return readFile(inputStream);
        }
        catch(FileNotFoundException ex){
            //ex.printStackTrace();
            System.err.println(ex.getMessage());
            return null;
        }
    }
    private void launch(String[] args){
        CmdLineParser parser = new CmdLineParser(this);

        try{
            parser.parseArgument(args);
        }catch(CmdLineException ex) {
            System.err.println(ex.getMessage());
            parser.printUsage(System.err);
            return;
        }
        List<String> result;
        List<String> strings = readFile(inputFileName);
        if(r){
            result = Grep.toGrepRegex(i, v, strings, word);
        }
        else{
            result = Grep.toGrep(i, v, strings, word);
        }
        for(String s: result){
            System.out.println(s);
        }
    }
}
