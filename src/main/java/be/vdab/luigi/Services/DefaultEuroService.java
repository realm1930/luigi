package be.vdab.luigi.Services;

import be.vdab.luigi.restclients.KoersClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public
class DefaultEuroService implements EuroService {
    private final KoersClient koersClient;

    public DefaultEuroService(KoersClient koersClient) {
        this.koersClient = koersClient;
    }
    @Override
    public BigDecimal naarDollar(BigDecimal euro){
        return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
    }
}