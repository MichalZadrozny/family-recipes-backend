package pl.michalzadrozny.familyrecipes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nutrients")
public class Nutrients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private Long id;

    private double calories;
    private double proteins;
    private double carbs;
    private double fats;
}
