package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Inheritance(strategy = InheritanceType.JOINED)
public class InventaireItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String description;
    protected double prix;
    protected int quantite;
    protected String restockDate;


    @ManyToOne
    protected Vente vente;


}






