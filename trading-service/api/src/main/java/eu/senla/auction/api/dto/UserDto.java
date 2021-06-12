package eu.senla.auction.api.dto;

import eu.senla.auction.entity.entities.Bet;
import eu.senla.auction.entity.entities.Lot;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private String password;
    private Double balance;
    private Set<Role> role;
    private List<Lot> lots;
    private List<Bet> bets;

}