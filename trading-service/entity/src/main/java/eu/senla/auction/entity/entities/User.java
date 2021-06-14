package eu.senla.auction.entity.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

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
