package lesniak.marta.habitTracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginDto {

    private String usernameOrEmail;
    private String password;


}
