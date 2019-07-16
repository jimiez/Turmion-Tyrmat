/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.apuluokat;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import logiikka.maailma.Ruutu;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class LataajaTest {

    Lataaja lataaja;

    public LataajaTest() {
    }

    @Before
    public void setUp() {
        this.lataaja = new Lataaja();
    }

    @Test
    public void testHaeKuva() {
       
        BufferedImage kuva = this.lataaja.haeKuva("gobbyliini.png");
        assertEquals(50, kuva.getWidth());
    }

    @Test
    public void testHaeRuudut() {
        ArrayList<Ruutu> ruudut = lataaja.haeRuudut();
        assertEquals(5, ruudut.size());
    }
}
