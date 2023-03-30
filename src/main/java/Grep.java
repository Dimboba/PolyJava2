import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {

    private boolean ignore;
    private boolean inverted;
    private boolean regex;
    private List<String> strings;
    private String word;

    public void setIgnore(boolean ignore) { this.ignore = ignore; }
    public void setInverted(boolean inverted) { this.inverted = inverted; }
    public void setRegex(boolean regex) { this.regex = regex; }
    public void setStrings(List<String> strings) { this.strings = strings; }
    public void setWord(String word) { this.word = word; }


    public Grep(boolean ignore, boolean inverted, boolean regex, List<String> strings, String word){
        this.ignore = ignore;
        this.inverted = inverted;
        this.regex = regex;
        this.strings = strings;
        this.word = word;
    }

    public List<String> toGrep(){
        if (regex) return toGrepRegex();
        return  toSimpleGrep();
    }
    private List<String> toSimpleGrep(){
        List<String> result= new ArrayList<>();
        for(String s: strings){
            String line = s;
            if(ignore) {
                line = line.toLowerCase();
            }
            boolean contains = line.contains(word);
            if(contains && !inverted){
                result.add(s);
            }
            else if(!contains && inverted){
                result.add(s);
            }
        }
        return result;
    }
    private List<String> toGrepRegex(){
        List<String> result = new ArrayList<>();
        Pattern pattern;
        if(ignore) {
            pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        }
        else{
            pattern = Pattern.compile(word);
        }
        for(String s: strings) {

            Matcher matcher = pattern.matcher(s);
            boolean contains = matcher.matches();
            if(contains && !inverted){
                result.add(s);
            }
            else if(!contains && inverted){
                result.add(s);
            }
        }
        return result;
    }
}
