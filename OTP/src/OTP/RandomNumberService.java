package OTP;


import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class RandomNumberService {

    private static final String API_DATA_LIMIT = "https://www.random.org/quota";
    private static final String INT_GENERATOR = "https://www.random.org/integers";
    private static final RestTemplate restTemplate = new RestTemplate();

    public String getDataLimit() {

        try {
            return restTemplate.getForObject(API_DATA_LIMIT + "/?format=plain", String.class);
        } catch (ResourceAccessException | RestClientResponseException ex) {
            errorMessage();
        }
        return null;
    }

    public String[] getTrueRandom(int totalNums) {
        Key dummyKey = new Key(new int[]{});
        int refLength = dummyKey.getCharToIntRef().length() - 1;
        String values = null;
        try {
        values = restTemplate.getForObject(INT_GENERATOR + "/?num=" + totalNums
                + "&min=0&max=" + refLength + "&col=1&base=10&format=plain&rnd=new", String.class);
        } catch (ResourceAccessException | RestClientResponseException ex) {
            errorMessage();
        }
        StringBuilder valuesFormat = new StringBuilder();
        assert values != null;
        Scanner scanner = new Scanner(values);
        while (scanner.hasNextLine()) {
            valuesFormat.append(scanner.nextLine()).append(",");
        }
        return valuesFormat.toString().split(",");
    }

    private void errorMessage() {
        System.out.println("\r\nSomething went wrong. Please check your internet connection.");
    }
}



