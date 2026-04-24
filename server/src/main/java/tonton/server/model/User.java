package tonton.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "token_version")
    private Integer tokenVersion;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Quote> quotes;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserAddress> userAddresses;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
}
