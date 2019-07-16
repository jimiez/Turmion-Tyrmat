/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.esineet;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class EsineTaulukotTest {
    
    EsineTaulukot esineet;
    
    public EsineTaulukotTest() {
    }

   
    @Before
    public void setUp() {
       esineet = new EsineTaulukot();
       
    }

    @Test
    public void testGetAse() {
        System.out.println("getAse");
        String ase = "Pitkämiekka";
        
        Ase result = esineet.getAse(ase);
        assertEquals("Pitkämiekka", result.getNimi());
     
    }

    @Test
    public void testGetPanssari() {
        
        String panssari = "Nahkanuttu";
        
        Panssari result = esineet.getPanssari(panssari);
        assertEquals("Nahkanuttu", result.getNimi());
      
    }

    @Test
    public void testTeeTaikaAse() {
        System.out.println("teeTaikaAse");
        Ase ase = esineet.getRandomAse(1);
        Ase uusase = esineet.teeTaikaAse(ase, 1);
               
    
        assertEquals(1, uusase.getPlussat());
       
    }
}
