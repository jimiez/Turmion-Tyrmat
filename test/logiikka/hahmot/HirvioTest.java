/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.hahmot;

import logiikka.esineet.Ase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class HirvioTest {

    Hirvio hirvio;
    Ase ase;
    
    public HirvioTest() {
    }

    @Before
    public void setUp() {
        ase = new Ase("testi", 1, 1, 1, 1, 1, null);
        this.hirvio = new Hirvio("testi", 1, 1, 1, null, 1, ase);
    }

    @Test
    public void testGetPuhtiNyt() {
        assertEquals(0, hirvio.getPuhtiNyt());

    }

    @Test
    public void testGetMaksimiPuhti() {
        assertEquals(0, hirvio.getMaksimiPuhti());

    }

    @Test
    public void testVahennaPuhtia() {
        assertEquals(true, hirvio.vahennaPuhtia(10));

    }

    @Test
    public void testLisaaPuhtia() {
        
        assertEquals(true, hirvio.lisaaPuhtia(1));
    }

    @Test
    public void testGetTyyppi() {

        assertEquals("hirviö", hirvio.getTyyppi());

    }

    @Test
    public void testGetXpArvo() {
        assertEquals(1, hirvio.getXpArvo());
    }

    @Test
    public void testGetAC() {
        assertEquals(1, hirvio.getAC());

    }

    @Test
    public void testGetBAB() {
        assertEquals(1, hirvio.getBAB());

    }
}
