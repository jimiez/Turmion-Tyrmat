/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.esineet;

import logiikka.hahmot.Hahmo;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author J-P
 */
public class RojuTest {
    
    Roju roju;
    
    public RojuTest() {
    }

   
    @Before
    public void setUp() {
        roju =new Roju("testi", 1, 1, "testi");
    }

    @Test
    public void testGetToiminto() {
       
        boolean result = roju.getToiminto(null);
        assertEquals(false, result);
       
    }
}
