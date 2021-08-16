package eu.senla.auction.trading.service.providers;

import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.payment.BankDto;
import eu.senla.auction.trading.api.providers.IPaymentProviders;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
public class PaymentProvider implements IPaymentProviders {

    private final RestTemplate restTemplate;
    private final Environment env;

    private final String PAYMENT_URI = "spring.provider.payment";

    public PaymentProvider(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Override
    public Boolean addBalance(BalanceDto balanceDto) {
        return restTemplate.postForObject(env.getProperty(PAYMENT_URI) + "/bank/addBalance", balanceDto, Boolean.class);
    }

    @Override
    public BankDto createBalance(BankDto postBank) {
        return restTemplate.postForObject(env.getProperty(PAYMENT_URI) + "/bank/create", postBank, BankDto.class);
    }

}
