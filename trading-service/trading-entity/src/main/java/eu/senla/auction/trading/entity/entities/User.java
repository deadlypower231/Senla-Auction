package eu.senla.auction.trading.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && birthday.equals(user.birthday) && password.equals(user.password) && Objects.equals(balance, user.balance) && Objects.equals(roles, user.roles) && Objects.equals(lots, user.lots) && Objects.equals(bets, user.bets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, email, birthday, password, balance, roles, lots, bets);
    }
}
