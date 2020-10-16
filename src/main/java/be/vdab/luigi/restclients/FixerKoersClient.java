package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

@Component
@Qualifier("Fixer")
public class FixerKoersClient implements KoersClient {
    private static final Pattern PATTERN = Pattern.compile(".*\"USD\":");
    private final URL url;
    FixerKoersClient(){
        try{
            url = new URL("http://data.fixer.io/api/latest?access_key=04df31c47dc17f8cc662ebfdfe275d12&symbols=USD");
            } catch (MalformedURLException exception){
            throw new KoersClientException("Fixer URL is verkeerd.");
        }
    }

    @Override
    public BigDecimal getDollarKoers() {
        try (var scanner = new Scanner(url.openStream())){
            scanner.skip(PATTERN);
            scanner.useDelimiter("}");
            return new BigDecimal(scanner.next());
        } catch (IOException |NumberFormatException ex){
            throw new KoersClientException("Kan koers niet inlezen via Fixer.", ex);
        }
    }
}
