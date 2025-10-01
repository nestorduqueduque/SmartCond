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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private Long document;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(nullable = true)
    private String direction;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id", nullable = true)
    private ApartmentEntity apartment;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<NoticeEntity> notices = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
}
