import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrepTest {
    @Test
    public void Test1(){
        String cmd="src/test/resources/test1.txt";
        String word = "check";
        String[] args = {word, cmd};
        GrepLauncher grepLauncher = new GrepLauncher();
        List<String> ans = List.of("check 1", "hello check 3", "hellocheck 5", "checkhello 6");
    }

    @Test
    public void testGrepInvertedIgnore(){
        List<String> test = List.of(
                "check 1", "Check 2", "hello check 3", "hello 4", "hellocheck 5", "checkhello 6", "Checkhello 7");
        Grep grep = new Grep(false, false, false, test, "check");
        assertEquals(List.of("check 1", "hello check 3", "hellocheck 5", "checkhello 6"),
                grep.toGrep());
        grep.setInverted(true);
        assertEquals(List.of("Check 2", "hello 4", "Checkhello 7"),
                grep.toGrep());

        grep.setInverted(false);
        grep.setIgnore(true);
        assertEquals(List.of("check 1", "Check 2", "hello check 3", "hellocheck 5", "checkhello 6", "Checkhello 7"),
            grep.toGrep());

        grep.setInverted(true);
        assertEquals(List.of("hello 4"), grep.toGrep());
    }

    @Test
    public void testReadFile(){
        List<String> test = List.of(
                "check 1", "Check 2", "hello check 3", "hello 4", "hellocheck 5", "checkhello 6", "Checkhello 7");
        GrepLauncher grepLauncher = new GrepLauncher();
        assertEquals(test, grepLauncher.readFile("src/test/resources/test1.txt"));
    }

    @Test
    public void testRegex(){
        List<String> test = List.of(
                "abuser122",
                "abuse122",
                "abuse1234",
                "2abuse",
                "Abuse124",
                "abuse",
                "abuse132abuse",
                "a322",
                "1423",
                "a",
                "aaaa"
        );
        String regex = "[a-z]{0,}[0-9]{0,3}";
        Grep grep = new Grep(false, false, true, test, regex);
        List<String> ans = List.of("abuser122",
                "abuse122",
                "abuse",
                "a322",
                "a",
                "aaaa");
        assertEquals(ans, grep.toGrep());

        ans = List.of(
                "abuse1234",
                "2abuse",
                "Abuse124",
                "abuse132abuse",
                "1423");
        grep.setInverted(true);
        assertEquals(ans, grep.toGrep());

        ans = List.of("abuser122",
                "abuse122",
                "Abuse124",
                "abuse",
                "a322",
                "a",
                "aaaa");
        grep.setInverted(false);
        grep.setIgnore(true);
        assertEquals(ans, grep.toGrep());

        ans = List.of(
                "abuse1234",
                "2abuse",
                "abuse132abuse",
                "1423");
        grep.setInverted(true);
        assertEquals(ans, grep.toGrep());
    }
}
