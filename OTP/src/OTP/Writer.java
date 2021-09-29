package OTP;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Writer {

    public void exportCiphertext(String fileName, Ciphertext ciphertext, int keyNumber, String keyFileName) {
        File exportPath = new File("src/OTP/OUTPUT/MESSAGE/CIPHERTEXT/" + fileName + ".txt");
        try(PrintWriter printWriter = new PrintWriter(exportPath)) {
            printWriter.println(keyFileName);
            printWriter.println(keyNumber);
            printWriter.println(ciphertext.getPrintableCiphertext());
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
        }

/*    public void exportKeys(int keyLength, int numKeys, String fileName) {
        File exportPath = new File("src/OTP/OUTPUT/KEYS/" + fileName + ".txt");
        try(PrintWriter printWriter = new PrintWriter(exportPath)) {
            for(int i = 1; i <= numKeys; i++) {
                Key key = new Key(keyLength);
                printWriter.println(key);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
}*/

    public void exportKeys(int keyLength, int numKeys, String fileName) {
        File exportPath = new File("src/OTP/OUTPUT/KEYS/" + fileName + ".txt");
        RandomNumberService rns = new RandomNumberService();
        int totalNums = keyLength * numKeys;
        String[] numBank = rns.getTrueRandom(totalNums);
        int count = 0;
        try(PrintWriter printWriter = new PrintWriter(exportPath)) {
            for(int i = 1; i <= numKeys; i++) {
                int[] keyValues = new int[keyLength];
                for (int c = 0; c <= keyLength - 1; c++) {
                    keyValues[c] = Integer.parseInt(numBank[count]);
                    count++;
                }
                Key key = new Key(keyValues);
                printWriter.println(key);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }


    public void overwriteUsedKey(String keyFileName, int keyLineNumber) {
        List<String> keyLines = new ArrayList<>();
        try {
            keyLines = Files.readAllLines(Path.of("src/OTP/INPUT/KEYS/" + keyFileName + ".txt"));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        String lineToReplace = keyLines.get(keyLineNumber - 1);
        for(int i = 0; i <= keyLines.size() - 1; i++) {
            if(lineToReplace.equals(keyLines.get(i))) {
                keyLines.set(i, "USED");
            }
        }
        try {
            Files.write(Path.of("src/OTP/INPUT/KEYS/" + keyFileName + ".txt"), keyLines);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
