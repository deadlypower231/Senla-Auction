package eu.senla.auction.trading.entity.entities;

import eu.senla.auction.trading.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "lot")
public class Lot extends AEntity<String> {

    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Double price;
    private User userWin;
    private List<Bet> bets;
    private Status status;


}
