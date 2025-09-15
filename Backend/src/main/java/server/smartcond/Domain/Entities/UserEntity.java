package server.smartcond.Domain.Entities;


import jakarta.persistence.*;
import lombok.*;
import server.smartcond.Domain.Utils.RoleEnum;


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
    private String document;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = true)
    private String direction;

    @Column(nullable = true)
    private String tower;

    @Column(nullable = true)
    private String apartment;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
}
