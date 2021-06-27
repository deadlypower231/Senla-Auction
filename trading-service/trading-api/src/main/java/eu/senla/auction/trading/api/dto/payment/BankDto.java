package eu.senla.auction.trading.api.dto.payment;

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
