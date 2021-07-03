package eu.senla.auction.trading.api.dto.payment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private String buyerId;
    private String dealerId;
    private Double amount;

}
