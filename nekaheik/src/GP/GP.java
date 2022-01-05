package GP;

import java.io.File;
import java.util.List;

/**
 * @author Nella
 * @version 13.8.2020
 * 
 * Huolehtii Kilpailut ja Luistelijat -luokkien välisestä yhteistyöstä
 * ja välittää näitä tietoja pyydettäessä.
 * Lukee ja kirjoittaa GP:n tiedostoon pyytämällä apua avustajiltaan.
 * Avustajat: Kilpailut, Luistelijat, Kilpailu, Luistelija
 */
public class GP {
    
    private Kilpailut kilpailut = new Kilpailut();
    private Luistelijat luistelijat = new Luistelijat();
    
    /**
     * Tuo tiedostossa olevat kilpailut näkyviin. Heittää poikkeuksen, mikäli liikaa kilpailuita (yritetään lisätä).
     * @param i kilpailun numero
     * @return haluttu kilpailu
     * @throws IndexOutOfBoundsException heittää virheen jos ei toimi
     */
    public Kilpailu annaKilpailu(int i) throws IndexOutOfBoundsException {
        return kilpailut.anna(i);
    }

    /**
     * Palauttaa kilpailuiden lukumäärän (rajoitettu kuuteen).
     * @return kilpailuiden määrä
     */
    public int getKilpailuja() {
        return kilpailut.getLkm();
    }
    
    
    /**
     * Lisää uuden kilpailun. Heittää poikkeukset jos ei voida lisätä (ei voi olla yli 6 kilpailua).
     * @param kilpailu kilpailu, joka lisätään
     * @throws Poikkeukset virhe/poikkeus
     * @example
     *  <pre name="test">
     *  #THROWS Poikkeukset
     *  GP gp = new GP();
     *  gp.getKilpailuja() === 0;
     *  Kilpailu usa1 = new Kilpailu(), usa2 = new Kilpailu();
     *  gp.lisaa(usa1);
     *  gp.lisaa(usa2);
     *  gp.getKilpailuja() === 2;
     *  gp.annaKilpailu(0) === usa1;
     *  gp.annaKilpailu(1) === usa2;
     *  gp.annaKilpailu(4) === usa1; #THROWS IndexOutOfBoundsException
     *  gp.lisaa(usa1);
     *  gp.lisaa(usa2);
     *  gp.lisaa(usa1);
     *  gp.lisaa(usa2);
     *  gp.lisaa(usa1); #THROWS Poikkeukset
     *  </pre> 
     */
    public void lisaa(Kilpailu kilpailu) throws Poikkeukset {
        kilpailut.lisaa(kilpailu);
    }

    /**
     * Luistelijan lisäys.
     * @param luistelija joka lisätään
     */
    public void lisaa(Luistelija luistelija) {
        luistelijat.lisaa(luistelija);
    }
    
    /**
     * Antaa halutun (valitun) kilpailun luistelijat näkyviin.
     * @param kilpailu kilpailu jonka luistelijoita kysytään
     * @return kilpailun luistelijat
     * @throws Poikkeukset heittää virheen jos luistelijalle parametrinä vietävää kilpailuidtä ei ole.
     */
    public List<Luistelija> annaLuistelijat(Kilpailu kilpailu) throws Poikkeukset {
        return luistelijat.annaLuistelijat(kilpailu.getIdNro());
    }
    
    /**
     * Alustaa oliot ja lukee GPn tiedot tiedostosta
     * @param kansionNimi sen kansion nimi, jossa tiedosto on
     * @throws Poikkeukset heittää poikkeukset jos ei toimi
     * @example
     * <pre name="test">
     * #THROWS Poikkeukset
     * #import java.io.File;
     * #import java.util.*;
     * 
     * GP gp = new GP();
     * 
     * Kilpailu skate1 = new Kilpailu();
     * Kilpailu skate2 = new Kilpailu();
     * skate1.vastaaSkateAmerica();
     * skate2.vastaaSkateAmerica();
     * skate1.luo();
     * skate2.luo();
     * Luistelija rika1 = new Luistelija();
     * Luistelija rika2 = new Luistelija();
     * Luistelija rika3 = new Luistelija();
     * Luistelija rika4 = new Luistelija();
     * rika1.luo();
     * rika2.luo();
     * rika3.luo();
     * rika4.luo();
     * rika1.vastaaRikaKihira(skate1.getIdNro(), skate2.getIdNro());
     * rika2.vastaaRikaKihira(skate1.getIdNro());
     * rika3.vastaaRikaKihira(skate1.getIdNro(), skate2.getIdNro());
     * rika4.vastaaRikaKihira(skate2.getIdNro());
     * 
     * String hakemisto = "testiGP";
     * String tiedostonNimi1 = hakemisto+"/luistelijat";
     * String tiedostonNimi2 = hakemisto+"/kilpailut";
     * File kansio = new File(hakemisto);
     * File tiedosto1 = new File(tiedostonNimi1 + ".dat");
     * File tiedosto2 = new File(tiedostonNimi2 + ".dat"); 
     * kansio.mkdir();
     * tiedosto1.delete();
     * tiedosto2.delete();
     * gp.setTiedosto(hakemisto);
     * gp.lueTiedostosta(hakemisto); #THROWS Poikkeukset
     * gp.lisaa(skate1);
     * gp.lisaa(skate2);
     * gp.lisaa(rika1);
     * gp.lisaa(rika2);
     * gp.lisaa(rika3);
     * gp.lisaa(rika4);
     * gp.tallenna();
     * gp = new GP();
     * gp.lueTiedostosta(hakemisto);
     * 
     * gp.annaKilpailu(0).toString() === skate1.toString();
     * gp.annaKilpailu(1).toString() === skate2.toString();
     * gp.annaKilpailu(2); #THROWS IndexOutOfBoundsException
     * 
     * List<Luistelija> kilpailun1Luistelijat = gp.annaLuistelijat(skate1);
     * Iterator<Luistelija> luistelija1 = kilpailun1Luistelijat.iterator();
     * luistelija1.next().toString() === rika1.toString();
     * luistelija1.next().toString() === rika2.toString();
     * luistelija1.next().toString() === rika3.toString();
     * luistelija1.hasNext() === false;
     * List<Luistelija> kilpailun2Luistelijat = gp.annaLuistelijat(skate2);
     * Iterator<Luistelija> luistelija2 = kilpailun2Luistelijat.iterator();
     * luistelija2.next().toString() === rika1.toString();
     * luistelija2.next().toString() === rika3.toString();
     * luistelija2.next().toString() === rika4.toString();
     * luistelija2.hasNext() === false;
     * 
     * tiedosto1.delete() === true;
     * tiedosto2.delete() === true;
     * kansio.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String kansionNimi) throws Poikkeukset {
        kilpailut = new Kilpailut();
        luistelijat = new Luistelijat();
        
        setTiedosto(kansionNimi);
        
        //tallenna();
        kilpailut.lueTiedostosta();
        luistelijat.lueTiedostosta();
    }
    
    /**
     * Luo tiedoston oikeaan paikkaan perusnimellä.
     * @param kansionNimi sen kansion nimi, jossa tiedosto on
     */
    public void setTiedosto(String kansionNimi) {
        File kansio = new File(kansionNimi);
        kansio.mkdirs();
        String hakemistonNimi = "";
        if (kansionNimi != "" ) hakemistonNimi = kansionNimi + "/";
        kilpailut.setTiedostonPerusnimi(hakemistonNimi + "kilpailut");
        luistelijat.setTiedostonPerusnimi(hakemistonNimi + "luistelijat");
    }
    
    /**
     * Tallentaa GPn tiedostoon.
     * @throws Poikkeukset heittää virheen jos ei toimi
     */
    public void tallenna() throws Poikkeukset {
        String virhe = "";
        try {
            kilpailut.tallenna();
        } catch ( Poikkeukset poikkeus ) {
            virhe = poikkeus.getMessage();
        }
        try {
            luistelijat.tallenna();
        } catch ( Poikkeukset poikkeus ) {
            virhe += poikkeus.getMessage();
        }
        if ( virhe != "" ) throw new Poikkeukset(virhe);
    }
    

    /**
     * Tallentaa (lisää) uuden kilpailun tai muokkaa kilpailua.
     * @param kilpailu tallentaa uuden kilpailun tai tallentaa muutokset kilpailuun
     * @throws Poikkeukset heittää virheen jos ei voi lisätä (liikaa alkoita)
     */
    public void muokkaaTaiLisaa(Kilpailu kilpailu) throws Poikkeukset {
        kilpailut.muokkaaTaiLisaa(kilpailu);
    }
    
    /**
     * Tallentaa (lisää) uuden luistelijan tai muokkaa kilpailua.
     * @param luistelija tallentaa uuden luistelijan tai tallentaa muutokset luistelijaan
     */
    public void muokkaaTaiLisaa(Luistelija luistelija) {
        luistelijat.muokkaaTaiLisaa(luistelija);
    }
    
    /**
     * Hakee oikean kilpailun aktiiviseksi.
     * @param hakuehto jonka perusteella haetaan
     * @return haetun kilpailun indeksin
     */
    public int haeKilpailu(String hakuehto) {
        return kilpailut.haeKilpailu(hakuehto);
    }
    
    /**
     * Testiohjelma GPstä.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        GP gp = new GP();

        try {
            // kerho.lueTiedostosta("kelmit");

            Kilpailu usa1 = new Kilpailu(), usa2 = new Kilpailu();
            usa1.luo();
            usa1.vastaaSkateAmerica();
            usa2.luo();
            usa2.vastaaSkateAmerica();

            gp.lisaa(usa1);
            gp.lisaa(usa2);

            System.out.println("============= GPn testi =================");

            for (int i = 0; i < gp.getKilpailuja(); i++) {
                Kilpailu kilpailu = gp.annaKilpailu(i);
                System.out.println("Kilpailu paikassa: " + i);
                kilpailu.tulosta(System.out);
            }

        } catch (Poikkeukset ex) {
            System.out.println(ex.getMessage());
        }
    }
}
