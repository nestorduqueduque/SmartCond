package server.smartcond.Domain.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CeladorRequestDTO {

    private String name;
    private String lastName;
    private String document;
    private String email;
    private String password;
    private String phoneNumber;
    private String direction;
}
