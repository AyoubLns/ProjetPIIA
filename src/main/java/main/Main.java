package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Arbre;
import model.Tree;
import vue.View;
import controller.TreeController;

public class Main extends Application {
    private static final int width = 1400, height = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Création d'un arbre à partir des fichiers CSV contenant des nœuds et des liens
        Tree tree = new Tree("PeojetPIIA/src/main/resources/treeoflife_nodes_simplified.csv", "PeojetPIIA/src/main/resources/treeoflife_links_simplified.csv");
        //Tree tree = new Tree("PeojetPIIA/src/main/resources/treeoflife_nodes.csv", "PeojetPIIA/src/main/resources/treeoflife_links.csv");

        // Création d'un Arbre basé sur le tree
        Arbre arbre = new Arbre(tree);

        // Création de la vue pour afficher l'Arbre
        View view = new View(arbre);

        // Création d'une scène pour afficher la vue
        new Scene(view.getPane(), width, height);

        // Création d'un ScrollPane pour permettre le zoom et le déplacement
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(view.getPane());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Arbre de Vie");
        primaryStage.setScene(new Scene(scrollPane, width, height));
        primaryStage.show();

        // Création et liaison du contrôleur avec le panneau et le scrollPane
        new TreeController((Pane) view.getPane(), scrollPane);
    }
}
