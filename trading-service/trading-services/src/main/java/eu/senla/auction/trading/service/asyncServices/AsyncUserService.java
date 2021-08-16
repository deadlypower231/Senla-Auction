package eu.senla.auction.trading.service.asyncServices;

import eu.senla.auction.trading.api.dto.payment.BankDto;
import eu.senla.auction.trading.api.providers.IPaymentProviders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
@Slf4j
public class AsyncUserService {

    private final IPaymentProviders paymentProviders;

    public AsyncUserService(IPaymentProviders paymentProviders) {
        this.paymentProviders = paymentProviders;
    }

    @Async
    public void createBalance(BankDto postBank) {
        boolean check = true;
        while (check) {
            try {
                Thread.sleep(10000);
                paymentProviders.createBalance(postBank);
                log.info("Connection to the bank service has been restored! ");
                check = false;
            } catch (Exception e) {
                log.info("No connection to banking service! ");
            }
        }
    }

}
