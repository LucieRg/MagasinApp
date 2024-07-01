package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.util.StatusType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn (name = "vente_id")
    protected Long id;
    private LocalDate venteDate;
   @Enumerated(EnumType.STRING)
   private StatusType statusType;


    @ManyToOne
    private Client client ;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL)
    private List<InventaireItem>  inventaireItem;

    @Override
    public String toString() {
        return "Vente{" +
                "id=" + id +
                ", venteDate=" + venteDate +
                ", statusType=" + statusType +
                '}';
    }
}
