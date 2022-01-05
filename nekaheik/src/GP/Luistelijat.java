package GP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * @author Nella
 * @version 13.8.2020
 * Pitää yllä varsinaista luistelijarekisteriä eli osaa lisätä ja poistaa luistelijan.
 * Lukee ja kirjoittaa luistelijoiden tiedostoon.
 * Osaa etsiä ja lajitella.
 * Avustajat: Luistelija
 */
public class Luistelijat implements Iterable<Luistelija> {

    private String tiedostonNimi = "";
    private final Collection<Luistelija> luistelijatAlkiot = new ArrayList<Luistelija>();
    private boolean muutettu = false;
    
    /**
     * Iteraattori kaikkien luistelijoiden läpikäymiseen.
     * @return luistelijaiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import.java.util.*;
     * 
     * Luistelijat luistelijat = new Luistelijat();
     * Luistelija alexTrus = new Luistelija(1,2);
     * luistelijat.lisaa(alexTrus);
     * Luistelija anya = new Luistelija(2);
     * luistelijat.lisaa(anya);
     * Luistelija aliona = new Luistelija(4,7);
     * luistelijat.lisaa(aliona);
     * 
     * Iterator<Luistelija> iteraattori = luistelijat.iterator();
     * iteraattori.next() === alexTrus;
     * iteraattori.next() === anya;
     * iteraattori.next() === aliona;
     * iteraattori.next() === alexTrus; #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int id1[] = {1,2,4};
     * 
     * for ( Luistelija luistelija : luistelijat) {
     *      luistelija.getKilpailuId1() === id1[n]; n++;
     * }
     * 
     * n === 3;
     * 
     * </pre>
     */
    @Override
    public Iterator<Luistelija> iterator() {
        return luistelijatAlkiot.iterator();
    }

    /**
     * Lisää Luistelija -collectioniin (tietorakenteeseen) luistelijan.
     * @param luistelija lisättävä luistelija
     */
    public void lisaa(Luistelija luistelija) {
        luistelijatAlkiot.add(luistelija);
        muutettu = true;
    }

    /**
     * Antaa valitun kilpailun luistelijat näkyviin.
     * @param idNro kilpailu, josta ollaan kiinnostuneita
     * @return listan luistelijoista, jotka ovat kilpailussa
     * @example 
     * <pre name="test">
     * #import java.util.*;
     * 
     * Luistelijat luistelijat = new Luistelijat();
     * Luistelija alexTrus = new Luistelija(1,2);
     * luistelijat.lisaa(alexTrus);
     * Luistelija anya = new Luistelija(2);
     * luistelijat.lisaa(anya);
     * Luistelija aliona = new Luistelija(4,7);
     * luistelijat.lisaa(aliona);
     * 
     * List<Luistelija> kilpailunLuistelijat;
     * kilpailunLuistelijat = luistelijat.annaLuistelijat(1);
     * kilpailunLuistelijat.size() === 1;
     * kilpailunLuistelijat = luistelijat.annaLuistelijat(2);
     * kilpailunLuistelijat.size() === 2;
     * kilpailunLuistelijat.get(0) == alexTrus === true;
     * kilpailunLuistelijat.get(1) == anya === true;
     * kilpailunLuistelijat.get(1) == aliona === false;
     * kilpailunLuistelijat = luistelijat.annaLuistelijat(3);
     * kilpailunLuistelijat.size() === 0;
     * kilpailunLuistelijat = luistelijat.annaLuistelijat(4);
     * kilpailunLuistelijat.size() === 1;
     * </pre>
     */
    public List<Luistelija> annaLuistelijat(int idNro) {
        List<Luistelija> kilpailunLuistelijat = new ArrayList<Luistelija>();
        for(Luistelija luistelija : luistelijatAlkiot) {
            if(luistelija.getKilpailuId1() == idNro || luistelija.getKilpailuId2() == idNro) {
                kilpailunLuistelijat.add(luistelija);
            }
        } 
        try {
            Collections.sort(kilpailunLuistelijat, new Luistelija.Vertailija());
        } catch (NullPointerException poikkeus) {
            return kilpailunLuistelijat;
        }
        
        return kilpailunLuistelijat;
    }

    /**
     * Luo luistelijat -tiedoston perusnimen.
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
     * Lukee luistelijat tiedostosta.
     * @throws Poikkeukset heittää virheen jos ei onnistu
     * @example
     * <pre name="test">
     * #THROWS Poikkeukset
     * #import java.io.File;
     * 
     * Luistelijat luistelijat = new Luistelijat();
     * Luistelija rika1 = new Luistelija();
     * Luistelija rika2 = new Luistelija();
     * rika1.luo();
     * rika2.luo();
     * rika1.vastaaRikaKihira(1,2);
     * rika2.vastaaRikaKihira(4);
     * String hakemisto = "testiGP";
     * String tiedostonNimi = hakemisto+"/luistelijat";
     * File tiedosto = new File(tiedostonNimi + ".dat");
     * File kansio = new File(hakemisto);
     * kansio.mkdir();
     * tiedosto.delete();
     * luistelijat.setTiedostonPerusnimi(tiedostonNimi);
     * luistelijat.lueTiedostosta(); #THROWS Poikkeukset
     * luistelijat.lisaa(rika1);
     * luistelijat.lisaa(rika2);
     * luistelijat.tallenna();
     * luistelijat = new Luistelijat();
     * luistelijat.setTiedostonPerusnimi(tiedostonNimi);
     * luistelijat.lueTiedostosta();
     * Iterator<Luistelija> luistelija = luistelijat.iterator();
     * luistelija.next().toString() === rika1.toString();
     * luistelija.next().toString() === rika2.toString();
     * luistelija.hasNext() === false;
     * luistelijat.lisaa(rika2);
     * luistelijat.tallenna();
     * tiedosto.delete() === true;
     * kansio.delete() === true;
     * </pre>
     */
    public void lueTiedostosta() throws Poikkeukset {
        try (BufferedReader lukija = new BufferedReader(new FileReader(getTiedostonNimi()))) {
           String rivi = "";
            
           while( (rivi = lukija.readLine()) != null) {
               rivi = rivi.trim();
               if ( rivi == "" ) continue;
               Luistelija luistelija = new Luistelija();
               luistelija.parse(rivi);
               lisaa(luistelija);
           } 
           muutettu = false;
        } catch (FileNotFoundException poikkeus) {
            throw new Poikkeukset("Tiedosto " + getTiedostonNimi() + " ei aukea.");
        } catch (IOException poikkeus) {
            throw new Poikkeukset("Ongelmia tiedoston kanssa: " + poikkeus.getMessage());
        }
    }

    /**
     * Kirjoittaa luistelijoiden tiedot tiedostoon.
     * @throws Poikkeukset heittää jos ei toimi.
     */
    public void tallenna() throws Poikkeukset {
        if ( !muutettu ) return;
        File tiedosto = new File(getTiedostonNimi());
        
        try ( PrintWriter out = new PrintWriter(new FileWriter(tiedosto.getCanonicalPath())) ) {
            for (Luistelija luistelija : this) {
                out.println(luistelija.toString());
            }
        } catch (FileNotFoundException poikkeus) {
            throw new Poikkeukset("Tiedosto " + tiedosto.getName() + " ei aukea.");
        } catch (IOException poikkeus) {
            throw new Poikkeukset("Tiedoston " + tiedosto.getName() + " kirjoittamisessa ongelmia.");
        }
        
        muutettu = false;
    }
    
    /**
     * Tekee muutettu termin (boolean) tilaan "true", jotta tallennus tapahtuu.
     */
    public void setMuutettuTrue() {
        muutettu = true;
    }
    
    /**
     * Tallentaa uuden luistelijan tai tallentaa muutokset luistelijaan.
     * @param luistelija muokattava tai lisättävä luistelija
     */
    public void muokkaaTaiLisaa(Luistelija luistelija) {
        int id = luistelija.getLuistelijaId();
        for (Luistelija lui : luistelijatAlkiot) {
            if (lui.getLuistelijaId() == id) {
                luistelijatAlkiot.remove(lui);    
                luistelijatAlkiot.add(luistelija);
                muutettu = true;
                return;
            }
        }
        lisaa(luistelija);
    }
}
