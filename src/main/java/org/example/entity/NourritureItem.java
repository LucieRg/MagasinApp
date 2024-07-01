package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@SuperBuilder

public class NourritureItem extends InventaireItem {
    private String datePeremption;

    
}
