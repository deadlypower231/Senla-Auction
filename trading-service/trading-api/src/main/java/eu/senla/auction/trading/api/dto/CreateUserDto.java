package eu.senla.auction.trading.api.dto;

import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthday;

}
