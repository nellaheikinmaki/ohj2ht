package GP;

/**
 * @author Nella
 * @version 13.8.2020
 * Huomioi poikkeustapaukset.
 */
public class Poikkeukset extends Exception {
    
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public Poikkeukset(String viesti) {
        super(viesti);
    }

}
