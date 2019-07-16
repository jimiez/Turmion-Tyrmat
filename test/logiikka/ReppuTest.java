/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import logiikka.esineet.Esine;
import logiikka.esineet.Roju;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class ReppuTest {

    Reppu reppu;
    Peli peli;

    public ReppuTest() {
    }

    @Before
    public void setUp() {
        this.peli = new Peli("testi", 10, 5, 0, null);
        this.reppu = new Reppu(peli);
        reppu.lisaa(new Roju("joku", 1, 1, null));
    }

    @Test
    public void testLisaa() {
        assertEquals(1, reppu.getKokonaisMaara());
    }

    @Test
    public void testTuhoa() {
        reppu.tuhoa("joku");
        assertEquals(0, reppu.getKokonaisMaara());
    }

    @Test
    public void testGetKokonaisPaino() {
        assertEquals(1, reppu.getKokonaisPaino(), 0.1);
    }

    @Test
    public void testGetKokonaisMaara() {
        assertEquals(1, reppu.getKokonaisMaara());
    }

    @Test
    public void testGetEsine() {
        Esine esine = reppu.getEsine("joku");

        assertEquals("joku", esine.getNimi());
    }

    @Test
    public void testPainoString() {
        String maksimi = "" + this.peli.getPelaaja().getMaksimipaino();
        String expResult = "1.0 / " + maksimi;
        assertEquals(expResult, this.reppu.painoString());
        
    }
}
