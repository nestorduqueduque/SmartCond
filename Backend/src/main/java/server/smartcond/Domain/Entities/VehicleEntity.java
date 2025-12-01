package server.smartcond.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.Utils.VehicleOwnerType;
import server.smartcond.Domain.Utils.VehicleType;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_owner")
    private VehicleOwnerType vehicleOwnerType;

    @Column
    private String brand;

    @Column
    private String model;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt; // NUEVO

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartment;

}
