/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.esineet.Ase;
import logiikka.hahmot.Hirvio;
import logiikka.hahmot.Pelaaja;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class PerusHyokkaysTest {

    Pelaaja pelaaja;
    Hirvio hirvio;
    Peli peli;
    PerusHyokkays perushyokkays;
    Ase ase;

    public PerusHyokkaysTest() {
    }

    @Before
    public void setUp() {
        ase = new Ase("testi", 1, 1, 2, 1, 0, null);
        peli = new Peli("pelaaja", 10, 5, 0, null);
        pelaaja = peli.getPelaaja();
        pelaaja.setAse(ase);
        pelaaja.asetaOminaisuudet(10, 10, 10);
        hirvio = new Hirvio("hirviö", 0, 0, 0, null, 1, ase);
        perushyokkays = new PerusHyokkays(pelaaja, hirvio, peli);
    }

    @Test
    public void testHaeOsuma() {
        assertEquals(true, perushyokkays.haeOsuma());
    }

    @Test
    public void testTeeVahinko() {

        assertEquals(true, perushyokkays.teeVahinko());
    }
}
