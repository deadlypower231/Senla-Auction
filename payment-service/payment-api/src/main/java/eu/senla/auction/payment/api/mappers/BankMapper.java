package eu.senla.auction.payment.api.mappers;

import eu.senla.auction.payment.api.dto.BankDto;
import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.entities.Bank;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BankMapper {

    public Bank mapBank(CreateBankDto source) {
        return Bank.builder()
                .userId(source.getUserId())
                .balance(source.getBalance())
                .build();
    }

    public BankDto mapBankDto(Bank source) {
        return (source != null) ? BankDto.builder()
                .userId(source.getUserId())
                .balance(source.getBalance())
                .build() : null;
    }

    public CreateBankDto createMapBank(Bank source) {
        return CreateBankDto.builder()
                .userId(source.getUserId())
                .balance(source.getBalance())
                .build();
    }

    public List<BankDto> mapDtos(List<Bank> source) {
        return source.stream().map(BankMapper::mapBankDto).collect(Collectors.toList());
    }

}
