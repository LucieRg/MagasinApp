package org.example.controller;

import java.util.Scanner;

public class ConsoleController {
    private static final Scanner scanner = new Scanner(System.in);
    private final ClientUI clientUI;
    private final InventaireUI inventaireUI;
    private final VenteUI venteUI;

    public ConsoleController() {
        clientUI = new ClientUI(scanner);
        inventaireUI = new InventaireUI(scanner);
        venteUI = new VenteUI(scanner);
    }

    public void run() {
        boolean quit = false;
        while (!quit) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    inventaireUI.gererInventaire();
                    break;
                case "2":
                    venteUI.gererVentes();
                    break;
                case "3":
                    clientUI.gererClients();
                    break;
                case "4":
                    quit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
        System.out.println("Merci d'avoir utilisé notre application.");
    }

    private void afficherMenuPrincipal() {
        System.out.println("=== MENU ===");
        System.out.println("1. Gestion de l'Inventaire");
        System.out.println("2. Gestion des Ventes");
        System.out.println("3. Gestion des Clients");
        System.out.println("4. Quitter");
        System.out.print("Choix : ");
    }

    public static void main(String[] args) {
        new ConsoleController().run();
    }
}
