package GP;

import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Nella
 * @version 13.8.2020
 * 
 * Tietää kilpailun kentät.
 * Osaa tarkistaa tietyn kentän oikeellisuuden/syntaksin.
 * Osaa muuttaa tiedostosta tulevan merkkijonon kilpailun tiedoiksi.
 * Osaa antaa merkkijonona i:nnen kentän tiedot.
 * Osaa laittaa merkkijonon i:nneksi kentäksi.
 */
public class Kilpailu implements Cloneable {
    
    private int idNro = 0;
    private String nimi = "";
    private String maa = "";
    private int vko = 1;
    private String lisatietoja = "";
    
    private static int NextId = 1;
    
    /** 
     * Kilpailuiden vertailija viikon mukaan järjestämistä varten.
     * Oma luokkansa.
     */ 
    public static class Vertailija implements Comparator<Kilpailu> { 
        
        @Override 
        public int compare(Kilpailu kilpailu1, Kilpailu kilpailu2) { 
            return kilpailu1.getViikko().compareToIgnoreCase(kilpailu2.getViikko()); 
        } 
    } 
    
    /**
     * Palauttaa kilpailun nimen.
     * @return kilpailun nimi
     * @example
     * <pre name="test">
     *   Kilpailu usa = new Kilpailu();
     *   usa.vastaaSkateAmerica();
     *   usa.getNimi() =R= "Skate America";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Muodostaa halutun merkkijonon tallentamista varten.
     */
    @Override
    public String toString() {
        return "" +
                getIdNro() + "|" +
                getNimi() + "|" +
                maa + "|" +
                vko + "|" +
                lisatietoja + "|";
    }
    
    /**
     * Testiohjelman apuohjelma.
     */
    public void vastaaSkateAmerica() {
        nimi = "Skate America";
        maa = "USA";
        vko = 42;
    }

    /**
     * Tulostaa kilpailun tiedot.
     * @param out mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", idNro, 3) + "  " + nimi);
        out.println("  Paikka: " + maa);
        out.print("  Ajankohta: vko " + vko);
        out.println("  " + lisatietoja);
    }

    /**
     * Luo kilpailulle (seuraavan) rekisterinumeron.
     * @return kilpailun uusi idNro
     * @example
     * <pre name="test">
     *   Kilpailu usa1 = new Kilpailu();
     *   usa1.getIdNro() === 0;
     *   usa1.luo();
     *   Kilpailu usa2 = new Kilpailu();
     *   usa2.luo();
     *   int n1 = usa1.getIdNro();
     *   int n2 = usa2.getIdNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int luo() {
        idNro = NextId;
        NextId++;
        return idNro;
    }
    
    /**
     * Palauttaa kilpailun id numeron.
     * @return kilpailun id numero
     */
    public int getIdNro() {
        return idNro;
    }
    
    /**
     * Parsettaa ylimääräiset merkit pois.
     * @param rivi käsiteltävä rivi
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setIdNro(Mjonot.erota(sb, '|', getIdNro()));
        nimi = Mjonot.erota(sb,'|',nimi);
        maa = Mjonot.erota(sb,'|', maa);
        vko = Mjonot.erotaInt(sb, vko);
    }
    
    private void setIdNro(int nro) {
        idNro = nro;
        if (idNro >= NextId) NextId = idNro + 1;
    }

    /**
     * Palauttaa kilpailun maan.
     * @return kilpailun maa
     */
    public String getMaa() {
        return maa;
    }

    /**
     * Palauttaa kilpailun viikon.
     * @return kilpailun viikko
     */
    public String getViikko() {
        if (vko > 0 && vko < 54) {
            return Integer.toString(vko);
        }
        return "";
    }
    
    @Override
    public Kilpailu clone() throws CloneNotSupportedException {
        Kilpailu uusi;
        uusi = (Kilpailu) super.clone();
        return uusi;
    }

    /**
     * Asettaa käsiteltävään tekstikenttään tiedot.
     * @param id tekstikentän id
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
        case "viikko":
            try {
                vko = Integer.parseInt(tteksti);
                
            } catch (NumberFormatException poikkeus) {
                vko = 0;
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Testiohjelma kilpailulle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kilpailu usa = new Kilpailu(), usa2 = new Kilpailu();
        usa.luo();
        usa2.luo();
        usa.tulosta(System.out);
        usa.vastaaSkateAmerica();
        usa.tulosta(System.out);
        
        usa2.vastaaSkateAmerica();
        usa2.tulosta(System.out);
        
        usa2.vastaaSkateAmerica();
        usa2.tulosta(System.out);
    }


}
