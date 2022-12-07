import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {

    public static List<String> toGrep(boolean ignore, boolean inverted, List<String> strings, String word){
        List<String> result= new ArrayList<>();
        for(String s: strings){
            if(ignore) {
                s = s.toLowerCase();
            }
            boolean contains = s.contains(word);
            if(contains && !inverted){
                result.add(s);
            }
            else if(!contains && inverted){
                result.add(s);
            }
        }
        return result;
    }
    public static List<String> toGrepRegex(boolean ignore, boolean inverted, List<String> strings, String regex){
        List<String> result = new ArrayList<>();
        Pattern pattern;
        if(ignore) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        else{
            pattern = Pattern.compile(regex);
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
