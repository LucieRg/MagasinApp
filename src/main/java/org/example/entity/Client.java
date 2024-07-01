package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "client_id")
    private Long id;
    private String nom;
    private String email;



    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Vente> ventes;


}