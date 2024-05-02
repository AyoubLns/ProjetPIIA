package model;

public class Link {
    private final Node source; // Noeud source du lien.
    private Node node; // Noeud cible du lien.

    // Constructeur prenant les noeuds source et cible en paramètres pour initialiser le lien.
    public Link(Node source, Node node) {
        this.source = source;
        this.node = node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    // Getters pour obtenir les noeuds source et cible du lien.
    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return node;
    }

    // Méthode pour afficher une représentation textuelle du lien.
    public String toString() {
        return "Link [source=" + source + ", node=" + node + "]";
    }

    // Méthode pour vérifier l'égalité entre deux liens.
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Link other = (Link) obj;
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        if (node == null) {
            return other.node == null;
        } else return node.equals(other.node);
    }

    // Méthode pour calculer le code de hachage du lien.
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }
}
