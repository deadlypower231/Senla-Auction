package eu.senla.auction.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User extends AEntity<String> {

    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Date birthday;
    private String password;
    private Double balance;
    private Set<Role> role;
    private List<Lot> lots;
    private List<Bet> bets;

}
