package logiikka.hahmot;

import java.util.ArrayList;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.esineet.Ase;
import logiikka.esineet.Esine;
import logiikka.esineet.EsineTaulukot;

/**
 * Luokka jossa alustetaan kaikki pelin hirviöt. Peli - luokka käyttää karttojen
 * täyttämiseen hirviöillä
 *
 */
public class HirvioTaulukko {

    private ArrayList<Hirvio> tier1hirviot;
    private ArrayList<Hirvio> tier2hirviot;
    private ArrayList<Hirvio> tier3hirviot;
    private ArrayList<Hirvio> tier4hirviot;
    private EsineTaulukot esineTaulukot;

    /**
     * Konstruktorissa alustetaan kaikki eri tasoiset hirviötaulukot ja
     * täytetään ne
     *
     */
    public HirvioTaulukko() {
        esineTaulukot = new EsineTaulukot();
        tier1hirviot = new ArrayList<Hirvio>();
        tier2hirviot = new ArrayList<Hirvio>();
        tier3hirviot = new ArrayList<Hirvio>();
        tier4hirviot = new ArrayList<Hirvio>();
        lisaaHirviot();

    }

    /**
     * Lisää hirviöitä erillisiin taulukkoihin niiden haastavuuden perusteella
     *
     */
    private void lisaaHirviot() {

        // tier 1 hirviöt

        tier1hirviot.add(new Hirvio("Rotta", 7, 0, 25, "rotta.png", 4, new Ase("hirviöase", 0, 0, 1, 2, 0, "")));
        tier1hirviot.add(new Hirvio("Gobbyliini", 10, 1, 50, "gobbyliini.png", 6, new Ase("hirviöase", 0, 0, 1, 4, 0, "")));
        tier1hirviot.add(new Hirvio("Tappaja-ampiainen", 12, 2, 75, "amppari.png", 8, new Ase("hirviöase", 0, 0, 1, 6, 0, "")));
        tier1hirviot.add(new Hirvio("Rosmo", 14, 3, 100, "rosmo.png", 10, new Ase("hirviöase", 0, 0, 1, 6, 0, "")));
        
        // Tier 2 hirviöt

        tier2hirviot.add(new Hirvio("Lurkki", 10, 2, 100, "lurkki.png", 15, new Ase("hirviöase", 0, 0, 1, 6, 0, "")));
        tier2hirviot.add(new Hirvio("Ninja", 16, 4, 250, "ninja.png", 20, new Ase("hirviöase", 0, 0, 1, 8, 0, "")));
        tier2hirviot.add(new Hirvio("Liskomies", 12, 2, 125, "liskomies.png", 6, new Ase("hirviöase", 0, 0, 1, 8, 1, "")));
        tier2hirviot.add(new Hirvio("Kivihirviö", 15, 3, 150, "kivihirvio.png", 15, new Ase("hirviöase", 0, 0, 1, 10, 0, "")));
        tier2hirviot.add(new Hirvio("Kärmes", 14, 4, 200, "karmes.png", 16, new Ase("hirviöase", 0, 0, 2, 4, 0, "")));
        // Tier 3

        tier3hirviot.add(new Hirvio("Kiukkumöykky", 15, 5, 500, "kiukkumoykky.png", 25, new Ase("hirviöase", 0, 0, 1, 10, 1, "")));
        tier3hirviot.add(new Hirvio("Kyylä", 20, 5, 500, "kyyla.png", 20, new Ase("hirviöase", 0, 0, 1, 10, 1, "")));
        tier3hirviot.add(new Hirvio("Hirmuliskomies", 18, 4, 450, "liskomies.png", 20, new Ase("hirviöase", 0, 0, 1, 8, 2, "")));
        tier3hirviot.add(new Hirvio("Ilkeämielinen Velho", 18, 4, 550, "velho.png", 25, new Ase("hirviöase", 0, 0, 1, 10, 1, "")));

        tier4hirviot.add(new Hirvio(";D", 30, 15, 5000, "loppupomo.png", 250, new Ase("hirviöase", 0, 0, 5, 8, 5, "")));

    }

    /**
     * Palauttaa satunnaisen hirviön ja asettaa sille loottia
     *
     * @param tier Kuinka kovia hirviöitä arvotaan (1-3)
     * @return Arvottu hirviö
     */
    public synchronized Hirvio getRandomHirvio(int tier) {

        ArrayList<Hirvio> arvottavaLista;

        if (tier == 1) {
            arvottavaLista = tier1hirviot;
        } else if (tier == 2) {
            arvottavaLista = tier2hirviot;
        } else if (tier == 3) {
            arvottavaLista = tier3hirviot;
        } else if (tier == 4) {
            arvottavaLista = tier4hirviot;
        } else {
            arvottavaLista = null;
        }

        int arvottuHirvio = Tyokalupakki.heitaNoppaa(1, arvottavaLista.size()) - 1;
        Hirvio hirvio = arvottavaLista.get(arvottuHirvio);
        ArrayList<Esine> loot = esineTaulukot.arvoLoottia(tier);
        return new Hirvio(hirvio, loot);
    }
}
