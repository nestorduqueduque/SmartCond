package server.smartcond.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;
import server.smartcond.Domain.Utils.RoleEnum;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartments")
public class ApartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tower_id")
    private TowerEntity tower;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<UserEntity> residents = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<VehicleEntity> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<VisitorEntity> visitors = new ArrayList<>();

}
