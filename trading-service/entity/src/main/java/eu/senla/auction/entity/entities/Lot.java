package eu.senla.auction.entity.entities;

import eu.senla.auction.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "lot")
public class Lot {

    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Double price;
    private User userWin;
    private List<Bet> bets;
    private Status status;


}
