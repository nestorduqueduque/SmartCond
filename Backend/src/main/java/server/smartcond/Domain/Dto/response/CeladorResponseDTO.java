package server.smartcond.Domain.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CeladorResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String document;
    private String email;
    private String phoneNumber;
    private boolean isEnabled;
    private String role;

}
