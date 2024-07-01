package org.example;

import org.example.controller.ConsoleController;
import org.example.entity.ElectroniqueItem;
import org.example.entity.ModeItem;
import org.example.entity.NourritureItem;
import org.example.entity.Vente;
import org.example.service.ClientService;
import org.example.service.InventaireItemService;
import org.example.service.VenteService;

import java.time.LocalDate;

import static org.example.util.CategorieType.HOMME;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ConsoleController consoleController = new ConsoleController();
        consoleController.run();
    }
}
