package model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    private final List<Node> nodes; // Liste des noeuds de l'arbre.
    private final List<Link> links; // Liste des liens entre les noeuds.

    // Constructeur prenant en paramètres les chemins vers les fichiers contenant les données des noeuds et des liens.
    public Tree(String nodesFile, String linksFile) {
        nodes = new ArrayList<>(); // Initialisation de la liste des noeuds.
        links = new ArrayList<>(); // Initialisation de la liste des liens.
        try {
            readNodes(nodesFile); // Lecture des noeuds depuis le fichier.
            readLinks(linksFile); // Lecture des liens depuis le fichier.
        } catch (IOException e) {
            e.printStackTrace();  // Gestion appropriée des exceptions
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode privée pour lire les données des noeuds depuis un fichier CSV.
    private void readNodes(String file) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] line;
            boolean header = true; // On suppose que la première ligne est un en-tête
            while ((line = reader.readNext()) != null) {
                if (header) {
                    header = false; // On saute l'en-tête
                    continue;
                }
                try {
                    // L'exemple suppose que le format du fichier est nom, nomDeL'espece, x, y, toLorgLink, extinct, confidence, phylesis
                    String nodeName = line[0];
                    String speciesName = line[1];
                    int nbSousEspece = Integer.parseInt(line[2]);
                    int id = Integer.parseInt(line[0]); // Ajout de l'ID de l'espèce
                    double x = Double.parseDouble(line[2]);
                    double y = Double.parseDouble(line[3]);
                    boolean extinct = Boolean.parseBoolean(line[5]);
                    String confidence = line[6];
                    String phylesis = line[7];

                    Species species = new Species( id, speciesName, nbSousEspece,extinct,  confidence, phylesis); // Création de l'espèce
                    Node node = new Node(nodeName, species, x, y); // Création du noeud
                    nodes.add(node); // Ajout du noeud à la liste
                } catch (NumberFormatException e) {
                    // Log error or handle the specific case where parsing fails
                    System.err.println("Skipping line due to NumberFormatException: " + Arrays.toString(line));
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Échec de la validation du fichier CSV.", e);
        }
    }

    // Méthode privée pour lire les liens depuis un fichier CSV.
    private void readLinks(String file) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Node source = findNodeByName(line[0]); // Recherche du noeud source
                Node target = findNodeByName(line[1]); // Recherche du noeud cible
                if (source != null && target != null) {
                    links.add(new Link(source, target)); // Création du lien et ajout à la liste

                    // Stockage du fils pour le noeud source
                    source.addChild(target);
                    this.buildChildrenChildLists();
                }
            }
        }
    }

    public void buildChildrenChildLists() {
        for (Node node : nodes) {
            for (Node child : node.getChildren()) {
                node.addChildrenChild(child);
            }
        }
    }

    // Méthode privée pour trouver un noeud par son nom.
    private Node findNodeByName(String name) {
        return nodes.stream()
                .filter(node -> node.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Getters pour obtenir les listes de noeuds et de liens.
    public List<Node> getNodes() {
        return nodes;
    }

    public List<Link> getLinks() {
        return links;
    }
}
