package logiikka.apuluokat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import logiikka.hahmot.Hahmo;

/**
 * Luokka joka huolehtii lokikirjan täyttämisestä ja tulostamisesta, sekä pitää
 * tilastoa monista pelaajan teoista
 *
 */
public class Loki {

    private ArrayList<String> loki;
    private Map<String, Integer> tapot;
    private int kaikkiOtettuDama;
    private int kaikkiAnnettuDama;
    private int tapetutHirviot;
    private String tappaja;

    public Loki() {
        this.loki = new ArrayList<String>();
        this.loki.add("Uusi peli aloitettu!");
        this.tapot = new HashMap<String, Integer>();
        this.kaikkiOtettuDama = 0;
        this.kaikkiAnnettuDama = 0;
        this.tapetutHirviot = 0;
        this.tappaja = "???";
    }

    /**
     * Metodi merkintöjen lisäämiseksi lokikirjaan
     *
     * @param kirjaus Teksti joka halutaan lisätä lokiin
     */
    public void kirjaa(String kirjaus) {
        this.loki.add(kirjaus);
    }

    @Override
    public String toString() {
        String lokiTulostus = "";
        for (String s : this.loki) {
            lokiTulostus += s + "\n";
        }
        return lokiTulostus;
    }

    /**
     * Statistiikkaa. Kaikki pelaajan ottama vahinko.
     *
     * @param dama Lisätty vahinko
     *
     */
    public synchronized void kirjaaOtettuaDamaa(int dama) {
        this.kaikkiOtettuDama += dama;
    }

    /**
     * Statistiikkaa. Kaikki pelaajan tekemä vahinko.
     *
     * @param dama Lisätty vahinko
     *
     */
    public synchronized void kirjaaAnnettuaDamaa(int dama) {
        this.kaikkiAnnettuDama += dama;
    }

    /**
     * Statistiikkaa. Pitää kirjaa montako vihollista pelaaja on tappanut
     * yhteensä
     *
     * @param tapettu Tapettu hahmo
     */
    public synchronized void kirjaaTappo(Hahmo tapettu) {
        this.tapetutHirviot += 1;
        kirjaaHirvioTyyppi(tapettu);
    }

    /**
     * Statistiikkaa. Kirjaa ylös tapetun vihollisen nimen. Jos löytyy jo
     * ennestään listalta, lisätään lukumäärää vain yhdellä, muuten tehdään uusi
     * merkintä
     *
     * @param hahmo Tapettu hahmo
     */
    private synchronized void kirjaaHirvioTyyppi(Hahmo hahmo) {
        if (tapot.containsKey(hahmo.getNimi())) {
            int pelaajanTapot = this.tapot.get(hahmo.getNimi());
            this.tapot.put(hahmo.getNimi(), pelaajanTapot + 1);
        } else {
            this.tapot.put(hahmo.getNimi(), 1);
        }
    }

    /**
     * Statistiikkaa. Antaa merkkijonoesityksen pelaajan tappamista hirviöistä.
     * Järjestää tapetut hirviöt aakkosjärjestykseen.
     *
     * @return Kaikki tapetut hirviöt merkkijonoesityksenä
     */
    public String tulostaTapot() {
        String tappoString = "Tapetut hirviöt:\n";

        Object[] hirviot = tapot.keySet().toArray();
        Arrays.sort(hirviot);

        for (Object s : hirviot) {
            tappoString += s.toString() + ": " + this.tapot.get(s.toString()) + "\n";
        }
        return tappoString;
    }

    /**
     * Statistiikkaa. Kun pelaaja kuolee, kirjataan ylös hahmo joka hänet tappoi
     *
     * @param tappaja Mikä hirvioön pelaajan tappoi
     *
     */
    public void setTappaja(Hahmo tappaja) {
        this.tappaja = tappaja.getNimi();
    }

    public String getTappaja() {
        return tappaja;
    }

    public int getTapetutHirviot() {
        return this.tapetutHirviot;
    }

    public int getAnnettuDama() {
        return this.kaikkiAnnettuDama;
    }

    public int getOtettuDama() {
        return this.kaikkiOtettuDama;
    }
}
