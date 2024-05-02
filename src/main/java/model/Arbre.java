package model;

public class Arbre {
    private final Tree tree;

    // Constructeur prenant un objet de type Tree en paramètre pour initialiser l'arbre associé à cette instance.
    public Arbre(Tree tree) {
        this.tree = tree;
    }

    // Méthode pour obtenir l'arbre associé à cette instance.
    public Tree getTree() {
        return tree;
    }

}
