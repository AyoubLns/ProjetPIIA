package controller;

import javafx.scene.Cursor;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;

public class TreeController {
    private final Pane pane;
    private double[] offset;

    public TreeController(Pane pane, ScrollPane scrollPane) {
        this.pane = pane;
        attachListeners(scrollPane);
    }

    private void attachListeners(ScrollPane scrollPane) {
        // Gestion de la molette de la souris pour activer le zoom
        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            double zoomFactor = 1.05; // Facteur de zoom
            if (event.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor; // Dé-zoomer
            }
            pane.setScaleX(pane.getScaleX() * zoomFactor);
            pane.setScaleY(pane.getScaleY() * zoomFactor);
            event.consume();
        });

        pane.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                pane.setCursor(Cursor.CLOSED_HAND);
                offset = new double[]{pane.getTranslateX() - event.getSceneX(), pane.getTranslateY() - event.getSceneY()};
            }
        });

        pane.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                double offsetX = event.getSceneX() + offset[0];
                double offsetY = event.getSceneY() + offset[1];

                /*
                // Limiter le déplacement à la fenêtre
                double minX = -(int) pane.getWidth() / 2;
                double minY = -(int) (pane.getHeight() - 100) / 2;
                double maxX = (int) pane.getWidth() / 2;
                double maxY = (int) (pane.getHeight() - 100) / 2;

                offsetX = Math.max(minX, Math.min(offsetX, maxX));
                offsetY = Math.max(minY, Math.min(offsetY, maxY));
                */

                pane.setTranslateX(offsetX);
                pane.setTranslateY(offsetY);
            }
        });

        pane.setOnMouseReleased(event -> pane.setCursor(Cursor.DEFAULT));
    }
}
