/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.esineet.Ase;
import logiikka.hahmot.Hirvio;
import logiikka.hahmot.Pelaaja;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import logiikka.apuluokat.Tyokalupakki;

/**
 *
 * @author J-P
 */
public class HirvioHyokkaysTest {
    
    Pelaaja pelaaja;
    Hirvio hirvio;
    Peli peli;
    HirvioHyokkays hirviohyokkays;
    Ase ase;
    
    public HirvioHyokkaysTest() {
    }

     
    @Before
    public void setUp() {
        ase = new Ase("testi", 1, 1, 1, 1, 0, null);
        peli = new Peli("pelaaja", 10, 5, 0, null);
        pelaaja = peli.getPelaaja();
        pelaaja.asetaOminaisuudet(0, 0, 0);
        hirvio = new Hirvio("hirviö", 0, 10, 0, null, 1, ase);
        hirviohyokkays = new HirvioHyokkays(hirvio, pelaaja, peli);
    }

  
    @Test
    public void testTeeVahinko() {
              
       assertEquals(false, hirviohyokkays.teeVahinko());
    }

    @Test
    public void testHaeOsuma() {
       assertEquals(true, this.hirviohyokkays.haeOsuma());
    }
}
