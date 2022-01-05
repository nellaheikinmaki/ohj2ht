package fxGP;
   

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * Luokka vuoden valitsemiseksi ja ohjelman käynnistämiseksi.
 * @author Nella
 * @version 2.1.2021
 *
 */
public class AloitusGUIController implements ModalControllerInterface<String>{
    
    @FXML private TextArea aloitusVuosi;
    
    private String annettuVuosi = null;
    
    // Selvä, joka avaa pääikkunan.
    @FXML private void handleSelva() {
        haeVuosi();
    }
    
    // Peruuta, sammuttaa ohjelman.
    @FXML private void handlePeruuta() {
        Dialogs.showMessageDialog("Ei osata vielä sulkea");
    }
    
    @Override
    public String getResult() {
        return annettuVuosi;
    }

    @Override
    public void handleShown() {
        aloitusVuosi.requestFocus();
    }
    
    @Override
    public void setDefault(String oletus) {
        aloitusVuosi.setText(oletus);
    }  
    
    /**
     * Hakee annetun vuoden ja sulkee aloitusikkunan.
     */
    public void haeVuosi(){
        annettuVuosi = aloitusVuosi.getText();
        ModalController.closeStage(aloitusVuosi);
    }
    
    /**
     * Avaa vuoden virheen. Tätä ei kutsuta missään nyt.
     */
    public void avaaVuosiVirhe() {
        ModalController.showModal(GPGUIController.class.getResource("VuosiVirheGUIView.fxml"),
            "Aloitus", null, "2019");
    }
    
    /**
     * Avaa aloitusikkunan.
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletusVuosi mitä vuotta käytetään oletuksena
     * @return aloitusnäkymän avaus
     */
    public static String kysyVuosi(Stage modalityStage, String oletusVuosi) {
        return ModalController.showModal(AloitusGUIController.class.getResource("AloitusGUIView.fxml"),
        "Aloitus", modalityStage, oletusVuosi);
    }
}
