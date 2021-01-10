package pl.michalzadrozny.familyrecipes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private Long id;
    private String name;

    @OneToOne
    private AppUser author;
    private int preparationTime; //In minutes

    @OneToOne
    private Rating rating;
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients;

    @OneToOne
    private Nutrients nutrients;
    private Diet diet;
}
