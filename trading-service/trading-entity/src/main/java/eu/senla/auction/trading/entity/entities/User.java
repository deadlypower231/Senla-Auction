package eu.senla.auction.trading.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "user")
public class User extends AEntity<ObjectId> {

    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String birthday;
    private String password;
    private Double balance;
    private Set<Role> roles;
    private List<ObjectId> lots;
    private List<ObjectId> bets;

}
