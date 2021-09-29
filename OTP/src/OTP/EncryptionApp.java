package OTP;

import java.util.Scanner;

public class EncryptionApp {

    private static final String MAIN_MENU_OPTION_GENERATE_KEYS = "GENERATE KEYS";
    private static final String MAIN_MENU_OPTION_ENCRYPT_MESSAGE = "ENCRYPT MESSAGE";
    private static final String MAIN_MENU_OPTION_DECRYPT_MESSAGE = "DECRYPT MESSAGE";
    private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
    private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_GENERATE_KEYS,
                                                                     MAIN_MENU_OPTION_ENCRYPT_MESSAGE,
                                                                     MAIN_MENU_OPTION_DECRYPT_MESSAGE,
                                                                     MAIN_MENU_OPTION_EXIT };


    private final Menu menu;
    Writer writer = new Writer();
    Reader reader = new Reader();


    public static void main(String[] args) {

        EncryptionApp encryptionCLI = new EncryptionApp();
        encryptionCLI.run();
    }

    public EncryptionApp() {
        this.menu = new Menu(System.in, System.out);
    }
    private void run() {
        boolean run = true;
        while(run) {
            String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            switch (choice) {
                case MAIN_MENU_OPTION_GENERATE_KEYS:
                    createKeysMenu();
                    break;
                case MAIN_MENU_OPTION_ENCRYPT_MESSAGE:
                    encryptMenu();
                    break;
                case MAIN_MENU_OPTION_DECRYPT_MESSAGE:
                    decryptMenu();
                    break;
                case MAIN_MENU_OPTION_EXIT:
                    run = false;
                    break;
            }
        }
    }

    private void createKeysMenu() {
        RandomNumberService rns = new RandomNumberService();
        if(rns.getDataLimit() != null) {
        System.out.println("\r\nTrue random numbers are provided by Random.org. The current data limit for this IP address " +
                "is, " + rns.getDataLimit().trim() + " bits.\r\n");

        boolean enoughData = Integer.parseInt(rns.getDataLimit().trim()) > 0;
        if(enoughData) {
        String fileName = "";
        boolean run = true;
        while (run) {
        fileName = getUserInput("Enter a file name (\".txt\" not required):");
            if(reader.outputKeyExists(fileName)) {
                String overwrite = getUserInput("That file already exists. Enter \"y\" to overwrite," +
                        " or enter any other key to go back.");
                if (overwrite.equals("y")) {
                    run = false;
                }
            } else {
                run = false;
            }
        }
        int keyLength = getUserInputInteger("Desired key length:");
        int numKeys = getUserInputInteger("Desired number of keys:");
        long size = (long) keyLength * numKeys;
        String readyToGenerate = getUserInput("Enter \"y\" to generate keys, or enter any other key to return to main menu.");
        if (readyToGenerate.equals("y") && size <= 10000) {
        writer.exportKeys(keyLength, numKeys, fileName);
        System.out.println("\r\nKeys have been generated.  They are located in the OUTPUT/KEYS folder. " +
                "The remaining data limit is " + rns.getDataLimit().trim() + " bits.");
        } else if(readyToGenerate.equals("y")) {
            System.out.println("\r\nThe maximum value for (Key Length * Number of Keys) is 10,000. No keys were generated.");
        } else {
            System.out.println("\r\nNo keys were generated.");
        }
    } else {
            System.out.println("This IP address does not have sufficient available data with Random.org.");
        }}
    }

    private Key chooseKey() {
        boolean run = true;
        String keyFile = null;
        Key key = null;
       while(run) {
        keyFile = getUserInput("Enter name of key file:");
        if(!reader.inputKeysExist(keyFile)) {
            System.out.println("That file does not exist.");
        } else {
            run = false;
            }
       }
        while (key == null) {
        int keyNumber = getUserInputInteger("Which key should be used?");
        key = reader.importKey(keyFile, keyNumber);
        }
        return key;
    }

    private String enterPlaintextMessage() {
        Key dummyKey = new Key(new int[]{});
        String plaintext = null;
        boolean run = true;
        while (run) {
        plaintext = getUserInput("Enter message");
        if(!hasLegalCharacters(plaintext)) {
            System.out.println("This message contains illegal characters. Legal characters are: "
                    + dummyKey.getCharToIntRef());
        } else {
            run = false;
            }
        }
        return plaintext;
    }


    private void encryptMessage(String plaintext, Key chosenKey, String outputFileName, String keyFileName) {
        Ciphertext ciphertext = new Ciphertext(plaintext, chosenKey);
        writer.exportCiphertext(outputFileName, ciphertext, chosenKey.getKeyNumber(), keyFileName);
        //Overwrite disabled for testing
        //writer.overwriteUsedKey(chosenKey.getFileName(), chosenKey.getKeyNumber());
        System.out.println("\r\nMessage encrypted and placed in OUTPUT/MESSAGE/CIPHERTEXT folder.");
    }

    private void decryptMessage(String message, String keyFileName, int keyNumber) {
        Key key = reader.importKey(keyFileName, keyNumber);
        if(key != null) {
            Plaintext plaintext = new Plaintext(message, key);
            //Overwrite disabled for testing
            //writer.overwriteUsedKey(keyFileName, keyNumber);
            System.out.println("\r\n" + plaintext.printable());
        }
    }

    private String nameOutputFile() {
        String outputName = "";
        boolean run = true;
        while (run) {
            outputName = getUserInput("Enter a name for this message (\".txt\" not required):");
            if(reader.outputCiphertextExists(outputName)) {
                String overwrite = getUserInput("That file already exists. Enter \"y\" to overwrite," +
                        " or enter any other key to go back.");
                if (overwrite.equals("y")) {
                    run = false;
                }
            } else {
                run = false;
            }
        }
        return outputName;
    }

    private String chooseInputCiphertext() {
        String inputFile = getUserInput("Enter ciphertext file name:");
        if(reader.inputCiphertextExists(inputFile)) {
            return reader.importCiphertext(inputFile);
        } else {
            return "";
        }
    }

    private boolean hasLegalCharacters(String message) {
        Key dummyKey = new Key(new int[]{});
        boolean legalChars = true;
        for (int i = 0; i <= message.length() - 1; i++) {
            if(!dummyKey.getCharToIntRef().contains(String.valueOf(message.charAt(i)))) {
                legalChars = false;
            }
        }
        return legalChars;
    }

    private void encryptMenu() {
        Key chosenKey = chooseKey();
        String outputFileName = nameOutputFile();
        boolean runEncrypt = true;
        while (runEncrypt) {
            String plaintext = enterPlaintextMessage();
            boolean keyIsLongEnough = plaintext.length() <= chosenKey.getKeyLength();
            if (keyIsLongEnough) {
                String shouldEncrypt = getUserInput("\r\nMessage is ready to encrypt. Enter \"y\" to" +
                        " encrypt, or enter any other key to cancel.");
                if(shouldEncrypt.equals("y")) {
                encryptMessage(plaintext, chosenKey, outputFileName, chosenKey.getFileName());
                } else {
                    System.out.println("\r\nMessage was not encrypted.");
                }
                runEncrypt = false;
            } else {
                System.out.println("The message is too long for the chosen key. Current key length is "
                        + chosenKey.getKeyLength() + ".");
            }
        }
    }

    private void decryptMenu() {
        String initialInput = chooseInputCiphertext();
        if(initialInput.length() > 0) {
            String[] ciphertextInfo = initialInput.split(",");
            String keyFileName = ciphertextInfo[0];
            int keyNumber = Integer.parseInt(ciphertextInfo[1]);
            StringBuilder message = new StringBuilder();
            for(int i = 2; i <= ciphertextInfo.length - 1; i++) {
                message.append(ciphertextInfo[i]).append(",");
            }
            decryptMessage(message.toString(), keyFileName, keyNumber);
        } else {
            System.out.println("\r\n*** That file does not exist. ***");
        }
    }

    private String getUserInput(String prompt) {
        System.out.print(prompt + " >>> ");
        return new Scanner(System.in).nextLine();
    }

    private int getUserInputInteger(String prompt) {

        int choice = 0;
        while (choice <= 0) {
            System.out.print(prompt + " >>> ");
            String inputString = new Scanner(System.in).nextLine();
        try {
            choice = Integer.parseInt(inputString);
            if(choice <= 0) {
                System.out.println("Please enter an integer greater than zero.");
                }
            } catch (Exception e) {
                System.out.println("Please enter an integer greater than zero.");
            }
        }
        return choice;
    }
}


