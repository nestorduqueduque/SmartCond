package server.smartcond.Domain.Dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private Long document;
    private String email;
    private Long phoneNumber;
    private Integer tower;
    private Integer apartment;
    private boolean isEnabled;
    private String role;
}
