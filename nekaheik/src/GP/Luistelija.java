package GP;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Nella
 * @version 13.8.2020
 * Tietää luistelijan kentät.
 * Osaa tarkistaa tietyn kentän oikeellisuuden/syntaksin.
 * Osaa muuttaa tiedostosta tulevan merkkijonon luistelijan tiedoiksi.
 * Osaa antaa merkkijonona i:nnen kentän tiedot.
 * Osaa laittaa merkkijonon i:nneksi kentäksi.
 */
public class Luistelija implements Cloneable {
    private int luistelijaId;
    private int kilpailuId1;
    private int kilpailuId2;
    private String nimi;
    private String maa;
    private int ika;
    
    private static int nextId;

    /** 
     * Luistelijoiden vertailija aakkostamista varten
     * Oma luokkansa.
     */ 
    public static class Vertailija implements Comparator<Luistelija> { 
        
        @Override 
        public int compare(Luistelija luistelija1, Luistelija luistelija2) { 
            return luistelija1.getNimi().compareToIgnoreCase(luistelija2.getNimi()); 
        } 
    } 
    
    /**
     * Asettaa luistelijalle yhden kilpailuid:n.
     * @param id1 ensimmäinen kilpailu
     */
    public Luistelija(int id1) {
        this.kilpailuId1 = id1;
    }
    
    /**
     * Asettaa luistelijalle kaksi kilpailuid:tä.
     * @param id1 ensimmäinen kilpailu
     * @param id2 toinen kilpailu
     */
    public Luistelija(int id1, int id2) {
        this.kilpailuId1 = id1;
        this.kilpailuId2 = id2;
    }
   
    
    /**
     * Luistelijan konstruktori.
     */
    public Luistelija() {
        
    }

    /**
     * Tulostetaan luistelijan tiedot.
     * @param out
     */
    private void tulosta(PrintStream out) {
        out.println(nimi + " " + maa + " " + ika);
    }
    
    
    /**
     * Tulostetaan kilpailun tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Parsettaa ylimääräiset merkit pois.
     * @param rivi käsiteltävä rivi
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        String numero = Mjonot.erota(sb, '|', String.valueOf(getLuistelijaId()));
        setIdNro(Integer.parseInt(numero));
        String id1 = Mjonot.erota(sb, '|', String.valueOf(kilpailuId1));
        String id2 = Mjonot.erota(sb, '|', String.valueOf(kilpailuId2));
        kilpailuId1 = Integer.parseInt(id1);
        kilpailuId2 = Integer.parseInt(id2);
        nimi = Mjonot.erota(sb, '|', nimi);
        maa = Mjonot.erota(sb,'|', maa);
        ika = Mjonot.erotaInt(sb, ika);
    }
    
    private void setIdNro(int nro) {
        luistelijaId = nro;
        if (luistelijaId >= nextId) nextId = luistelijaId + 1;
    }
    
    /**
     * Muodostaa halutun merkkijonon tallentamista varten.
     */
    @Override
    public String toString() {
        return "" +
                getLuistelijaId() + "|" +
                getKilpailuId1() + "|" +
                getKilpailuId2() + "|" +
                nimi + "|" +
                maa + "|" +
                ika + "|";
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot luistelijalle.
     * @param id1 1. kilpailun id
     * @param id2 2. kilpailun id
     */
    public void vastaaRikaKihira(int id1, int id2) {
        kilpailuId1 = id1;
        kilpailuId2 = id2;
        nimi = "Rika Kihira";
        maa = "Japani";
        ika = 17;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot luistelijalle.
     * @param id1 1. kilpailun id
     */
    public void vastaaRikaKihira(int id1) {
        kilpailuId1 = id1;
        nimi = "Rika Kihira";
        maa = "Japani";
        ika = 17;
    }
    
    /**
     * Antaa seuraavan id numeron luistelijalle.
     * @return seuraavan id numeron luistelijalle
     * @example
     * <pre name="test">
     *   Luistelija alexTrus = new Luistelija();
     *   alexTrus.getLuistelijaId() === 0;
     *   alexTrus.luo();
     *   Luistelija anya = new Luistelija();
     *   anya.luo();
     *   int n1 = alexTrus.getLuistelijaId();
     *   int n2 = anya.getLuistelijaId();
     *   n1 === n2-1;
     * </pre>
     */
    public int luo() {
        luistelijaId = nextId;
        nextId++;
        return luistelijaId;
    }
    
    /**
     * Palauttaa luistelijan nimen.
     * @return luistelijan nimen
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Palauttaa luistelijan id numeron.
     * @return luistelijan id numeron
     */
    public int getLuistelijaId() {
        return luistelijaId;
    }
    
    /**
     * Palauttaa luistelijan 1. kilpailun id:n.
     * @return luistelijan kilpailun id 1
     */
    public int getKilpailuId1() {
        return kilpailuId1;
    }
    
    /**
     * Palauttaa luistelijan 2. kilpailun id:n.
     * @return luistelijan kilpailun id 2
     */
    public int getKilpailuId2() {
        return kilpailuId2;
    }
    
    /**
     * Palauttaa luistelijan maan.
     * @return luistelijan maa
     */
    public String getMaa() {
        return maa;
    }

    /**
     * Palauttaa luistelijan iän.
     * @return luistelijan ikä
     */
    public String getIka() {
        return Integer.toString(ika);
    }
    
    @Override
    public Luistelija clone() throws CloneNotSupportedException {
        Luistelija uusi;
        uusi = (Luistelija) super.clone();
        return uusi;
    }

    /**
     * Asettaa käsiteltävään tekstikenttään tiedot.
     * @param id luistelijan id
     * @param teksti tekstikentän teksti
     */
    public void aseta(String id, String teksti) {
        String tteksti = teksti.trim();
        switch ( id ) {
        case "nimi":
            nimi = tteksti;
            break;
        case "maa":
            maa = tteksti;
            break;
        case "ika":
            try {
                ika = Integer.parseInt(tteksti);
                
            } catch (NumberFormatException poikkeus) {
                ika = 0;
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Testiohjelma luistelijalle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Luistelija lui = new Luistelija();
        lui.vastaaRikaKihira(2, 1);
        lui.tulosta(System.out);
    }
}
