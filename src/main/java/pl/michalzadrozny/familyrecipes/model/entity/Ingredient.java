package pl.michalzadrozny.familyrecipes.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Min(value = 0, message = "Ilość nie może być mniejsza niż 0")
    private double amount;

    @NotBlank(message = "Nazwa przepisu nie może być pusta")
    private String name;
    private String unit;
}
