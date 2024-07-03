package org.example.service;

import org.example.entity.InventaireItem;
import org.example.repository.InventaireITemRepository;

import java.util.List;

public class InventaireItemService {
    private InventaireITemRepository inventaireRepository = new InventaireITemRepository();

    public boolean addItem(InventaireItem item) {
        return inventaireRepository.save(item);

    }

    public boolean updateItem(InventaireItem item) {
        return inventaireRepository.update(item);

    }

    public boolean deleteItem(InventaireItem item) {
        return inventaireRepository.delete(item);

    }

    public InventaireItem findItem(Long id) {

        return inventaireRepository.findById(InventaireItem.class, id);
    }

    public List<InventaireItem> findAll() {
        return inventaireRepository.findAll(InventaireItem.class);
    }

}
