package server.smartcond.Domain.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.smartcond.Domain.Utils.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {

    private Long id;
    private String plate;
    private VehicleType type;
    private String brand;
    private String model;
    private Integer apartment;
}
