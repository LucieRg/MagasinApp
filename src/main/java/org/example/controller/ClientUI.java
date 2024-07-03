package org.example.controller;

import org.example.entity.Client;
import org.example.service.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientUI {
    private final Scanner scanner;
    private final ClientService clientService;

    public ClientUI(Scanner scanner) {
        this.scanner = scanner;
        this.clientService = new ClientService();
    }

    public void gererClients() {
        boolean retourMenu = false;
        while (!retourMenu) {
            afficherMenu();
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

    private void afficherMenu() {
        System.out.println("\n=== Gestion des Clients ===");
        System.out.println("1. Ajouter un Client");
        System.out.println("2. Modifier un Client");
        System.out.println("3. Supprimer un Client");
        System.out.println("4. Consulter les Clients");
        System.out.println("5. Retour au Menu Principal");
        System.out.print("Choix : ");
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
    }

    private void consulterClients() {
        System.out.println("\n=== Liste des Clients ===");
        List<Client> clients = clientService.findAllClient();
        for (Client client : clients) {
            System.out.println(client);
        }
    }
}
