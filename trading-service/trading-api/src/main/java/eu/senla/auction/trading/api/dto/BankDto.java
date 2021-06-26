package eu.senla.auction.trading.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDto {

    private String userId;
    private Double balance;

}
