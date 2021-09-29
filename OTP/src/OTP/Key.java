package OTP;


import java.util.ArrayList;
import java.util.List;

public class Key {

    private final List<Integer> keyValues = new ArrayList<>();
    private final int keyLength;
    private int keyNumber;
    private String fileName;


    public Key (int[] values) {
        this.keyLength = values.length;
        for(int i = 0; i <= values.length - 1; i++) {
            keyValues.add(values[i]);
        }
    }

    public Key(String inputKey, int keyNumber, String fileName) {
        this.fileName = fileName;
        this.keyNumber = keyNumber;
        String[] keyValuesArrayString = inputKey.split(",");
        this.keyLength = keyValuesArrayString.length;
        for(int i = 0; i <= keyLength - 1; i++) {
            keyValues.add(Integer.parseInt(keyValuesArrayString[i]
                    .replace("[", "").replace("]", "")));
        }
    }

    public List<Integer> getKeyValues() {
        return keyValues;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public String getFileName() {
        return fileName;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public String getCharToIntRef() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ -abcdefghijklmnopqrstuvwxyz0123456789)('.,!?";
    }


    @Override
    public String toString() {
        return String.valueOf(keyValues).replace(" ", "");
    }

}
