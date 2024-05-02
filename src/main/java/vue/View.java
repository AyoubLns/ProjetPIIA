package vue;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class View {
    private static final int width = 1400, height = 800;
    private final Pane pane;
    private final Tree tree;
    private Node node;

    // Constructeur prenant un objet Arbre en paramètre pour initialiser la vue.
    public View(Arbre arbre) {
        this.pane = new Pane(); // Création d'un nouveau panneau pour afficher les éléments graphiques.

        this.tree = arbre.getTree(); // Récupération de l'arbre associé à cette vue.

        // Création de liens entre le nœud central et les nœuds racines
        ArrayList<Node> roots = new ArrayList<>();
        for (Link link : tree.getLinks()) {
            if (!roots.contains(link.getSource())) {
                roots.add(link.getSource());
            }
            if (!roots.contains(link.getTarget())) {
                roots.add(link.getTarget());
            }
        }
        for (Node root : roots) {
            Link link = new Link(node, root);
            tree.getLinks().add(link);
        }

        displayTree(tree); // Affichage de l'arbre.
    }

    // Méthode pour afficher l'arbre.
    private void displayTree(Tree tree) {
        double centerX = (double) width / 2;
        double centerY = (double) height / 2;

        int nodesInCurrentLayer = 1;
        int totalNodes = 1;
        double currentRadius = 0;
        //placement des noeuds en cercle
        while (totalNodes < tree.getNodes().size()) {
            currentRadius += 100; // Augmenter le rayon pour chaque couche circulaire
            double angleStep = 2 * Math.PI / nodesInCurrentLayer;
            for (int i = 0; i < nodesInCurrentLayer && totalNodes < tree.getNodes().size(); i++) {
                double angle = i * angleStep;
                double x = centerX + currentRadius * Math.cos(angle);
                double y = centerY + currentRadius * Math.sin(angle);
                Node node = tree.getNodes().get(totalNodes);
                node.setX(x);
                node.setY(y);
                totalNodes++;
            }
            nodesInCurrentLayer += 4; // Ajouter 4 nœuds à chaque couche circulaire suivante
        }

        // Affichage des nœuds de l'arbre.
        for (Node node : tree.getNodes()) {
            Circle circle = new Circle(node.getX(), node.getY(), 10); // Création d'un cercle pour représenter le nœud.
            circle.setFill(Color.GRAY);
            circle.setOnMouseClicked(event -> {
                if (node.getSpecies() != null) {
                    showSpeciesInfo(node.getSpecies(), node);
                } else {
                    System.out.println("Aucune espèce associée à ce nœud.");
                }
            });
            pane.getChildren().add(circle); // Ajout du cercle au panneau.

            /*
            // Ajouter une étiquette pour chaque nœud avec le nom de l'espèce
            Label label = new Label(node.getSpecies().getName());
            label.setTextFill(Color.BLACK);
            label.setStyle("-fx-font-size: 20px;");
            double labelX = node.getX() - circle.getRadius() * Math.cos(node.getAngle());
            double labelY = node.getY() - circle.getRadius() * Math.sin(node.getAngle()) + 10;
            label.setLayoutX(labelX);
            label.setLayoutY(labelY);

            pane.getChildren().add(label);
            */
        }

        // Affichage des liens entre les nœuds de l'arbre.
        for (Link link : tree.getLinks()) {
            if (link.getSource() != null && link.getTarget() != null) {
                double startX = link.getSource().getX();
                double startY = link.getSource().getY();
                double endX = link.getTarget().getX();
                double endY = link.getTarget().getY();

                Line line = new Line(startX, startY, endX, endY); // Création d'une ligne pour représenter le lien.
                line.setStrokeWidth(2); // Définition de l'épaisseur de la ligne.
                line.setStroke(Color.GRAY); // Définition de la couleur de la ligne.
                pane.getChildren().add(line); // Ajout de la ligne au panneau.
            }
        }

        // Ajout de la légende
        double legendX = -50; // Position initiale sur l'axe x
        double legendY = -200; // Position initiale sur l'axe y
        double offsetY = 30; // Décalage vertical entre chaque cercle et son étiquette

        // Espece non decouverte
        Circle legend1 = new Circle(legendX, legendY, 10); // Cercle gris
        legend1.setFill(Color.GRAY);
        Label label1 = new Label("Espèce non découverte");
        label1.setLayoutX(legendX + 20); // Ajustement de la position horizontale de l'étiquette
        label1.setLayoutY(legendY -10); // Position verticale de l'étiquette

        // Espece cliquee
        Circle legend2 = new Circle(legendX, legendY + offsetY, 10); // Cercle cyan
        legend2.setFill(Color.CYAN);
        Label label2 = new Label("Espèce cliquée");
        label2.setLayoutX(legendX + 20); // Ajustement de la position horizontale de l'étiquette
        label2.setLayoutY(legendY + offsetY -10); // Position verticale de l'étiquette

        // Espece decouverte
        Circle legend3 = new Circle(legendX, legendY + 2 * offsetY, 10); // Cercle bleu clair
        legend3.setFill(Color.LIGHTBLUE);
        Label label3 = new Label("Espèce découverte");
        label3.setLayoutX(legendX + 20); // Ajustement de la position horizontale de l'étiquette
        label3.setLayoutY(legendY + 2 * offsetY -10); // Position verticale de l'étiquette

        // Espece est un fils direct
        Circle legend4 = new Circle(legendX, legendY + 3 * offsetY, 10);
        legend4.setFill(Color.LIGHTCORAL);
        Label label4 = new Label("Espèce est un fils direct");
        label4.setLayoutX(legendX + 20); // Ajustement de la position horizontale de l'étiquette
        label4.setLayoutY(legendY + 3 * offsetY - 10); // Position verticale de l'étiquette

        // Espece est un fils
        Circle legend5 = new Circle(legendX, legendY + 4 * offsetY, 10);
        legend5.setFill(Color.LIGHTSALMON);
        Label label5 = new Label("Espèce est un fils");
        label5.setLayoutX(legendX + 20); // Ajustement de la position horizontale de l'étiquette
        label5.setLayoutY(legendY + 4 * offsetY - 10); // Position verticale de l'étiquette

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(legendX - 20.0, legendY - 20.0,
                legendX - 20.0, legendY + 5 * offsetY - 10.0,
                legendX + 200.0, legendY + 5 * offsetY - 10.0,
                legendX + 200.0, legendY - 20.0);
        polygon.setFill(Color.WHITE);
        pane.getChildren().add(polygon);
        pane.getChildren().addAll(legend1, legend2, legend3, legend4, legend5, label1, label2, label3, label4, label5);
    }



    private void showSpeciesInfo(Species species, Node node) {
        // Mettre à jour la couleur du nœud cliqué
        Circle clickedCircle = getCircleForNode(node);
        if (clickedCircle != null) {
                clickedCircle.setFill(Color.CYAN);
                node.estCyan();
        }

        // Si le nœud a des enfants, les afficher en rouge
        if (!node.getChildren().isEmpty()) {
            for (Node child : node.getChildren()) {
                Circle childCircle = getCircleForNode(child);
                if (childCircle != null) {
                    childCircle.setFill(Color.LIGHTCORAL);
                }
            }
        }

        // Si le nœud a des enfants d'enfants, les afficher en rouge clair
        if (!node.getChildrenChild().isEmpty()) {
            for (Node child : node.getChildrenChild()) {
                Node parent = child.getParent();
                if (!parent.equals(node)) {
                    Circle childCircle = getCircleForNode(child);
                    if (childCircle != null) {
                        childCircle.setFill(Color.LIGHTSALMON);
                    }
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations sur l'Espèce");
        alert.setHeaderText("Détails de l'Espèce");

        String contentText = "Id de l'Espèce: " + species.getId() +
                "\nNom de l'Espèce: " + species.getName();
        if (species.isExtinct()) {
            contentText += "\nL'espèce est en voie d'extinction";
        } else {
            contentText += "\nL'espèce n'est pas en voie d'extinction";
        }
        if (species.getConfidence().equals("0")) {
            contentText += "\nL'information est peu fiable";
        } else if (species.getConfidence().equals("1")) {
            contentText += "\nL'information est moyennement fiable";
        } else {
            contentText += "\nL'information est fiable";
        }
        if (species.getPhylesis().equals("0")) {
            contentText += "\nL'espèce est monophylétique";
        } else if (species.getPhylesis().equals("1")) {
            contentText += "\nL'espèce est paraphylétique";
        } else {
            contentText += "\nL'espèce est polyphylétique";
        }
        contentText += "\nNombre de sous-espèces : " + species.getNbEnfant();

        Hyperlink link = new Hyperlink("Plus de détails sur l'espèce");
        link.setOnAction(e -> {
            try {
                String url = "http://tolweb.org/" + species.getName() + "/" + species.getId();
                java.awt.Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        VBox vbox = new VBox(new Text(contentText), link);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        alert.getDialogPane().setContent(vbox);
        alert.showAndWait();


        // Réinitialisation de la couleur des fils à gris
        for (Node child : node.getChildren()) {
            Circle childCircle = getCircleForNode(child);
            if (childCircle != null && !child.aEteCyan()) {
                childCircle.setFill(Color.GRAY);
            }else{
                assert childCircle != null;
                childCircle.setFill(Color.LIGHTBLUE);
            }
        }

        // Réinitialisation de la couleur des enfants d'enfants à gris
        if (!node.getChildrenChild().isEmpty()) {
            for (Node child : node.getChildrenChild()) {
                Circle childCircle = getCircleForNode(child);
                if (childCircle != null && !child.aEteCyan()) {
                    childCircle.setFill(Color.GRAY);
                }else{
                    assert childCircle != null;
                    childCircle.setFill(Color.LIGHTBLUE);
                }
            }
        }

        // Réinitialisation de la couleur du nœud cliqué à bleu clair
        if (clickedCircle != null) {
            clickedCircle.setFill(Color.LIGHTBLUE);
        }
    }


    // Méthode utilitaire pour obtenir le cercle associé à un nœud
    private Circle getCircleForNode(Node node) {
        for (Node n : tree.getNodes()) {
            if (n.equals(node)) {
                for (javafx.scene.Node child : pane.getChildren()) {
                    if (child instanceof Circle && ((Circle) child).getCenterX() == n.getX() && ((Circle) child).getCenterY() == n.getY()) {
                        return (Circle) child;
                    }
                }
            }
        }
        return null;
    }

    // Méthode pour obtenir le panneau contenant l'arbre.
    public Parent getPane() {
        return pane;
    }


}