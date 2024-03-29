package eu.senla.auction.trading.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AEntity<T> {

    @MongoId
    protected T id;

}
