package org.example.controller;

import org.example.entity.*;
import org.example.service.InventaireItemService;
import org.example.util.CategorieType;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InventaireUI {
    private final Scanner scanner;
    private final InventaireItemService inventaireItemService;

    public InventaireUI(Scanner scanner) {
        this.scanner = scanner;
        this.inventaireItemService = new InventaireItemService();
    }

    public void gererInventaire() {
        boolean retourMenu = false;
        while (!retourMenu) {
            afficherMenu();
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

    private void afficherMenu() {
        System.out.println("\n=== Gestion de l'Inventaire ===");
        System.out.println("1. Ajouter un Article");
        System.out.println("2. Modifier un Article");
        System.out.println("3. Supprimer un Article");
        System.out.println("4. Consulter un Article");
        System.out.println("5. Restocker un Article");
        System.out.println("6. Retour au Menu Principal");
        System.out.print("Choix : ");
    }

    private void ajouterArticle() {
        System.out.println("\n=== Ajout d'un nouvel Article ===");
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Prix : ");
        double prix = scanner.nextDouble();
        System.out.println("Quantite : ");
        int quantite = scanner.nextInt();
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
                System.out.println("Type invalide.");
                return;
        }

        nouvelArticle.setDescription(description);
        nouvelArticle.setPrix(prix);
        nouvelArticle.setQuantite(quantite);
        nouvelArticle.setRestockDate(new Date());

        if (inventaireItemService.addItem(nouvelArticle)) {
            System.out.println("Article ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de l'article.");
        }
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

        System.out.print("Nouvelle Description (ou laisser vide pour garder inchangé) : ");
        String nouvelleDescription = scanner.nextLine();
        if (!nouvelleDescription.isEmpty()) {
            articleAModifier.setDescription(nouvelleDescription);
        }

        System.out.print("Nouveau Prix (ou laisser vide pour garder inchangé) : ");
        String nouveauPrixStr = scanner.nextLine();
        if (!nouveauPrixStr.isEmpty()) {
            double nouveauPrix = Double.parseDouble(nouveauPrixStr);
            articleAModifier.setPrix(nouveauPrix);
        }

        boolean success = inventaireItemService.updateItem(articleAModifier);
        if (success) {
            System.out.println("Article modifié avec succès !");
        } else {
            System.out.println("Erreur lors de la modification de l'article.");
        }
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
    }

    private void consulterArticles() {
        System.out.println("\n=== Liste des Articles ===");
        List<InventaireItem> articles = inventaireItemService.findAll();
        for (InventaireItem article : articles) {
            System.out.println(article);
        }
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

        System.out.print("Quantité à ajouter : ");
        int quantiteAjoutee = scanner.nextInt();
        scanner.nextLine();

        articleARestocker.setQuantite(articleARestocker.getQuantite() + quantiteAjoutee);
        articleARestocker.setRestockDate(new Date());

        boolean success = inventaireItemService.updateItem(articleARestocker);
        if (success) {
            System.out.println("Article restocké avec succès !");
        } else {
            System.out.println("Erreur lors du restockage de l'article.");
        }
    }
}
