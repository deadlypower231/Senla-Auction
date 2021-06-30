package eu.senla.auction.trading.api.dto.lot;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotCompletedCurrentUserDto {

    private String id;
    private String name;
    private String description;
    private String dateStart;
    private String dateEnd;
    private Double price;
    private String userWin;
    private String chat;

}
