package logiikka.esineet;

import logiikka.hahmot.Hahmo;

/**
 * Abstrakti yliluokka kaikille pelin esineille
 *
 */
public abstract class Esine {

    private String nimi;
    private double paino;
    private int arvo;
    private String kuvaus;
    private int bonus;

    /**
     * Konstruktorissa määritellään esineen nimi, paino ja arvo kultarahoissa
     *
     * @param nimi Esineen nimi, jolla se esiintyy pelin sisällä
     * @param paino Paino mysteeriyksiköissä
     * @param arvo Esineen arvo kultarahoissa
     */
    public Esine(String nimi, double paino, int arvo, String kuvaus) {
        this.nimi = nimi;

        if (this.paino < 0) {
            this.paino = 0;
        } else {
            this.paino = paino;
        }

        if (this.arvo < 0) {
            this.arvo = 0;
        } else {
            this.arvo = arvo;
        }

        this.kuvaus = kuvaus;
    }

    /**
     * Kaikilla aliluokilla tulee olla metodi joka tulostaa merkkijonona esineen
     * perustiedot
     *
     */
    public abstract String tulostaTiedot();

    /**
     * Aliluokkien pitää määritellä mitä tapahtuu, jos esine käytetään
     *
     */
    public abstract boolean getToiminto(Hahmo hahmo);

    public int getPlussat() {
        return bonus;
    }

    public String getNimi() {
        return nimi;
    }

    public int getArvo() {
        return arvo;
    }

    public double getPaino() {
        return paino;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    @Override
    public String toString() {

        return this.nimi;
    }

    public void setPlussat(int bonus) {
        this.bonus = bonus;
    }
}
