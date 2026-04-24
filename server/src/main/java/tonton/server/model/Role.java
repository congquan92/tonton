package tonton.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.RoleName;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private RoleName name;

    @Column(length = 255)
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> users;
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<PriceTier> priceTiers;
}
