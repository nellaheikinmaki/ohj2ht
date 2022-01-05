package GP.test;
// Generated by ComTest BEGIN
import java.io.File;
import static org.junit.Assert.*;
import org.junit.*;
import GP.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.26 11:19:07 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KilpailutTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta79 
   * @throws Poikkeukset when error
   */
  @Test
  public void testLueTiedostosta79() throws Poikkeukset {    // Kilpailut: 79
    Kilpailut kilpailut = new Kilpailut(); 
    Kilpailu skate1 = new Kilpailu(); 
    Kilpailu skate2 = new Kilpailu(); 
    skate1.luo(); 
    skate2.luo(); 
    skate1.vastaaSkateAmerica(); 
    skate2.vastaaSkateAmerica(); 
    String hakemisto = "testiGP"; 
    String tiedostonNimi = hakemisto+"/kilpailut"; 
    File tiedosto = new File(tiedostonNimi + ".dat"); 
    File kansio = new File(hakemisto); 
    kansio.mkdir(); 
    tiedosto.delete(); 
    kilpailut.setTiedostonPerusnimi(tiedostonNimi); 
    try {
    kilpailut.lueTiedostosta(); 
    fail("Kilpailut: 97 Did not throw Poikkeukset");
    } catch(Poikkeukset _e_){ _e_.getMessage(); }
    kilpailut.lisaa(skate1); 
    kilpailut.lisaa(skate2); 
    kilpailut.tallenna(); 
    kilpailut = new Kilpailut(); 
    kilpailut.setTiedostonPerusnimi(tiedostonNimi); 
    kilpailut.lueTiedostosta(); 
    assertEquals("From: Kilpailut line: 104", skate1.toString(), kilpailut.anna(0).toString()); 
    assertEquals("From: Kilpailut line: 105", skate2.toString(), kilpailut.anna(1).toString()); 
    try {
    kilpailut.anna(2); 
    fail("Kilpailut: 106 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    kilpailut.lisaa(skate2); 
    kilpailut.tallenna(); 
    assertEquals("From: Kilpailut line: 109", true, tiedosto.delete()); 
    assertEquals("From: Kilpailut line: 110", true, kansio.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testJarjesta184 */
  @Test
  public void testJarjesta184() {    // Kilpailut: 184
    Kilpailut kilpailut = new Kilpailut(); 
    for(int i = 3; i>0; i--) {
    Kilpailu usa = new Kilpailu(); 
    usa.luo(); usa.vastaaSkateAmerica(); 
    usa.aseta("viikko", String.valueOf(i)); 
    try {
    kilpailut.lisaa(usa); 
    } catch (Poikkeukset poikkeus) {
    poikkeus.printStackTrace(); 
    }
    }
    kilpailut.jarjesta(); 
    assertEquals("From: Kilpailut line: 197", 3, kilpailut.getLkm()); 
    assertEquals("From: Kilpailut line: 198", "1", kilpailut.anna(0).getViikko()); 
    assertEquals("From: Kilpailut line: 199", "2", kilpailut.anna(1).getViikko()); 
    assertEquals("From: Kilpailut line: 200", "3", kilpailut.anna(2).getViikko()); 
  } // Generated by ComTest END
}