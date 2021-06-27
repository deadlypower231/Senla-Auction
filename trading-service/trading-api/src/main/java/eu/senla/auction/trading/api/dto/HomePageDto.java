package eu.senla.auction.trading.api.dto;

import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.Role;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageDto {

    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private Double balance;
    private Set<RoleDto> roles;
    private List<LotDto> lots;
    private List<BetDto> bets;

}
