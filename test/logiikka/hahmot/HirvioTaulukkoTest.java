/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.hahmot;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author J-P
 */
public class HirvioTaulukkoTest {

    HirvioTaulukko hTaulukko;

    public HirvioTaulukkoTest() {
    }

    @Before
    public void setUp() {
        hTaulukko = new HirvioTaulukko();
    }

    @Test
    public void testGetRandomHirvio() {
        System.out.println("getRandomHirvio");
        int tier = 4;
        Hirvio result = hTaulukko.getRandomHirvio(tier);
        assertEquals(";D", result.getNimi());

    }
}
