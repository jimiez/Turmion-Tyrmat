/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.apuluokat;

import logiikka.hahmot.Hahmo;
import logiikka.hahmot.Hirvio;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class LokiTest {

    Loki loki;

    public LokiTest() {
    }

    @Before
    public void setUp() {
        loki = new Loki();
    }

    @Test
    public void testKirjaa() {
        
        assertEquals("Uusi peli aloitettu!\n", loki.toString());
    }

    @Test
    public void testToString() {
        assertEquals("Uusi peli aloitettu!\n", loki.toString());
    }

    @Test
    public void testKirjaaOtettuaDamaa() {
        loki.kirjaaOtettuaDamaa(1);
        assertEquals(1, loki.getOtettuDama());
    }

    @Test
    public void testKirjaaAnnettuaDamaa() {
        loki.kirjaaAnnettuaDamaa(1);
        assertEquals(1, loki.getAnnettuDama());
    }

    @Test
    public void testKirjaaTappo() {
        loki.kirjaaTappo(new Hirvio("testi", 0, 0, 0, null, 0, null));
        assertEquals("Tapetut hirviöt:\ntesti: 1\n", loki.tulostaTapot());
    }

    @Test
    public void testTulostaTapot() {
        loki.kirjaaTappo(new Hirvio("testi", 0, 0, 0, null, 0, null));
        assertEquals("Tapetut hirviöt:\ntesti: 1\n", loki.tulostaTapot());
    }

    @Test
    public void testSetTappaja() {
        loki.setTappaja(new Hirvio("testi", 0, 0, 0, null, 0, null));
        assertEquals("testi", loki.getTappaja());
    }

    @Test
    public void testGetTappaja() {
        loki.setTappaja(new Hirvio("testi", 0, 0, 0, null, 0, null));
        assertEquals("testi", loki.getTappaja());
    }

    @Test
    public void testGetTapetutHirviot() {
         loki.kirjaaTappo(new Hirvio("testi", 0, 0, 0, null, 0, null));
        assertEquals(1, loki.getTapetutHirviot());
    }

    @Test
    public void testGetAnnettuDama() {
       loki.kirjaaAnnettuaDamaa(1);
        assertEquals(1, loki.getAnnettuDama());
    }

    @Test
    public void testGetOtettuDama() {
       loki.kirjaaOtettuaDamaa(1);
        assertEquals(1, loki.getOtettuDama());
    }
}
