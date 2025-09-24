package server.smartcond.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;
import server.smartcond.Domain.Utils.PackageStatus;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "packages")
public class PackageEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDateTime receivedAt;

    @Column
    private LocalDateTime deliveredAt;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PackageStatus status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartment;

}
