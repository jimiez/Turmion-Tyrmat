package logiikka;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import logiikka.esineet.Esine;

/**
 * Luokka joka pitää kirja pelaajan kantamista esineistä. Esine-oliot säilötään
 * aakkoselliseen treemappiin, jossa avaimena toimii esineen/esineiden nimi ja
 * arvoina viittauksia kyseisen nimisiin olioihin
 *
 */
public class Reppu {

    /**
     * Esineet säilötään HahsMappiin muodossa esine - lukumäärä
     */
    private Map<String, ArrayList<Esine>> invo;
    private Peli peli;

    /**
     * Konstruktorissa vain alustetaan HashMap
     *
     */
    public Reppu(Peli peli) {
        this.invo = new TreeMap<String, ArrayList<Esine>>();
        this.peli = peli;
    }

    /**
     * Metodi jolla esineitä voi lisätä reppuun. Jos esine löytyy ennestään,
     * kasvatetaan vain lukumäärää.
     *
     * @param esine Lisättävä esine
     *
     */
    public synchronized boolean lisaa(Esine esine) {

        if (esine.getPaino() + this.getKokonaisPaino() < this.peli.getPelaaja().getMaksimipaino()) {
            if (this.invo.containsKey(esine.getNimi())) {
                this.invo.get(esine.getNimi()).add(esine);
            } else {
                ArrayList<Esine> uusiEsine = new ArrayList<Esine>();
                uusiEsine.add(esine);
                this.invo.put(esine.getNimi(), uusiEsine);
            }
            this.peli.getLoki().kirjaa(esine.getNimi() + " lisättiin reppuun!");
            return true;
        } else {
            this.peli.getLoki().kirjaa(esine.getNimi() + " ei mahtunut reppuun!");
            return false;
        }
    }

    /**
     * Vähentää repussa olevien tiettyjen esineiden lukumäärää yhdellä. Poistaa
     * esineen kokonaan jos lukumäärä on alle yhden
     *
     * @param esine Esine jonka lukumäärää halutaan vähentää
     */
    public synchronized boolean tuhoa(String esine) {

        if (this.invo.containsKey(esine)) {
            this.invo.get(esine).remove(this.invo.get(esine).size() - 1);
        } else {
            return false;
        }

        if (this.invo.get(esine).size() < 1) {
            this.invo.remove(esine);
            this.peli.getLoki().kirjaa(esine + " tuhottiin!");
            return true;
        }
        return true;
    }

    /**
     * Käy läpi koko repun sisällön ja laskee esineiden yhteispainon
     *
     * @return Palauttaa repun esineiden yhteispainon kuukivi-yksiköissä
     */
    public double getKokonaisPaino() {
        double kokonaispaino = 0;

        for (String n : this.invo.keySet()) {
            for (Esine e : this.invo.get(n)) {
                kokonaispaino += e.getPaino();
            }
        }

        return kokonaispaino;
    }

    /**
     * Käy läpi koko repun sisällön ja laskee esineiden yhteismäärän
     *
     * @return Palauttaa repun esineiden yhteismäärän
     */
    public int getKokonaisMaara() {

        int maara = 0;

        for (String n : this.invo.keySet()) {
            maara += this.invo.get(n).size();
        }
        return maara;
    }

    @Override
    public String toString() {
        return "Laukussa on " + this.getKokonaisMaara() + " esinettä ja ne painavat " + this.getKokonaisPaino();
    }

    /**
     * Tulostaa kaikki repun esineet. Testaustarkoituskiin lähinnä.
     *
     */
    public void tulostaEsineet() {
        for (String n : this.invo.keySet()) {
            System.out.println(n + " x " + this.invo.get(n).size());
        }
    }

    /**
     * Palauttaa esineen (jos löytyy)
     *
     * @return Palauttaa halutun esineen
     */
    public Esine getEsine(String haettava) {
        for (String n : this.invo.keySet()) {
            for (Esine e : this.invo.get(n)) {
                if (e.getNimi().equals(haettava)) {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * Palauttaa esineen (jos löytyy)
     *
     * @return Palauttaa halutun esineen
     */
    public Map<String, ArrayList<Esine>> palautaKaikki() {
        return this.invo;
    }

    /**
     * Palauttaa repun sisältämien esineiden yhteispainon ja pelaajan
     * maksimikantokyvyn
     *
     * @return Palauttaa repun sisältämien esineiden yhteispainon ja pelaajan
     * maksimikantokyvyn
     */
    public String painoString() {
        return this.getKokonaisPaino() + " / " + this.peli.getPelaaja().getMaksimipaino();
    }
}
