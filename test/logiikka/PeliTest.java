/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import logiikka.esineet.Ase;
import logiikka.esineet.Roju;
import logiikka.hahmot.Hahmo;
import logiikka.hahmot.Hirvio;
import logiikka.hyokkaykset.PerusHyokkays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author J-P
 */
public class PeliTest
{

    Peli peli;

    public PeliTest()
    {
    }

    @Before
    public void setUp()
    {
        this.peli = new Peli("testi", 10, 2, 1, null);
    }

    @Test
    public void testHyokkaa()
    {
        Ase testiase = new Ase("testi", 1, 1, 1, 1, 0, null);
        Hirvio hirvio = new Hirvio("testihirviö", 0, 0, 0, null, 4, null);
        this.peli.getPelaaja().setAse(testiase);
        this.peli.hyokkaa(new PerusHyokkays(this.peli.getPelaaja(), hirvio, peli));
        assertEquals(3, hirvio.getNytHP());
    }

    @Test
    public void testLuoUusiTaso()
    {
        this.peli.luoUusiTaso();
        int koko = this.peli.getKartta().getKartanKoko();
        assertEquals(150, koko);
    }

    @Test
    public void testOnkoNakoyhteys()
    {

        int[] koord1 =
        {
            5, 5
        };
        int[] koord2 =
        {
            0, 0
        };
        boolean expResult = false;
        boolean result = peli.onkoNakoyhteys(koord1, koord2);
        assertEquals(expResult, result);

    }

    @Test
    public void testSetKohde()
    {

        Hahmo kohde = new Hirvio("testihirviö", 0, 0, 0, null, 0, null);

        peli.setKohde(kohde);
        assertEquals("testihirviö", peli.getKohde().getNimi());
    }

    @Test
    public void testGetKohde()
    {
        Hahmo kohde = new Hirvio("testihirviö", 0, 0, 0, null, 0, null);
        peli.setKohde(kohde);
        assertEquals("testihirviö", peli.getKohde().getNimi());
    }

    @Test
    public void testGetKartta()
    {

        assertEquals(10, peli.getKartta().getKartanKoko());
    }

    @Test
    public void testGetReppu()
    {
        this.peli.getReppu().lisaa(new Roju("testiroju", 0, 0, null));
        assertEquals(1, peli.getReppu().getKokonaisMaara());
    }

    @Test
    public void testGetLoki()
    {
        this.peli.getLoki().kirjaaAnnettuaDamaa(1);
        assertEquals(1, peli.getLoki().getAnnettuDama());
    }

    @Test
    public void testGetPelaaja()
    {
        assertEquals("testi", peli.getPelaaja().getNimi());
    }

    @Test
    public void testGetSyvyys()
    {
        assertEquals(0, peli.getSyvyys());
    }
}
