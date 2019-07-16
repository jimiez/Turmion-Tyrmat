/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.esineet;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class PanssariTest {

    Panssari panssari;

    public PanssariTest() {
    }

    @Before
    public void setUp() {
        panssari = new Panssari("testi", 1, 1, null, 1);
    }

    @Test
    public void testGetPanssariArvo() {
        int expResult = 1;
        int result = panssari.getPanssariArvo();
        assertEquals(expResult, result);
    }


}
