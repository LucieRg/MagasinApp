package org.example.service;

import org.example.entity.Client;
import org.example.entity.InventaireItem;
import org.example.entity.Vente;
import org.example.repository.VenteRepository;
import org.example.util.StatusType;

import java.util.List;

public class VenteService {
    private static VenteRepository venteRepository = new VenteRepository();

    public boolean saveVente(Vente vente, Client client, StatusType statusType, List<InventaireItem> items) {
        return venteRepository.saveVente(vente, client, statusType, items);

    }
    public static boolean updateVente(Vente vente) {
        return venteRepository.update(vente);
    }

    public static boolean deleteVente(Vente vente) {
        return venteRepository.delete(vente);
    }

    public List<Vente> findByStatus(StatusType statusType) {
        return venteRepository.findByStatus(statusType);
    }

    public Vente findById(Long id) {
        return venteRepository.findById(Vente.class,id);
    }
}