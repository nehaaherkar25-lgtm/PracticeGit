package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        NewTest nt = new NewTest();
        nt.getdata();

        int[] arr1 = {4,7,2,9,24};
        System.out.println(arr1.length);

        ArrayList<String> ary1 = new ArrayList<String> ();
        ary1.add("Neha");
        ary1.add("Ganesh");
        ary1.add("Arvik");
        ary1.add("Gaikwad");
        ary1.remove(3);
        System.out.println("ArryaList : " +ary1.get(2));
        System.out.println(ary1.contains("Neha"));

        System.out.println(getData2());

        int num[] = {1,4,7,9,10};
        for(int i =0;i<num.length; i++){
            System.out.println(num[i]);
        }

        String[] s = {"neha", "ganesh", "Arvik"};
        for(int i=0; i<s.length;i++){
            System.out.println(s[i]);
        }
        System.out.println("String Array: " + Arrays.toString(s));

        String s1="Arvik Neha Ganesh";
        String rev="";
        for(int i=s1.length()-1; i>=0;i--){
           rev=rev+s1.charAt(i);
        }
        System.out.println(rev);
        String rev1="";
        for(int i=0; i<s1.length();i++){
            rev1=s1.charAt(i)+rev1;
        }
        System.out.println("New reverse string is :" +rev1);
        String[] splittedS = s1.split("Neha");
        System.out.println("Splitted Arrays : " + Arrays.toString(splittedS));

        List<String> aS1 = Arrays.asList(s);
        for(int i =0; i<aS1.size();i++){
            System.out.println(aS1.get(i) + aS1.contains("Arvik"));
        }

        for(int n:num) {
            if (n % 2 == 0) {
                System.out.println(n);
            }
        }
    }

    public static String getData2()
    {
        System.out.println ("hello world");
        return "rahul shetty";
    }

}