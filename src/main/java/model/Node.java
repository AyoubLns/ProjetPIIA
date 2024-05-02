package model;

import java.util.ArrayList;

public class Node {
    private final String name;
    private Species species;
    private double x;
    private double y;
    private Node parent;
    boolean cyan = false;
    private final ArrayList<Node> children = new ArrayList<>();

    private final ArrayList<Node> childrenChild = new ArrayList<>();

    public Node(String name, Species species, double x, double y) {
        this.name = name;
        this.species = species;
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addChild(Node child) {
        children.add(child);
        child.setParent(this);
    }

    public void addChildrenChild(Node child) {
        for(Node node : child.getChildren()) {
            if(node.getChildren().isEmpty()) {
                childrenChild.add(node);
            }
            childrenChild.add(child);
            addChildrenChild(node);
        }
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public ArrayList<Node> getChildrenChild() {
        return childrenChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void estCyan() {
         cyan = true;
    }

    public boolean aEteCyan() {
        return cyan;
    }
}
