package GP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author Nella
 * @version 13.8.2020
 * 
 * Pitää yllä varsinaista kilpailurekisteriä eli osaa lisätä ja poistaa kilpailun.
 * Lukee ja kirjoittaa kilpailuiden tiedostoon.
 * Osaa etsiä ja lajitella.
 * Avustajat: Kilpailu
 */
public class Kilpailut {
    
    private static final int MaxKilpailuja = 6;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Kilpailu kilpailut[] = new Kilpailu[MaxKilpailuja];
    private String kilpailunVuosi = "";
    private boolean muutettu = false;
    

    /**
     * Palauttaa pyydetyn kilpailun. Heittää virheen, jos liikaa kilpailuja.
     * @param i monesko kilpailu halutaan
     * @return palauttaa pyydetyn kilpailun
     * @throws IndexOutOfBoundsException heittää virheen jos ei toimi
     */
    public Kilpailu anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= lkm) throw new IndexOutOfBoundsException("Ei-kelvollinen indeksi: " + i);
        return kilpailut[i];
    }

    /**
     * Palauttaa kilpailuiden lukumäärän.
     * @return palauttaa lukumäärän
     */
    public int getLkm() {
        return lkm;
    }

    /**
     * Uuden kilpailun lisäys.
     * @param kilpailu kilpailu, joka lisätään
     * @throws Poikkeukset virhe/poikkeus
     */
    public void lisaa(Kilpailu kilpailu) throws Poikkeukset {
        if (lkm >= kilpailut.length) throw new Poikkeukset("Liikaa alkioita");
        kilpailut[lkm] = kilpailu;
        lkm++;
        muutettu = true;
    }

    /**
     * Luo kilpailutiedoston perusnimen.
     * @param nimi hakemiston perusnimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        tiedostonNimi = nimi + ".dat";
    }
    
    /**
     * Palauttaa tiedoston nimen.
     * @return tiedoston nimen
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
    }

    /**
     * Lukee kilpailut tiedostosta.
     * @throws Poikkeukset heittää virheen jos ei onnistu
     * @example
     * <pre name="test">
     * #THROWS Poikkeukset
     * #import java.io.File;
     * 
     * Kilpailut kilpailut = new Kilpailut();
     * Kilpailu skate1 = new Kilpailu();
     * Kilpailu skate2 = new Kilpailu();
     * skate1.luo();
     * skate2.luo();
     * skate1.vastaaSkateAmerica();
     * skate2.vastaaSkateAmerica();
     * String hakemisto = "testiGP";
     * String tiedostonNimi = hakemisto+"/kilpailut";
     * File tiedosto = new File(tiedostonNimi + ".dat");
     * File kansio = new File(hakemisto);
     * kansio.mkdir();
     * tiedosto.delete();
     * kilpailut.setTiedostonPerusnimi(tiedostonNimi);
     * kilpailut.lueTiedostosta(); #THROWS Poikkeukset
     * kilpailut.lisaa(skate1);
     * kilpailut.lisaa(skate2);
     * kilpailut.tallenna();
     * kilpailut = new Kilpailut();
     * kilpailut.setTiedostonPerusnimi(tiedostonNimi);
     * kilpailut.lueTiedostosta();
     * kilpailut.anna(0).toString() === skate1.toString();
     * kilpailut.anna(1).toString() === skate2.toString();
     * kilpailut.anna(2); #THROWS IndexOutOfBoundsException
     * kilpailut.lisaa(skate2);
     * kilpailut.tallenna();
     * tiedosto.delete() === true;
     * kansio.delete() === true;
     * </pre>
     */
    public void lueTiedostosta() throws Poikkeukset {
        try (BufferedReader lukija = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            kilpailunVuosi = lukija.readLine();
            if (kilpailunVuosi == null) throw new Poikkeukset("Kilpailun vuosi puuttuu");
            String rivi = lukija.readLine();
            if (rivi == null) throw new Poikkeukset("Maksimikoko puuttuu");
            
            while( (rivi = lukija.readLine()) != null) {
                rivi = rivi.trim();
                if ( rivi == "" ) continue;
                Kilpailu kilpailu = new Kilpailu();
                kilpailu.parse(rivi);
                lisaa(kilpailu);
            } 
            muutettu = false;
        } catch (FileNotFoundException poikkeus) {
            throw new Poikkeukset("Tiedosto " + getTiedostonNimi() + " ei aukea.");
        } catch (IOException poikkeus) {
            throw new Poikkeukset("Ongelmia tiedoston kanssa: " + poikkeus.getMessage());
        }
    }

    /**
     * Tallentaa kilpailut tiedostoon.
     * @throws Poikkeukset heittää virheen jos ei toimi
     */
    public void tallenna() throws Poikkeukset {
        if ( !muutettu ) return;
        
        File tiedosto = new File(getTiedostonNimi());
        
        try ( PrintWriter out = new PrintWriter(new FileWriter(tiedosto.getCanonicalPath())) ) {
            out.println(kilpailunVuosi);
            out.println(kilpailut.length);
            for (int i = 0; i < lkm; i++) {
                out.println(kilpailut[i].toString());
            }
        } catch (FileNotFoundException poikkeus) {
            throw new Poikkeukset("Tiedosto " + tiedosto.getName() + " ei aukea.");
        } catch (IOException poikkeus) {
            throw new Poikkeukset("Tiedoston " + tiedosto.getName() + " kirjoittamisessa ongelmia.");
        }
        
        muutettu = false;
    }
    
    /**
     * Tekee muutettu termin tilaan "true", jotta tallennus tapahtuu.
     */
    public void setMuutettuTrue() {
        muutettu = true;
    }
    
    /**
     * Tallentaa (lisää) tai muokkaa kilpailun tietoja.
     * @param kilpailu tallentaa uuden kilpailun tai tallentaa muutokset kilpailuun
     * @throws Poikkeukset heittää virheen jos ei voi lisätä (liikaa alkoita)
     */
    public void muokkaaTaiLisaa(Kilpailu kilpailu) throws Poikkeukset {
        int id = kilpailu.getIdNro();
        for (int i = 0; i < lkm; i++) {
            if (kilpailut[i].getIdNro() == id) {
                kilpailut[i] = kilpailu;
                muutettu = true;
                return;
            }
        }
        lisaa(kilpailu);
    }
    
    /**
     * Järjestää kilpailut -listan viikon numeron mukaan (pienimmästä suurimpaan)
     * <pre name="test">
     * Kilpailut kilpailut = new Kilpailut();
     * for(int i = 3;i>0;i--) {
     *     Kilpailu usa = new Kilpailu(); 
     *     usa.luo();    usa.vastaaSkateAmerica();
     *     usa.aseta("viikko", String.valueOf(i));
     *     try {
     *     kilpailut.lisaa(usa);
     *     } catch (Poikkeukset poikkeus) {
     *     poikkeus.printStackTrace();
     *     }
     * }
     * kilpailut.jarjesta();
     * kilpailut.getLkm() === 3;
     * kilpailut.anna(0).getViikko() === "1";
     * kilpailut.anna(1).getViikko() === "2";
     * kilpailut.anna(2).getViikko() === "3";
     * </pre>
     * 4,2,3,1 -> 1,2,3,4
     * 2,4,3,1
     * 2,3,4,1
     * 2,3,1,4 
     */
    public void jarjesta() {
        
        Kilpailu apu = new Kilpailu();
        
        for(int i=0; i<kilpailut.length; i++) {
            if (kilpailut[i] == null) return;
            
            for(int j=0; j<kilpailut.length-1; j++) {
                if (kilpailut[j+1] == null) break;
                
                if (Integer.valueOf(kilpailut[j].getViikko()) > Integer.valueOf(kilpailut[j+1].getViikko())) {
                    apu = kilpailut[j+1];
                    kilpailut[j+1] = kilpailut[j];
                    kilpailut[j] = apu;
                }
                
            }
            
        }
    }
    
    /**
     * Hakee oikean (valitun) kilpailun aktiiviseksi.
     * @param hakuehto jonka perusteella haetaan
     * @return haetun kilpailun indeksin
     */
    public int haeKilpailu(String hakuehto) {
        for (int i = 0; i < lkm; i++) {
            if (WildChars.onkoSamat(kilpailut[i].getNimi(), "*" + hakuehto + "*")) return i;
        }
        return 0;
    }
    
    /**
     * Kilpailut testiohjelma.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kilpailut kilpailut = new Kilpailut();

        Kilpailu usa = new Kilpailu(), usa2 = new Kilpailu();
        usa.luo();    usa.vastaaSkateAmerica();
        usa2.luo();   usa2.vastaaSkateAmerica();

        try {
            kilpailut.lisaa(usa);
            kilpailut.lisaa(usa2);
            
            System.out.println("========== Kilpailut testi ==============");

            for (int i=1; i<kilpailut.getLkm(); i++) {
                Kilpailu Kilpailu = kilpailut.anna(i);
                int j = i+1;
                System.out.println("Kilpailu nro: " + j);
                Kilpailu.tulosta(System.out);
                
            }
            
        } catch (Poikkeukset poikkeus) {
            // TODO Auto-generated catch block
            poikkeus.printStackTrace();
        }
    }
}
