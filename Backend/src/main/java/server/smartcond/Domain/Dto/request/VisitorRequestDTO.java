package server.smartcond.Domain.Dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorRequestDTO {
    private String name;
    private Long document;
    private String reason;
    private Integer apartment;
}
