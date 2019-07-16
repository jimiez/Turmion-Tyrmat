/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.esineet;

import logiikka.hahmot.Hahmo;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class AseTest {

    Ase ase;

    public AseTest() {
    }

    @Before
    public void setUp() {
        ase = new Ase("testi", 1, 1, 1, 1, 1, "testi");
    }

    @Test
    public void testGetNopat() {

        int[] expResult = ase.getNopat();
        int[] result = ase.getNopat();
        assertEquals(result[0], expResult[0]);

    }

    @Test
    public void testVahinkoString() {

        String expResult = "1d1 + 1";
        String result = ase.vahinkoString();
        assertEquals(expResult, result);

    }
}
