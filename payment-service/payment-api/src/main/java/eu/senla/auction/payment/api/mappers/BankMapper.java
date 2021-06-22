package eu.senla.auction.payment.api.mappers;

import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.entities.Bank;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BankMapper {

    public Bank mapBank(CreateBankDto source){
        return Bank.builder()
                .userId(source.getUserId())
                .balance(source.getBalance())
                .build();
    }

    public CreateBankDto createMapBank(Bank source){
        return CreateBankDto.builder()
                .userId(source.getUserId())
                .balance(source.getBalance())
                .build();
    }

}
