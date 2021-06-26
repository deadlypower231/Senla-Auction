package eu.senla.auction.payment.api.dto;

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