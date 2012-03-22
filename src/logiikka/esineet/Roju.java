package logiikka.esineet;

import logiikka.hahmot.Hahmo;

/**
 * Satunnaisen (toistaiseksi) tarpeettoman sälän luokka.
 *
 */
public class Roju extends Esine {

    /**
     * Konstruktoriin määritellään yliluokan vaatinat parametrit eikä mitään
     * muuta
     */
    public Roju(String nimi, double paino, int arvo, String kuvaus) {
        super(nimi, paino, arvo, kuvaus);
    }

    /**
     * Kuormitetulla konstruktorilla voidaan kopioida rojua
     */
    public Roju(Roju roju) {
        super(roju.getNimi(), roju.getPaino(), roju.getArvo(), roju.getKuvaus());
    }

    /**
     * Rojun tiedot merkkijonona
     */
    @Override
    public String tulostaTiedot() {
        String tiedot = getNimi() + "\n\n"
                + getKuvaus() + "\n\n"
                + "Paino: " + getPaino() + "\n"
                + "Arvo: " + getArvo() + " kultapalaa";

        return tiedot;
    }

    /**
     * Mitä tapahtuu kun rojua käytetään (ei mitään)
     */
    @Override
    public boolean getToiminto(Hahmo hahmo) {
        return false;
    }
}
