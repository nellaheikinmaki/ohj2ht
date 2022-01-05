package fxGP;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import GP.GP;
import GP.Kilpailu;
import GP.Luistelija;
import GP.Poikkeukset;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ListChooser;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import fi.jyu.mit.ohj2.WildChars;


/**
 * @author Nella
 * @version 12.6.2020
 * Luokka järjestelmän käyttöliittymän hoitamiseksi.
 */
public class GPGUIController implements Initializable{
    
    private String oletusVuosi = "2019";
    
    @FXML private TextArea kilpailunTiedot;
    @FXML private ListChooser<Kilpailu> kilpailutLista;
    @FXML private ListChooser<Luistelija> luistelijatLista;
    
    @FXML private TextArea etsiKilpailu;
    @FXML private TextArea etsiLuistelija;
    
    @FXML private TextArea editNimi;
    @FXML private TextArea editMaa;
    @FXML private TextArea editViikko;
    @FXML private TextArea editIka;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    // Kilpailun lisäämisikkunan avaaminen, muokkaa valittua kilpailua
    @FXML private void handleAvaaLisaaKilpailu() {
        muokkaaKilpailu();
    }

    // Luistelijan lisäämisikkunan avaaminen, muokkaa valittua luistelijaa
    @FXML private void handleAvaaLisaaLuistelija() {
        muokkaaLuistelija();
    }

    // Kilpailun hakeminen
    @FXML private void handleHaeKilpailu() {
        haeKilpailu();
    }

    // Luistelijan hakeminen
    @FXML private void handleHaeLuistelija() {
        haeLuistelija();
    }
    
    // Sulkee virheilmoituksen
    @FXML private void handleVirheSelva() {
        Dialogs.showMessageDialog("Ei osata vielä sulkea virhettä");
    }
    
    // Tallennus
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    // Avaa Vuosi
    @FXML private void handleAvaa() {
        avaa();
    }

    // Tulostaa näytölle
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    // Lopettaa/Sulkee ohjelman
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    // Lisää uuden kilpailun
    @FXML private void handleLisaaKilpailu() {
        lisaaKilpailu();
    }
    
    // Lisää uuden luistelijan
    @FXML private void handleLisaaLuistelija() {
        lisaaLuistelija();
    }
    
    // Poistaa kilpailun
    @FXML private void handlePoistaKilpailu() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }
    
    // Poistaa luistelijan
    @FXML private void handlePoistaLuistelija() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }
    
   // Lähentää, tätä ei tehdä
    @FXML private void handleLahenna() {
        Dialogs.showMessageDialog("Ei osata lähentää");
    }
    
   //Loitontaa, tätä ei tehdä
    @FXML private void handleLoitonna() {
        Dialogs.showMessageDialog("Ei osata loitontaa");
    }
    
    // Apuikkuna, tätä ei tehdä
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Tässä apua, ole hyvä.");
    }
    
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Ohjelmointi 2 harjoitustyö \n Tekijä: Nella Heikinmäki \n Kesä 2020");
    }
    
    
// ==================================================================
    
// Tästä eteenpäin ei suoraan käyttöliittymään liittyvää koodia.
    
    private GP gp;
    private Kilpailu valittuKilpailu;
    private Luistelija valittuLuistelija;
    
    
    /**
     * Alustaa tiedot.
     */
    protected void alusta() {
        kilpailutLista.clear();
        luistelijatLista.clear();
        kilpailutLista.addSelectionListener(e -> naytaKilpailu());
        luistelijatLista.addSelectionListener(e -> naytaLuistelija());
        //kilpailunTiedot.clear();
        
        
    }
 
    /**
     * Näyttää kilpailun tiedot.
     */
    protected void naytaKilpailu() {
        tyhjennaTiedot();
        valittuKilpailu = kilpailutLista.getSelectedObject();
        
        if(valittuKilpailu == null) return;
        
        editNimi.setText(valittuKilpailu.getNimi());
        editMaa.setText(valittuKilpailu.getMaa());
        editViikko.setText(valittuKilpailu.getViikko());
        editIka.setText("");
        naytaLuistelijat(valittuKilpailu, -1);
    }
    
    
    private void haeKilpailu() {
        String hakuehto = etsiKilpailu.getText();
        kilpailutLista.setSelectedIndex(gp.haeKilpailu(hakuehto));
        naytaKilpailu();
    }

    /**
     * Ottaa luistelijan valituksi.
     */
    protected void naytaLuistelija() {
        tyhjennaTiedot();
        valittuLuistelija = luistelijatLista.getSelectedObject();
        if(valittuLuistelija == null) return;
        
        editNimi.setText(valittuLuistelija.getNimi());
        editMaa.setText(valittuLuistelija.getMaa());
        editViikko.setText("");
        editIka.setText(valittuLuistelija.getIka());
        
    }
    
    private void tyhjennaTiedot() {
        editNimi.clear();
        editMaa.clear();
        editViikko.clear();
        editIka.clear();
    }

    private void naytaLuistelijat(Kilpailu kilpailu, int LuistelijanId) {
        luistelijatLista.clear();
        if (kilpailu == null) return;
        
        try {
            List<Luistelija> luistelijat = gp.annaLuistelijat(kilpailu);
            if(luistelijat.size() == 0) return;
            for (Luistelija luistelija: luistelijat) {
                luistelijatLista.add(luistelija.getNimi(), luistelija);
            }
        } catch (Poikkeukset poikkeus) {
            String virhe = poikkeus.getMessage();
            if (virhe != null ) Dialogs.showMessageDialog(virhe);
        }
        if (LuistelijanId < 0) return;
        luistelijatLista.setSelectedIndex(0); // tekee halutun luistelijan aktiiviseksi
    }
    
    private void haeLuistelija() {
        String hakuehto = etsiLuistelija.getText();

        int i = 0;
        luistelijatLista.setSelectedIndex(i);
        while (luistelijatLista.getSelectedObject() != null) {
            if (WildChars.onkoSamat(luistelijatLista.getSelectedObject().getNimi(), "*" + hakuehto + "*")) {
                luistelijatLista.setSelectedIndex(i);
                break;
            }
            i++;
            luistelijatLista.setSelectedIndex(i);
        }
        naytaLuistelija();
    }

    /**
     * Avaa aloitusnäkymän.
     * @return true jos onnistui ja false jos ei onnistunut
     */ 
    public boolean avaa() {
        String uusiVuosi = AloitusGUIController.kysyVuosi(null, oletusVuosi);
        if (uusiVuosi == null) return false;
        lueTiedosto(uusiVuosi);
        return true;
    }

    
    /**
     * Alustaa tiedot lukemalla ne halutusta tiedostosta.
     * @param uusiVuosi tiedosto josta GPn tiedot luetaan
     * @return palauttaa nullin jos toimii ja virheen jos ei toimi
     */
    protected String lueTiedosto(String uusiVuosi) {
        oletusVuosi = uusiVuosi;
        try {
            gp.lueTiedostosta(oletusVuosi);
            haeKilpailu(0);
            return null;
        } catch (Poikkeukset poikkeus) {
            haeKilpailu(0);
            String virhe = poikkeus.getMessage();
            if (virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    

    /**
     * Tarkistetaan onko tallennus tehty.
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * Tallentaa GPn tiedot.
     * @return palauttaa null jos toimii ja virheen jos ei toimi
     */
    private String tallenna() {
        try {
            gp.tallenna();
            return null;
        } catch (Poikkeukset poikkeus) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + poikkeus.getMessage());
            return poikkeus.getMessage();
        }
    }

    /**
     * Hakee käyttöliittymään näkymään oikeat kilpailut ja tekee halutun kilpailun aktiiviseksi.
     * @param idNro haettavan kilpailun id numero
     */
    protected void haeKilpailu(int idNro) {
        kilpailutLista.clear();
        
        int i = 0;
        for (int j = 0; j < gp.getKilpailuja(); j++) {
            Kilpailu kilpailu = gp.annaKilpailu(j);
            if (kilpailu.getIdNro() == idNro) i = j;
            kilpailutLista.add(kilpailu.getNimi(), kilpailu);
        }
        kilpailutLista.setSelectedIndex(i); // tekee halutun kilpailun aktiiviseksi
    }

    /**
     * Avaa kilpailun lisäys/muokkausikkunan, jossa voi muokata valittua kilpailua
     */
    public void muokkaaKilpailu() {
        try {
            Kilpailu kilpailu;
            kilpailu = LisaaKilpailuGUIController.kysyKilpailu(null, valittuKilpailu.clone());
            if (kilpailu == null) return;
            gp.muokkaaTaiLisaa(kilpailu);
            haeKilpailu(kilpailu.getIdNro());
        } catch (CloneNotSupportedException poikkeus){
            Dialogs.showMessageDialog("VIRHE");
        } catch (Poikkeukset poikkeus) {
            Dialogs.showMessageDialog(poikkeus.getMessage());
        }
    }
    
    /**
     * Avaa luistelijan lisäys/muokkausikkunan, jossa voi muokata valittua luistelijaa
     */
    public void muokkaaLuistelija() {
        if (valittuKilpailu == null) return;
        try {
            Luistelija muokattava;
            if (valittuLuistelija == null) return;
           
            muokattava = LisaaLuistelijaGUIController.kysyLuistelija(null, valittuLuistelija.clone());
            if (muokattava == null) return;
            
            gp.muokkaaTaiLisaa(muokattava);
            valittuKilpailu = gp.annaKilpailu(muokattava.getKilpailuId1());
            valittuLuistelija = muokattava;
            naytaKilpailu();
        } catch (CloneNotSupportedException poikkeus){
            Dialogs.showMessageDialog("VIRHE");
        } catch (IndexOutOfBoundsException poikkeus)  {
            Dialogs.showMessageDialog(poikkeus.getMessage());
        }
    }
    
    /**
     * Avaa kilpailun lisäys/muokkausikkunan, jossa voi lisätä uuden kilpailun
     */
    public void lisaaKilpailu() {
        try {
            Kilpailu uusi = new Kilpailu();
            uusi.luo();
            uusi = LisaaKilpailuGUIController.kysyKilpailu(null, uusi);
            if (uusi == null) return;
            gp.muokkaaTaiLisaa(uusi);
            haeKilpailu(uusi.getIdNro());
        } catch (Poikkeukset poikkeus) {
            Dialogs.showMessageDialog(poikkeus.getMessage());
        }
    }
    
    /**
     * Avaa luistelijan lisäys/muokkausikkunan, jossa voi lisätä uuden luistelijan.
     * annaKilpailu metodille viedään kilpailuid1-1 koska kilpailujen indeksit alkaa 1 (ei 0, ks. kilpailu luokan metodi luo())
     */
    public void lisaaLuistelija() {
        if (valittuKilpailu == null) return;
        try {
            Luistelija uusi = new Luistelija(valittuKilpailu.getIdNro());
            uusi.luo();
           
            uusi = LisaaLuistelijaGUIController.kysyLuistelija(null, uusi);
            if (uusi == null) return;
            
            gp.muokkaaTaiLisaa(uusi);
            valittuKilpailu = gp.annaKilpailu(uusi.getKilpailuId1()-1);
            valittuLuistelija = uusi;
            naytaKilpailu();
        } catch (IndexOutOfBoundsException poikkeus)  {
            Dialogs.showMessageDialog(poikkeus.getMessage());
        }
    }
    
    /**
     * Avaa yleisen virheen.
     */
    public void avaaVirhe() {
        String oletusVirhe = "";
        ModalController.showModal(GPGUIController.class.getResource("YleinenVirheGUIView.fxml"),
            "Aloitus", null, oletusVirhe);
    }

    /**
     * Alustaa GPn, kun ohjelma avataan. Tiedostosta luette GP laitetaan tähän myöhemmin.
     * @param gp1 gp joka alustetaan
     */
    public void setGP(GP gp1) {
        this.gp = gp1;
        naytaKilpailu();
        
    }
}
