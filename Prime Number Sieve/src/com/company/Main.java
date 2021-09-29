package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Main main = new Main();
        String error = "\r\n*** Only enter an integer 2 or greater ***";
        boolean run = true;
        while (run) {
        try {
            int target = main.getUserInput();
            if(target==0) {
                run=false;
            } else {
                main.sieve(target);
            }
        } catch (Exception e) {
            System.out.println(error);
        }
        }
    }
    private int getUserInput() {
        System.out.print("\r\nEnter a target integer 2 or greater to see prime numbers up to the target. Enter \"0\" to exit: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }
    private void sieve(int target) {
        List<Integer> list = new ArrayList<>();

        for(int i=2; i<=target; i++) {
            list.add(i);
        }
        for(int i=0; list.get(i)*list.get(i) <= target; i++) {
            for(int c=i; c<list.size(); c++) {
                if(c!=i && list.get(c)%list.get(i)==0) {
                    list.remove(list.get(c));
                }
            }
        }
        System.out.println("\r\nPrime numbers up to "+target+" are:");
        System.out.println(Arrays.toString(list.toArray()));
    }
}
