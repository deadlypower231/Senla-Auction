package eu.senla.auction.api.dto;

import lombok.*;

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
    private Date birthday;

}
