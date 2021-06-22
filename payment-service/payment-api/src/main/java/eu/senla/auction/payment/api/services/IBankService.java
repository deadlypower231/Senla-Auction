package eu.senla.auction.payment.api.services;

import eu.senla.auction.payment.api.dto.CreateBankDto;

public interface IBankService {

    CreateBankDto createBank(CreateBankDto createBankDto);

}
