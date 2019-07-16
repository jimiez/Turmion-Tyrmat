/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.maailma;

import java.util.ArrayList;
import java.util.Map;
import logiikka.Peli;
import logiikka.hahmot.Hahmo;
import logiikka.hahmot.Pelaaja;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class KarttaTest {

    Kartta kartta;
    Pelaaja pelaaja;
    Peli peli;
    
    public KarttaTest() {
    }

    @Before
    public void setUp() {
        peli = new Peli("testi", 10, 5, 0, null);
        kartta = peli.getKartta();
        pelaaja = this.peli.getPelaaja();
        
    }

    @Test
    public void testTunnusRuuduksi() {
        assertEquals("Kuusi", kartta.tunnusRuuduksi('X').getNimi());
    }

    @Test
    public void testGetRuutu_int_int() {

        assertEquals('X', kartta.getRuutu(0, 0));
    }

    @Test
    public void testGetRuutu_intArr() {
        int[] ruutu = {0, 0};
        assertEquals('X', kartta.getRuutu(ruutu));
    }

    @Test
    public void testOnkoHahmoa_int_int() {
        assertEquals(1, kartta.onkoHahmoa(5, 5));
    }

    @Test
    public void testOnkoHahmoa_intArr() {
        
        int[] ruutu = {5, 5};
        assertEquals(1, kartta.onkoHahmoa(ruutu));
    }

    @Test
    public void testGetHahmo_int_int() {

        Hahmo result = kartta.getHahmo(5, 5);
        assertEquals("testi", result.getNimi());

    }

    @Test
    public void testGetHahmo_intArr() {
        int[] ruutu = {5, 5};
        Hahmo result = kartta.getHahmo(ruutu);
        assertEquals("testi", result.getNimi());
    }

    @Test
    public void testGetKaikkiHahmot() {
        int[] koordinaatti = {5, 5};
        ArrayList result = kartta.getKaikkiHahmot(koordinaatti);
        assertEquals(1, result.size());

    }

    @Test
    public void testLiikutaOikealle() {

        assertEquals(true, kartta.liikuta(pelaaja, 2));

    }

    @Test
    public void testLiikutaVasemmalle() {

        assertEquals(true, kartta.liikuta(pelaaja, 2));

    }

    @Test
    public void testLisaaHahmo() {

        Hahmo result = kartta.getHahmo(5, 5);
        assertEquals("testi", result.getNimi());
    }

    @Test
    public void testPoistaHahmo() {

        kartta.poistaHahmo(pelaaja);
        assertEquals(0, kartta.onkoHahmoa(5, 5));
    }

    @Test
    public void testOnkoKuljettava_int_int() {


        assertEquals(false, kartta.onkoKuljettava(0, 0));
    }

    @Test
    public void testOnkoKuljettava_intArr() {

        int[] koordinaatit = {5, 5};
        assertEquals(true, kartta.onkoKuljettava(koordinaatit));

    }

    @Test
    public void testGetSijainti() {
        
        int[] expResult = {5,5};
        int[] result = kartta.getSijainti(pelaaja);
        assertEquals(expResult[0], result[0]);
     
    }

    @Test
    public void testGetKartanKoko() {
        System.out.println("getKartanKoko");
        
        int expResult = 10;
        int result = kartta.getKartanKoko();
        assertEquals(expResult, result);
     
    }

    @Test
    public void testGetHahmot() {
      
        Map result = kartta.getHahmot();
        assertEquals(1, result.size());
        
    }

    private char[][] luoTestikartta() {

        char[][] testikartta = new char[4][4];

        for (int i = 0; i < testikartta.length - 1; i++) {
            for (int j = 0; j < testikartta.length - 1; j++) {
                testikartta[i][j] = 'X';
            }
        }
        testikartta[1][1] = '.';
        testikartta[1][2] = '.';
        testikartta[2][1] = '.';
        testikartta[2][2] = '.';

        return testikartta;

    }
}
