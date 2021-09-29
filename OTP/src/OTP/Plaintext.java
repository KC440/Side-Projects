package OTP;



public class Plaintext {

    private final String plaintext;

    public String printable() {
        return plaintext;
    }

    public Plaintext(String ciphertext, Key key) {
        StringBuilder decryptedMessage = new StringBuilder();
        String[] initialNumsAsString = ciphertext.split(",");

        for(int i = 0; i <= initialNumsAsString.length - 1; i++) {
            int inputAsInt = Integer.parseInt(initialNumsAsString[i]);
            int shifted = Math.floorMod(inputAsInt - key.getKeyValues().get(i), key.getCharToIntRef().length());
            decryptedMessage.append(key.getCharToIntRef().charAt(shifted));
        }
        this.plaintext = decryptedMessage.toString();
    }
}
