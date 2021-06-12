package eu.senla.auction.entity.entities;

import eu.senla.auction.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bet")
public class Bet {

    private User user;
    private Double price;
    private Date date;
    private Lot lot;
    private Status status;

}
