package OTP;




public class Ciphertext {

    private final String printableCiphertext;

    public String getPrintableCiphertext() {
        return printableCiphertext;
    }

    public Ciphertext(String plaintext, Key key) {
        StringBuilder ciphertext = new StringBuilder();

        for(int i = 0; i <= plaintext.length() - 1; i++) {
            int shifted = key.getCharToIntRef().indexOf(plaintext.charAt(i));
            if(i == plaintext.length() - 1) {
                ciphertext.append((shifted + key.getKeyValues().get(i)) % key.getCharToIntRef().length());
            } else {
                ciphertext.append((shifted + key.getKeyValues().get(i)) % key.getCharToIntRef().length()).append(",");
            }
        }
        this.printableCiphertext = ciphertext.toString();
    }
}
