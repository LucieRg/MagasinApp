package org.example.controller;

import org.example.entity.*;
import org.example.service.*;
import org.example.util.CategorieType;
import org.example.util.StatusType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InventaireItemService inventaireItemService = new InventaireItemService();
    private static final VenteService venteService = new VenteService();
    private static final ClientService clientService = new ClientService();

    public void run() {
        List<InventaireItem> items = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    gererInventaire();
                    break;
                case "2":
                    gererVentes();
                    break;
                case "3":
                    gererClients();
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

    private void gererInventaire() {
        boolean retourMenu = false;
        while (!retourMenu) {
            System.out.println("\n=== Gestion de l'Inventaire ===");
            System.out.println("1. Ajouter un Article");
            System.out.println("2. Modifier un Article");
            System.out.println("3. Supprimer un Article");
            System.out.println("4. Consulter un Article");
            System.out.println("5. Restocker un Article");
            System.out.println("6. Retour au Menu Principal");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    ajouterArticle();
                    break;
                case "2":
                    modifierArticle();
                    break;
                case "3":
                    supprimerArticle();
                    break;
                case "4":
                    consulterArticles();
                    break;
                case "5":
                    restockerArticle();
                    break;
                case "6":
                    retourMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterArticle() {
        System.out.println("\n=== Ajout d'un nouvel Article ===");
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Prix : ");
        double prix = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Choisir le type d'article :");
        System.out.println("1. Mode");
        System.out.println("2. Electronique");
        System.out.println("3. Nourriture");
        System.out.print("Type : ");
        int type = scanner.nextInt();
        scanner.nextLine();

        InventaireItem nouvelArticle = null;

        switch (type) {
            case 1:
                nouvelArticle = new ModeItem();
                System.out.print("Catégorie (HOMME, FEMME, ENFANT) : ");
                String categorieStr = scanner.nextLine();
                CategorieType categorieType = CategorieType.valueOf(categorieStr.toUpperCase());
                ((ModeItem) nouvelArticle).setCategorieType(categorieType);
                System.out.print("Taille : ");
                ((ModeItem) nouvelArticle).setTaille(scanner.nextLine());
                break;
            case 2:
                nouvelArticle = new ElectroniqueItem();
                System.out.print("Durée de Batterie : ");
                int dureeBatterie = scanner.nextInt();
                ((ElectroniqueItem) nouvelArticle).setDureeBatterie(dureeBatterie);
                break;
            case 3:
                nouvelArticle = new NourritureItem();
                System.out.print("Date de Péremption (format JJ-MM-AAAA) : ");
                String datePeremption = scanner.nextLine();
                ((NourritureItem) nouvelArticle).setDatePeremption(datePeremption);
                break;
            default:
                System.out.println("Type d'article non reconnu.");
                return;
        }

        nouvelArticle.setDescription(description);
        nouvelArticle.setPrix(prix);
        nouvelArticle.setRestockDate("20-06-2024");

        if (inventaireItemService.addItem(nouvelArticle)) {
            System.out.println("Article ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de l'article.");
        }
        scanner.nextLine();
    }

    private void modifierArticle() {
        System.out.println("\n=== Modification d'un Article ===");
        System.out.print("Entrez l'ID de l'article à modifier : ");
        long id = scanner.nextLong();
        scanner.nextLine();

        InventaireItem articleAModifier = inventaireItemService.findItem(id);
        if (articleAModifier == null) {
            System.out.println("Aucun article trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Article actuel :");
        System.out.println(articleAModifier);

        System.out.print("Nouvelle description (ou laisser vide pour garder inchangé) : ");
        String nouvelleDescription = scanner.nextLine();
        if (!nouvelleDescription.isEmpty()) {
            articleAModifier.setDescription(nouvelleDescription);
        }

        System.out.print("Nouveau prix (ou 0.0 pour garder inchangé) : ");
        double nouveauPrix = scanner.nextDouble();
        scanner.nextLine();
        if (nouveauPrix != 0.0) {
            articleAModifier.setPrix(nouveauPrix);
        }

        boolean success = inventaireItemService.updateItem(articleAModifier);
        if (success) {
            System.out.println("Article modifié avec succès !");
        } else {
            System.out.println("Erreur lors de la modification de l'article.");
        }
        scanner.nextLine();
    }

    private void supprimerArticle() {
        System.out.println("\n=== Suppression d'un Article ===");
        System.out.print("Entrez l'ID de l'article à supprimer : ");
        long id = scanner.nextLong();
        scanner.nextLine();

        InventaireItem articleASupprimer = inventaireItemService.findItem(id);
        if (articleASupprimer == null) {
            System.out.println("Aucun article trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Êtes-vous sûr de vouloir supprimer cet article ?");
        System.out.println(articleASupprimer);
        System.out.print("Confirmer la suppression (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            boolean success = inventaireItemService.deleteItem(articleASupprimer);
            if (success) {
                System.out.println("Article supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression de l'article.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
        scanner.nextLine();
    }

    private void consulterArticles() {
        System.out.println("\n=== Liste des Articles ===");
        System.out.println("Liste article par son Id :");
        Long id = scanner.nextLong();
        List<InventaireItem> items = inventaireItemService.findAll();
        for (InventaireItem item : items) {
            System.out.println(item);
        }
        scanner.nextLine();
    }

    private void restockerArticle() {
        System.out.println("\n=== Restockage d'un Article ===");
        System.out.print("Entrez l'ID de l'article à restocker : ");
        long id = scanner.nextLong();
        scanner.nextLine();

        InventaireItem articleARestocker = inventaireItemService.findItem(id);
        if (articleARestocker == null) {
            System.out.println("Aucun article trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Article actuel :");
        System.out.println(articleARestocker);

        System.out.print("Nouvelle date de restockage (JJ-MM-AAAA) : ");
        String nouvelleDateRestock = scanner.nextLine();
        articleARestocker.setRestockDate(nouvelleDateRestock);

        boolean success = inventaireItemService.updateItem(articleARestocker);
        if (success) {
            System.out.println("Article restocké avec succès !");
        } else {
            System.out.println("Erreur lors du restockage de l'article.");
        }
        scanner.nextLine();

    }


    public void gererVentes() {
        boolean retourMenu = false;
        while (!retourMenu) {
            System.out.println("\n=== Gestion des Ventes ===");
            System.out.println("1. Enregistrer une Vente");
            System.out.println("2. Chercher vente par son status");
            System.out.println("3. Supprimer vente");
            System.out.println("4. Retour au Menu Principal");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    enregistrerVente();
                    break;
                case "2":
                    chercherByStatus();
                    break;
                case "3":
                    supprimerVente();
                    break;
                case "4":
                    retourMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void enregistrerVente() {
        System.out.println("\n=== Enregistrement d'une nouvelle Vente ===");


        Vente nouvelleVente = new Vente();
        nouvelleVente.setVenteDate(LocalDate.now());


        System.out.print("Entrez le nom du client : ");
        String nomClient = scanner.nextLine();
        Client client = new Client();
        client.setNom(nomClient);

        System.out.print("Entrez le statut de la vente (en_cours, finalisee, annulee) : ");
        String statusInput = scanner.nextLine();
        StatusType statusType = StatusType.valueOf(statusInput.toUpperCase());


        List<InventaireItem> items = new ArrayList<>();
        boolean addMoreItems = true;
        while (addMoreItems) {
            System.out.print("Entrez l' id de l'article d'inventaire : ");
            Long idItem = scanner.nextLong();
            scanner.nextLine();
            InventaireItem item = new InventaireItem();
            item.setId(idItem);
            items.add(item);

            System.out.print("Ajouter un autre article ? (oui/non) : ");
            String moreItems = scanner.nextLine();
            if (!moreItems.equalsIgnoreCase("oui")) {
                addMoreItems = false;
            }
        }

        boolean success = venteService.saveVente(nouvelleVente, client, statusType, items);
        if (success) {
            System.out.println("Vente enregistrée avec succès !");
        } else {
            System.out.println("Erreur lors de l'enregistrement de la vente.");
        }
        scanner.nextLine();
    }

    private void chercherByStatus() {
        System.out.println("\n=== Recherche des Ventes par état ===");
        System.out.print("Entrez l'état de la vente (En cours, Finalisée, Annulée) : ");
        String statusInput = scanner.nextLine();
        StatusType statusType = StatusType.valueOf(statusInput.toUpperCase());

        List<Vente> ventes = venteService.findByStatus(statusType);
        for (Vente vente : ventes) {
            System.out.println(vente);
        }
        scanner.nextLine();
    }

    private void supprimerVente() {
        System.out.println("\n=== Suppression d'une Vente ===");
        System.out.print("Entrez l'ID de la vente à supprimer : ");
        long id = Long.parseLong(scanner.nextLine());

        Vente venteASupprimer = venteService.findById(id);
        if (venteASupprimer == null) {
            System.out.println("Aucun client trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Êtes-vous sûr de vouloir supprimer ce client ?");
        System.out.println(venteASupprimer);
        System.out.print("Confirmer la suppression (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            boolean success = VenteService.deleteVente(venteASupprimer);
            if (success) {
                System.out.println("Client supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression du client.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
        scanner.nextLine();
    }


    public void gererClients() {
        boolean retourMenu = false;
        while (!retourMenu) {
            System.out.println("\n=== Gestion des Clients ===");
            System.out.println("1. Ajouter un Client");
            System.out.println("2. Modifier un Client");
            System.out.println("3. Supprimer un Client");
            System.out.println("4. Consulter les Clients");
            System.out.println("5. Retour au Menu Principal");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    ajouterClient();
                    break;
                case "2":
                    modifierClient();
                    break;
                case "3":
                    supprimerClient();
                    break;
                case "4":
                    consulterClients();
                    break;
                case "5":
                    retourMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterClient() {
        System.out.println("\n=== Ajout d'un nouveau Client ===");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Adresse Email : ");
        String email = scanner.nextLine();

        Client nouveauClient = new Client();
        nouveauClient.setNom(nom);
        nouveauClient.setEmail(email);

        if (clientService.addClient(nouveauClient)) {
            System.out.println("Client ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du client.");
        }
        scanner.nextLine();
    }

    private void modifierClient() {
        System.out.println("\n=== Modification d'un Client ===");
        System.out.print("Entrez l'ID du client à modifier : ");
        long id = Long.parseLong(scanner.nextLine());

        Client clientAModifier = clientService.findClientById(id);
        if (clientAModifier == null) {
            System.out.println("Aucun client trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Client actuel :");
        System.out.println(clientAModifier);

        System.out.print("Nouveau Nom (ou laisser vide pour garder inchangé) : ");
        String nouveauNom = scanner.nextLine();
        if (!nouveauNom.isEmpty()) {
            clientAModifier.setNom(nouveauNom);
        }

        System.out.print("Nouvelle Adresse Email (ou laisser vide pour garder inchangé) : ");
        String nouvelEmail = scanner.nextLine();
        if (!nouvelEmail.isEmpty()) {
            clientAModifier.setEmail(nouvelEmail);
        }

        boolean success = clientService.updateClient(clientAModifier);
        if (success) {
            System.out.println("Client modifié avec succès !");
        } else {
            System.out.println("Erreur lors de la modification du client.");
        }
        scanner.nextLine();
    }

    private void supprimerClient() {
        System.out.println("\n=== Suppression d'un Client ===");
        System.out.print("Entrez l'ID du client à supprimer : ");
        long id = Long.parseLong(scanner.nextLine());

        Client clientASupprimer = clientService.findClientById(id);
        if (clientASupprimer == null) {
            System.out.println("Aucun client trouvé avec l'ID " + id);
            return;
        }

        System.out.println("Êtes-vous sûr de vouloir supprimer ce client ?");
        System.out.println(clientASupprimer);
        System.out.print("Confirmer la suppression (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            boolean success = clientService.deleteClient(clientASupprimer);
            if (success) {
                System.out.println("Client supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression du client.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
        scanner.nextLine();
    }

    private void consulterClients() {
        System.out.println("\n=== Liste des Clients ===");
        List<Client> clients = ClientService.findAllClient();
        for (Client client : clients) {
            System.out.println(client);
        }
        scanner.nextLine();
    }

}




