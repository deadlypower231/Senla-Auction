package eu.senla.auction.payment.api.dto;

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
