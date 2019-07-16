/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.apuluokat;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class TyokalupakkiTest {
    
    public TyokalupakkiTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testHaeEtaisyys_4args() {
       
        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 2;
        int expResult = 1;
        int result = Tyokalupakki.haeEtaisyys(x1, y1, x2, y2);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testHaeEtaisyys_intArr_intArr() {
    
        int[] ekaRuutu = {1,1};
        int[] tokaRuutu = {2,2};
        int expResult = 1;
        int result = Tyokalupakki.haeEtaisyys(ekaRuutu, tokaRuutu);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testHeitaNoppaa_int_int() {
        System.out.println("heitaNoppaa");
        int noppienMaara = 1;
        int silmaLuku = 1;
        int expResult = 1;
        int result = Tyokalupakki.heitaNoppaa(noppienMaara, silmaLuku);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testHeitaNoppaa_intArr() {
    
        int[] nopat = {1,1};
        int expResult = 1;
        int result = Tyokalupakki.heitaNoppaa(nopat);
        assertEquals(expResult, result);
       
    }
}
