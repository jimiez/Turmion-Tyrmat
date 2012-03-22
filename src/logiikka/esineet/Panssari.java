/**
 * Pohjaluokka kaikille pelin panssareille
 */
package logiikka.esineet;

import logiikka.hahmot.Hahmo;

/**
 * Kaikkien pelin panssariolioiden pohja
 *
 */
public class Panssari extends Esine {

    private int panssariArvo;

    /**
     * Konstruktoriin vaaditaan kaikki Esine - luokan parametrit
     *
     * @param panssariArvo Panssarin luokitus, posiitivinen kokonaisluku. Isompi
     * parempi.
     */
    public Panssari(String nimi, double paino, int arvo, String kuvaus, int panssariArvo) {
        super(nimi, paino, arvo, kuvaus);
        this.panssariArvo = panssariArvo;
    }

    /**
     * Konstruktorin kuormitettu versio olioiden kopiointia varten
     *
     * @param panssari Kopioitava panssari
     */
    public Panssari(Panssari panssari) {
        super(panssari.getNimi(), panssari.getPaino(), panssari.getArvo(), panssari.getKuvaus());
        this.panssariArvo = panssari.getPanssariArvo();
    }

    public synchronized int getPanssariArvo() {
        return panssariArvo;
    }

    /**
     * Palauttaa panssarin tiedot merkkijonona
     * @return Panssarin tiedot merkkijonona
     */
    @Override
    public String tulostaTiedot() {
        String tiedot = this.getNimi() + "\n\n"
                + "Paino: " + this.getPaino() + "\n"
                + "Arvo: " + this.getArvo() + " kultapalaa\n"
                + "Panssariluokka: +" + this.getPanssariArvo() + "\n\n";
        tiedot += super.getKuvaus();
        return tiedot;
    }

      /**
     * Määrittelee mitä tapahtuu kun panssaria käytetääa
     *
     * @return Palauttaa true kun panssarin pukeminen onnistui
     */
    @Override
    public boolean getToiminto(Hahmo hahmo) {
        hahmo.setPanssari(this);
        return true;
    }
}
