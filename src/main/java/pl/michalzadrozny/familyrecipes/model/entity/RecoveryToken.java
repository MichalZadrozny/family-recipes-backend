package pl.michalzadrozny.familyrecipes.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class RecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne
    private AppUser appUser;
    private String value;

    public RecoveryToken(AppUser appUser, String value) {
        this.appUser = appUser;
        this.value = value;
    }
}