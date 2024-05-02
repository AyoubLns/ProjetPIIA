package model;

public class Species {
    private final String name;
    private final int id;
    private final boolean extinct; // Espèce éteinte
    private final String confidence; // Confiance
    private final String phylesis; // Phylésie
    private final int nbEnfant; // Nombre d'enfants

    public Species(int id, String name, int nbSousEspece, boolean extinct, String confidence, String phylesis) {
        this.name = name;
        this.id = id;
        this.extinct = extinct;
        this.confidence = confidence;
        this.phylesis = phylesis;
        this.nbEnfant = nbSousEspece;

    }
    // Getters and setters

    public int getNbEnfant() {
        return nbEnfant;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isExtinct() {
        return extinct;
    }

    public String getConfidence() {
        return confidence;
    }

    public String getPhylesis() {
        return phylesis;
    }

}
