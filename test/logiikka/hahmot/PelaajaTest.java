/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.hahmot;

import logiikka.esineet.Ase;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class PelaajaTest {
    
    Pelaaja pelaaja;
    Ase ase;
    public PelaajaTest() {
    }

    @Before
    public void setUp() {
        ase = new Ase("testiase", 1, 1, 1, 1, 1, null);
        pelaaja = new Pelaaja("testi", null, ase);
    }

    @Test
    public void testGetMod() {
        
        assertEquals(1, pelaaja.getMod(12));
    }

    @Test
    public void testGetMaksimipaino() {
       assertEquals(100.0, pelaaja.getMaksimipaino(), 0.1);
    }

    @Test
    public void testGetAC() {
        assertEquals(10, pelaaja.getAC());
    }

    @Test
    public void testGetBAB() {
        assertEquals(2, pelaaja.getBAB());
    }

    @Test
    public void testVahennaPuhtia() {
         assertEquals(true, pelaaja.vahennaPuhtia(1));
    }

    @Test
    public void testLisaaPuhtia() {
        assertEquals(true, pelaaja.lisaaPuhtia(1));
    }

    @Test
    public void testGetTyyppi() {
         assertEquals("pelaaja", pelaaja.getTyyppi());
    }

    @Test
    public void testLisaaExpaa() {
        pelaaja.lisaaExpaa(10);
         assertEquals(10, pelaaja.getXp());
    }

    @Test
    public void testGetXpArvo() {
        assertEquals(0, pelaaja.getXpArvo());
    }

    @Test
    public void testAsetaOminaisuudet() {
        pelaaja.asetaOminaisuudet(12, 10, 10); 
        assertEquals(12, pelaaja.getVoima());
    }

    @Test
    public void testAsetaPuhti() {
       pelaaja.asetaPuhti(15);
       assertEquals(15, pelaaja.getMaksimiPuhti());
    }

    @Test
    public void testGetAse() {
         assertEquals("testiase", pelaaja.getAse().getNimi());
    }

    @Test
    public void testKasvataTasoa() {
        pelaaja.kasvataTasoa();
         assertEquals(2, pelaaja.getLvl());
    }

    @Test
    public void testGetLvl() {
       assertEquals(1, pelaaja.getLvl());
    }

    @Test
    public void testGetVoima() {
       assertEquals(10, pelaaja.getVoima());
    }

    @Test
    public void testGetKesto() {
        assertEquals(10, pelaaja.getKesto());
    }

    @Test
    public void testGetKetteryys() {
        assertEquals(10, pelaaja.getKetteryys());
    }

    @Test
    public void testGetXp() {
         assertEquals(0, pelaaja.getXp());
    }

    @Test
    public void testGetMaksimiPuhti() {
         assertEquals(10, pelaaja.getMaksimiPuhti());
    }

    @Test
    public void testGetPuhtiNyt() {
         assertEquals(10, pelaaja.getPuhtiNyt());
    }
}
