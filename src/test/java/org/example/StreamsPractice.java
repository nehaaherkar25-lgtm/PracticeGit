package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsPractice {

    @Test
    public void streamsFilterPractice() throws IOException {
        ArrayList<String> name = new ArrayList<String>();
        name.add("Neha");
        name.add("Ganesh");
        name.add("Arvik");
        name.add("Disha");
        name.add("Jyoti");
        name.add("Sydhir");
        name.add("Advik");
        name.add("Aarya");

        List<String> names = Arrays.asList("Men","Women","Neutral");
        //methods:
        //1. filter()
        //2. count()
        //3. limit(int)
        //4. forEach()
        //5. sorted()
        //6. map()
        //7. concat()
        //8. anyMatch()
        //9. distict()
        //10.collect()
        Long c = name.stream().filter(s->s.startsWith("A")).count();
        System.out.println("Count of name starting with A: "+c);

        System.out.println("==============Names starting with A=============");
        name.stream().filter(s->s.startsWith("A")).forEach(s-> System.out.println(s));

        System.out.println("===========first name with length 5===============");
        name.stream().filter(s->s.length()==5).limit(1).forEach(s -> System.out.println(s));

        System.out.println("===========Name in ascending order in uppercase============");
        name.stream().sorted().map(s->s.toUpperCase()).forEach(s -> System.out.println(s));

        System.out.println("===========Name in descending order============");
        name.stream().sorted(Comparator.reverseOrder()).forEach(s -> System.out.println(s));

        System.out.println("===========Concatenated lists=========");
        Stream<String> concatNames = Stream.concat(name.stream(),names.stream());
        concatNames.forEach(s -> System.out.println(s));

        boolean flag=names.stream().anyMatch(s -> s.contains("men"));
        Assert.assertTrue(flag);

        List<Integer> val=Arrays.asList(5,3,7,8,9,4,3,1,7,5,1,5,6);
        System.out.println("============Unique and sorted values========");
        val.stream().distinct().sorted().forEach(s -> System.out.println(s));

        System.out.println("============3rd value form Unique and sorted list========");
        List<Integer> uniq = val.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println(uniq.get(2));
    }
}
