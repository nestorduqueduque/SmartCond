package server.smartcond.Domain.Dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentRequestDTO {

    private String name;
    private String lastName;
    private Long document;
    private String email;
    private String password;
    private Long phoneNumber;
    private Integer apartment;

}
