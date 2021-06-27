package eu.senla.auction.trading.api.dto.payment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceDto {

    private String userId;
    private Double amount;
    private String numberCard;
    private String cvv;

}