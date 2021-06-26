package eu.senla.auction.trading.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "user")
public class User extends AEntity<String> {

    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Date birthday;
    private String password;
    private Double balance;
    private Set<Role> roles;
    private List<Lot> lots;
    private List<Bet> bets;

}
