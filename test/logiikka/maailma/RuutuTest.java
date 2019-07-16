/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.maailma;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class RuutuTest {

    Ruutu ruutu;

    public RuutuTest() {
    }

    @Before
    public void setUp() {
        ruutu = new Ruutu("testiruutu", 'T', true, "testiruutu.png");
    }

    @Test
    public void testGetNimi() {
       
        assertEquals("testiruutu", ruutu.getNimi());
      
    }

    @Test
    public void testGetTunnus() {
        assertEquals('T', ruutu.getTunnus());
    }

    @Test
    public void testGetKuva() {
         assertEquals("testiruutu.png", ruutu.getKuva());
    }

    @Test
    public void testIsLapaistava() {
      assertTrue(ruutu.isLapaistava());
    }
  
}
