package fxGP;

   
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import GP.Kilpailu;

/**
 * @author Nella
 * @version 12.6.2020
 * Luokka kilpailun lisäämisen käyttöliittymän hoitamiseksi.
 */
public class LisaaKilpailuGUIController implements ModalControllerInterface<Kilpailu>, Initializable {

    @FXML private TextArea editKilpailunNimi;
    @FXML private TextArea editKilpailunMaa;
    @FXML private TextArea editKilpailunViikko;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    // Tallentaa kilpailun.
    @FXML private void handleOkKilpailu() {
        tallennaKilpailu();
    }

    // ==============================================================
    private Kilpailu valittuKilpailu;
    
    /**
     * Tyhjentää kilpailun lisäysikkunan kentät.
     */
    public void tyhjenna() {
        editKilpailunNimi.setText("");
        editKilpailunMaa.setText("");
        editKilpailunViikko.setText("");
    }
    
    /**
     * Alustaa kilpailun lisäysikkunan.
     */
    protected void alusta() {
        editKilpailunNimi.setOnKeyReleased(e -> kasitteleMuokkaaKilpailua((TextArea)(e.getSource())));
        editKilpailunMaa.setOnKeyReleased(e -> kasitteleMuokkaaKilpailua((TextArea)(e.getSource())));
        editKilpailunViikko.setOnKeyReleased(e -> kasitteleMuokkaaKilpailua((TextArea)(e.getSource())));
    }

    /**
     * Käsittelee kilpailun muokkaukset.
     * @param textArea tekstikenttä jota muokataan
     */
    protected void kasitteleMuokkaaKilpailua(TextArea textArea) {
        if (valittuKilpailu == null) return;
        String id = textArea.getId();
        String teksti = textArea.getText();
        valittuKilpailu.aseta(id, teksti);
    }

    @Override
    public void setDefault(Kilpailu oletus) {
        valittuKilpailu = oletus;
        naytaKilpailu(valittuKilpailu);
    }

    @Override
    public Kilpailu getResult() {
        return valittuKilpailu;
    }
    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub  
    }
    
    /**
     * Näyttää aktiivisena olevan kilpailun tiedot.
     * @param kilpailu näytettävä (käsittelyssä oleva) kilpailu
     */
    public void naytaKilpailu(Kilpailu kilpailu) {
        if (kilpailu == null) return;
        editKilpailunNimi.setText(kilpailu.getNimi());
        editKilpailunMaa.setText(kilpailu.getMaa());
        editKilpailunViikko.setText(kilpailu.getViikko());
    }
    
    /**
     * Hoitaaa tietojen tallentamisen ja antaa virheen jos kaikki tiedot ei ole annettu.
     */
    public void tallennaKilpailu() {
        if ( valittuKilpailu != null && 
                (valittuKilpailu.getNimi().trim().equals("") || 
                valittuKilpailu.getMaa().trim().equals("") ||
                valittuKilpailu.getViikko().trim().equals(""))) {
            Dialogs.showMessageDialog("Tietoja puuttuu tai ne eivät ole kelvollisia.");
            return;
        }
        ModalController.closeStage(editKilpailunNimi);
    }
    
    /**
     * Luodaan kilpailun tietojen täyttämiseen dialogi ja palautetaan sama tietue muutettuna tai null.
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painaa Cancel, muuten täytetty tietue
     */
    public static Kilpailu kysyKilpailu(Stage modalityStage, Kilpailu oletus) {
        return ModalController.<Kilpailu, LisaaKilpailuGUIController>showModal(LisaaKilpailuGUIController.class.getResource("LisaaKilpailuGUIView.fxml"), 
                "GP", modalityStage, oletus, null);
    }
}


