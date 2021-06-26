package eu.senla.auction.payment.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private String numberCard;
    private Double balance;
    private String cvv;

}
