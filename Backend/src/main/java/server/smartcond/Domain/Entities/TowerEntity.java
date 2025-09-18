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
@Table(name = "towers")
public class TowerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer number;

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL)
    private List<ApartmentEntity> apartments = new ArrayList<>();
}
