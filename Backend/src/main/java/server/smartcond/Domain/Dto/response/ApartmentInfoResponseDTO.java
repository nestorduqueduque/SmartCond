package server.smartcond.Domain.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentInfoResponseDTO {
    private Long id;
    private Integer number;
    private TowerInfoResponseDTO tower;
}
