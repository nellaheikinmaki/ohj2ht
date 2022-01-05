package fxGP;

import GP.GP;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * @author Nella
 * @version 12.6.2020
 *
 * Pääohjelma. Avaa pääikkunan.
 */
public class GPMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("GPGUIView.fxml"));
            final Pane root = ldr.load();
            final GPGUIController GPCtrl = (GPGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("GP.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("GP");
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !GPCtrl.voikoSulkea() ) event.consume();
            });
            
            GP gp = new GP();
            GPCtrl.setGP(gp);
            
            primaryStage.show();
            if ( !GPCtrl.avaa() ) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}
