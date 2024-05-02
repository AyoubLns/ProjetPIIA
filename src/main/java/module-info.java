module com.example.peojetpiia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.opencsv;
    requires java.desktop;

    opens com.example.peojetpiia to javafx.fxml;
    exports com.example.peojetpiia;
    exports main to javafx.graphics; // Ajoutez cette ligne
}