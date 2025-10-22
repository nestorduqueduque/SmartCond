package server.smartcond.Domain.Dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"email", "message", "jwt", "status"})
public class LoginResponseDTO {
    private String email;
    private String message;
    private String jwt;
    private boolean status;
}