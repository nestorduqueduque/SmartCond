package server.smartcond.Domain.Dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.smartcond.Domain.Utils.VehicleOwnerType;
import server.smartcond.Domain.Utils.VehicleType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {

    private Long id;
    private String plate;
    private VehicleType type;
    private VehicleOwnerType ownerType;
    private String brand;
    private String model;
    private Integer apartment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredAt;
}
