package server.smartcond.Domain.Dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.smartcond.Domain.Utils.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    private String plate;
    private VehicleType type;
    private String brand;
    private String model;
    private Integer apartment;
}
