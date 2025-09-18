package server.smartcond.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.Utils.VehicleType;


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

    @Column
    private String brand;

    @Column
    private String model;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartment;

}
