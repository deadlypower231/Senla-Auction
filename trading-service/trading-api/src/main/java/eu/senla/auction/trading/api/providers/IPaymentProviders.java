package eu.senla.auction.trading.api.providers;

import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.payment.BankDto;

public interface IPaymentProviders {

    Boolean addBalance(BalanceDto balanceDto);

    BankDto createBalance(BankDto postBank);

}
