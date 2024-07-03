package org.example.controller;

import org.example.entity.Client;
import org.example.entity.InventaireItem;
import org.example.entity.Vente;
import org.example.service.VenteService;
import org.example.service.ClientService;
import org.example.service.InventaireItemService;
import org.example.util.StatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VenteUI {
    private final Scanner scanner;
    private final VenteService venteService;
    private final ClientService clientService;
    private final InventaireItemService inventaireItemService;

    public VenteUI(Scanner scanner) {
        this.scanner = scanner;
        this.venteService = new VenteService();
        this.clientService = new ClientService();
        this.inventaireItemService = new InventaireItemService();
    }

    public void gererVentes() {
        boolean retourMenu = false;
        while (!retourMenu) {
            afficherMenu();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    effectuerVente();
                    break;
                case "2":
                    consulterVente();
                    break;
                case "3":
                    mettreAJourVente();
                    break;
                case "4":
                    supprimerVente();
                    break;
                case "5":
                    consulterVenteParStatut();
                    break;
                case "6":
                    retourMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void afficherMenu() {
        System.out.println("\n=== Gestion des Ventes ===");
        System.out.println("1. Effectuer une Vente");
        System.out.println("2. Consulter une Vente");
        System.out.println("3. Mettre à jour une Vente");
        System.out.println("4. Supprimer une Vente");
        System.out.println("5. Consulter les Ventes par Statut");
        System.out.println("6. Retour au Menu Principal");
        System.out.print("Choix : ");
    }

    private void effectuerVente() {
        System.out.println("\n=== Effectuer une Vente ===");


        System.out.print("Entrez l'ID du client : ");
        long clientId = scanner.nextLong();
        scanner.nextLine();
        Client client = clientService.findClientById(clientId);
        if (client == null) {
            System.out.println("Aucun client trouvé avec l'ID " + clientId);
            return;
        }


        Vente vente = new Vente();
        List<InventaireItem> items = new ArrayList<>();


        while (true) {
            System.out.print("Entrez l'ID de l'article à vendre (ou '0' pour terminer) : ");
            long id = scanner.nextLong();
            if (id == 0) {
                break;
            }
            scanner.nextLine();

            InventaireItem articleAVendre = inventaireItemService.findItem(id);
            if (articleAVendre == null) {
                System.out.println("Aucun article trouvé avec l'ID " + id);
                continue;
            }

            System.out.print("Quantité à vendre : ");
            int quantiteVendue = scanner.nextInt();
            scanner.nextLine();

            if (quantiteVendue > articleAVendre.getQuantite()) {
                System.out.println("Quantité insuffisante en stock.");
                continue;
            }

            articleAVendre.setQuantite(articleAVendre.getQuantite() - quantiteVendue);
            items.add(articleAVendre);
        }


        boolean success = venteService.saveVente(vente, client, StatusType.FINALISEE, items);
        if (success) {
            System.out.println("Vente effectuée avec succès !");
        } else {
            System.out.println("Erreur lors de la vente.");
        }
    }

    private void consulterVente() {
        System.out.print("Entrez l'ID de la vente à consulter : ");
        long venteId = scanner.nextLong();
        scanner.nextLine();

        Vente vente = venteService.findById(venteId);
        if (vente != null) {
            System.out.println("Détails de la vente : " + vente);
        } else {
            System.out.println("Aucune vente trouvée avec l'ID " + venteId);
        }
    }

    private void mettreAJourVente() {
        System.out.print("Entrez l'ID de la vente à mettre à jour : ");
        long venteId = scanner.nextLong();
        scanner.nextLine();

        Vente vente = venteService.findById(venteId);
        if (vente == null) {
            System.out.println("Aucune vente trouvée avec l'ID " + venteId);
            return;
        }


        System.out.print("Entrez le nouveau statut (EN_ATTENTE, FINALISEE, ANNULEE) : ");
        String nouveauStatut = scanner.nextLine();
        vente.setStatusType(StatusType.valueOf(nouveauStatut));

        boolean success = venteService.updateVente(vente);
        if (success) {
            System.out.println("Vente mise à jour avec succès !");
        } else {
            System.out.println("Erreur lors de la mise à jour de la vente.");
        }
    }

    private void supprimerVente() {
        System.out.print("Entrez l'ID de la vente à supprimer : ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Vente venteASupprimer = venteService.findById(id);
        if (venteASupprimer == null) {
            System.out.println("Aucune vente trouvée avec l'ID " + id);
            return;
        }

        System.out.print("Etes-vous sûr de vouloir supprimer la vente ?");
        System.out.println(venteASupprimer);
        System.out.print("Confirmer la suppression (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            boolean success = venteService.deleteVente(venteASupprimer);
            if (success) {
                System.out.println("Vente supprimée avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression de la vente.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    private void consulterVenteParStatut() {
        System.out.print("Entrez le statut des ventes à consulter (EN_COURS, FINALISEE, ANNULEE) : ");
        String statut = scanner.nextLine();

        List<Vente> ventes = venteService.findByStatus(StatusType.valueOf(statut));
        if (ventes.isEmpty()) {
            System.out.println("Aucune vente trouvée avec le statut " + statut);
        } else {
            System.out.println("Ventes avec le statut " + statut + " : ");
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        }
    }
}
