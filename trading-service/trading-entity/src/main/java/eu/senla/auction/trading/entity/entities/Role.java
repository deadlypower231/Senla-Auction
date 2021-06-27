package eu.senla.auction.trading.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "role")
public class Role extends AEntity<ObjectId> {

    @Field(name = "roleName")
    @Indexed(unique = true)
    private String roleName;

    @Override
    public String toString() {
        return  "roleName=" + roleName;
    }
}
