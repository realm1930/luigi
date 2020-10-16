package be.vdab.luigi.Services;

import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.restclients.KoersClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultEuroService implements EuroService {
    private final KoersClient[] koersClients;

    public DefaultEuroService(KoersClient[] koersClients){
        this.koersClients=koersClients;
    }
    @Override
    public BigDecimal naarDollar(BigDecimal euro){
        Exception laatste = null;
        for (var client:koersClients){
            try{
                return euro.multiply(client.getDollarKoers())
                        .setScale(2,RoundingMode.HALF_UP);
            } catch (KoersClientException e){
                laatste = e;
            }
        }
        throw new KoersClientException("Kan dollar koers nergens lezen.",laatste);
    }
}