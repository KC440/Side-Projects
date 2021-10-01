package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Main main = new Main();
        String error = ANSI_RED+"*** Only enter an integer "
                +ANSI_YELLOW+"2"+ANSI_RED+" or greater ***"+ANSI_RESET;
        boolean run = true;
        while (run) {
            try {
                int target = main.getUserInput();
                if(target==0) {
                    run=false;
                } else if(target>1) {
                    main.sieve(target);
                } else {
                    System.out.println(error);
                }
            } catch (Exception e) {
                System.out.println(error);
            }
        }
    }

    private int getUserInput() {

        System.out.print(ANSI_CYAN+"Enter a target integer "+ANSI_YELLOW+"2"+ANSI_CYAN+
                " or greater to see prime numbers up to the target. Enter \""
                +ANSI_YELLOW+"0"+ANSI_CYAN+"\" to exit: "+ANSI_RESET);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private void sieve(int target) {

        List<Integer> list = new ArrayList<>();
        list.add(2);

        for(int i=3; i<=target; i+=2) {
            list.add(i);
        }

        for(int i=0; list.get(i)*list.get(i)<=target; i++) {
            for (int c=i+1; c < list.size(); c++) {
                if (c!=i+1 && list.get(c)%list.get(i)==0) {
                    list.remove(list.get(c));
                }
            }
        }

        System.out.println(ANSI_CYAN+"\r\nPrime numbers up to "+ANSI_YELLOW+target+ANSI_CYAN+
                ", with centered square primes in "+ANSI_BLUE+"blue"+ANSI_CYAN+", star primes in "+ANSI_RED+"red"+ANSI_CYAN+
                ", and primes that are both in "+ANSI_PURPLE+"purple"+ANSI_CYAN+":\r\n"+ANSI_RESET);

        findFigurates(list);
    }

    private void findFigurates(List<Integer> list) {

        System.out.print(" ");

        for(int i=0; i<list.size(); i++) {
            int num = list.get(i);
            int star = 1;
            int square = 1;
            int count = 1;

            while (star<num || square<num) {
                if(star<num) {
                    star+=12*count;
                }
                if(square<num) {
                    square+=4*count;
                }
                count++;
            }

            boolean isStar=star==num;
            boolean isCenterSquare=square==num;

            if (isStar && isCenterSquare) {
                System.out.print(ANSI_PURPLE+num+ANSI_RESET);
            } else if(isStar) {
                System.out.print(ANSI_RED+num+ANSI_RESET);
            } else if(isCenterSquare) {
                System.out.print(ANSI_BLUE+num+ANSI_RESET);
            } else {
                System.out.print(ANSI_GREEN+num+ANSI_RESET);
            }
            if (i!= list.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.println("\r\n");
    }
}
