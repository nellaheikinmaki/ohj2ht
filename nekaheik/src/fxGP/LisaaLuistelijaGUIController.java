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

import GP.Luistelija;

/**
 * @author Nella
 * @version 12.6.2020
 * Luokka luistelijan lisäämisen käyttöliittymän hoitamiseksi.
 */
public class LisaaLuistelijaGUIController implements ModalControllerInterface<Luistelija>, Initializable {

    @FXML private TextArea editLuistelijanNimi;
    @FXML private TextArea editLuistelijanMaa;
    @FXML private TextArea editLuistelijanIka;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta(); 
    }
    
    // Tallentaa uuden luistelijan.
    @FXML private void handleOkLuistelija() {
        tallennaLuistelija();
    }

    // ==============================================================
    private Luistelija valittuLuistelija;
    
    /**
     * Tyhjentää luistelijan lisäysikkunan kentät.
     */
    public void tyhjenna() {
        editLuistelijanNimi.setText("");
        editLuistelijanMaa.setText("");
        editLuistelijanIka.setText("");
    }
    
    /**
     * Alustaa luistelijan lisäysikkunan.
     */
    protected void alusta() {
        editLuistelijanNimi.setOnKeyReleased(e -> kasitteleMuokkaaLuistelijaa((TextArea)(e.getSource())));
        editLuistelijanMaa.setOnKeyReleased(e -> kasitteleMuokkaaLuistelijaa((TextArea)(e.getSource())));
        editLuistelijanIka.setOnKeyReleased(e -> kasitteleMuokkaaLuistelijaa((TextArea)(e.getSource())));
    }

    /**
     * Käsittelee luistelijan muokkauksen.
     * @param textArea tekstikenttä jota muokataan
     */
    protected void kasitteleMuokkaaLuistelijaa(TextArea textArea) {
        if (valittuLuistelija == null) return;
        String id = textArea.getId();
        String teksti = textArea.getText();
        valittuLuistelija.aseta(id, teksti);
    }

    @Override
    public void setDefault(Luistelija oletus) {
        valittuLuistelija = oletus;
        naytaLuistelija(valittuLuistelija);
    }

    @Override
    public Luistelija getResult() {
        return valittuLuistelija;
    }
    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub  
    }
    
    /**
     * Laittaa aktiivisena olevan luistelijan tiedot näkyviin.
     * @param luistelija näytettävä (käsittelyssä oleva) luistelija
     */
    public void naytaLuistelija(Luistelija luistelija) {
        if (luistelija == null) return;
        editLuistelijanNimi.setText(luistelija.getNimi());
        editLuistelijanMaa.setText(luistelija.getMaa());
        editLuistelijanIka.setText(luistelija.getIka());
    }
    
    /**
     * Hoitaaa tietojen tallentamisen ja antaa virheen jos kaikki tiedot ei ole annettu.
     */
    public void tallennaLuistelija() {
        if ( valittuLuistelija != null && 
                (valittuLuistelija.getNimi().trim().equals("") || 
                valittuLuistelija.getMaa().trim().equals("") ||
                valittuLuistelija.getIka().trim().equals(""))) {
            Dialogs.showMessageDialog("Tietoja puuttuu tai ne eivät ole kelvollisia.");
            return;
        }
        ModalController.closeStage(editLuistelijanNimi);
    }
    
    /**
     * Luodaan luistelijan tietojen täyttämiseen dialogi ja palautetaan sama tietue muutettuna tai null.
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painaa Cancel, muuten täytetty tietue
     */
    public static Luistelija kysyLuistelija(Stage modalityStage, Luistelija oletus) {
        return ModalController.<Luistelija, LisaaLuistelijaGUIController>showModal(LisaaLuistelijaGUIController.class.getResource("LisaaLuistelijaGUIView.fxml"), 
                "GP", modalityStage, oletus, null);
    }
}


