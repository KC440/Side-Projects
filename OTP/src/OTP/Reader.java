package OTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Reader {

    /*private Key selectedKey;

    public Key getSelectedKey() {
        return selectedKey;
    }*/

    public boolean inputKeysExist(String filename) {
        File file = new File("src/OTP/INPUT/KEYS/" + filename + ".txt");
        return file.exists();
    }

    public Key importKey(String keyFileName, int keyLineNumber) {
        List<String> keyLines = new ArrayList<>();
        Key key = null;
        try {
            keyLines = Files.readAllLines(Path.of("src/OTP/INPUT/KEYS/" + keyFileName + ".txt"));
        } catch (IOException ioe) {
            System.out.println("\r\n*** The required key file was not found. ***");
        }
        String selectedKey = "DNE";
        if(keyLineNumber <= keyLines.size()) {
        selectedKey = keyLines.get(keyLineNumber - 1);
        }
        if(!selectedKey.equals("USED") && !selectedKey.equals("DNE")) {
            key = new Key(selectedKey, keyLineNumber, keyFileName);
        } else {
            System.out.println("\r\n*** The requested key does not exist, or was already used. ***\r\n");
        }
        return key;
    }

    public String importCiphertext(String fileName) {
        File importPath = new File("src/OTP/INPUT/CIPHERTEXT/" + fileName + ".txt");
        StringBuilder ciphertext = new StringBuilder();
        try {
            Scanner scanner = new Scanner(importPath);
            while (scanner.hasNextLine()) {
                ciphertext.append(scanner.nextLine()).append(",");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("That message file does not exist.");
        }
        return ciphertext.toString();
    }

    public boolean outputKeyExists(String fileName) {
        File exportPath = new File("src/OTP/OUTPUT/KEYS/" + fileName + ".txt");
        return exportPath.exists();
    }

    public boolean outputCiphertextExists(String fileName) {
        File exportPath = new File("src/OTP/OUTPUT/MESSAGE/CIPHERTEXT/" + fileName + ".txt");
        return exportPath.exists();
    }
    public boolean inputCiphertextExists(String fileName) {
        File exportPath = new File("src/OTP/INPUT/CIPHERTEXT/" + fileName + ".txt");
        return exportPath.exists();
    }

     /*public void importKeys(String keyFileName, int keyNumber) {
                    int totalLines = 0;
                try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(
                        "src/OTP/INPUT/KEYS/" + keyFileName + ".txt"))) {

                    while(bufferedReader.readLine() != null) {
                        totalLines++;
                    } bufferedReader.close();
                    BufferedReader bufferedReader2 = Files.newBufferedReader(Paths.get(
                            "src/OTP/INPUT/KEYS/" + keyFileName + ".txt"));
                    if(keyNumber >= 1 && keyNumber <= totalLines) {
                        int currentLine = 1;

                    do {
                        String selectedKeyAsString = bufferedReader2.readLine();
                        if(!selectedKeyAsString.equals("USED")) {
                        selectedKey = new Key(selectedKeyAsString, keyNumber, keyFileName);
                        } else {
                            selectedKey = null;
                        }
                        currentLine++;

                    } while (currentLine <= keyNumber);
                    bufferedReader2.close();
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }

    }*/
}
