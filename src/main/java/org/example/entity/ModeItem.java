package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.util.CategorieType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@SuperBuilder

public class ModeItem extends InventaireItem {

    @Enumerated(EnumType.STRING)
    private CategorieType categorieType;
    private String taille;


    @Override
    public String toString() {
        return "ModeItem{" +
                "id=" + getId() +
                ", description='" + getDescription() + '\'' +
                ", prix=" + getPrix() +
                ", quantite=" + getQuantite() +
                ", restockDate='" + getRestockDate() + '\'' +
                ", categorieType=" + categorieType +
                ", taille='" + taille + '\'' +
                '}';
    }
}
