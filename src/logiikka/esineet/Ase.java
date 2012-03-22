package logiikka.esineet;

import logiikka.hahmot.Hahmo;

/**
 * Pelin kaikkien aseiden templaatti
 */
public class Ase extends Esine {

    /**
     * Ensimmäisessä alkiossa noppien lukumäärä, toisessa niiden silmäluku esim.
     * nopat[0] = 1; nopat[1] = 6 on sama kuin 1d6
     *
     */
    private int[] nopat;

    /**
     * Konstruktoriin vaaditaan kaikki yläluokan parametrit, lisäksi
     * määritellään vahinkoon tarvittavat nopat ja mahdollinen lisävahinko
     *
     * @see Esine
     * @param nopat Noppien lukumäärä
     * @param silmaluku Noppien silmäluku
     * @param bonus Jos ase on vaikkapa +1 tmv.
     *
     */
    public Ase(String nimi, double paino, int arvo, int nopat, int silmaluku, int bonus, String kuvaus) {
        super(nimi, paino, arvo, kuvaus);
        this.nopat = new int[2];
        this.nopat[0] = nopat;
        this.nopat[1] = silmaluku;
        super.setPlussat(bonus);
    }

    /**
     * Kuormitettu konstruktori jolla voi kopioida aseita
     *
     * @see Esine
     * @param ase Ase joka halutaan kopioida
     *
     *
     */
    public Ase(Ase ase) {
        super(ase.getNimi(), ase.getPaino(), ase.getArvo(), ase.getKuvaus());
        this.nopat = ase.getNopat();
        this.setPlussat(ase.getPlussat());
    }

    /**
     * Kuormitettu konstruktori jolla voi kopioida bonuksella varustettuja
     * aseita
     *
     * @see Esine
     * @param ase Ase joka halutaan kopioida
     * @param bonus lisättävän aseen bonus
     */
    public Ase(Ase ase, int bonus) {
        super(ase.getNimi() + " +" + bonus, ase.getPaino(), ase.getArvo(), ase.getKuvaus());
        this.nopat = ase.getNopat();
        this.setPlussat(bonus);
    }

    /**
     * Palauttaa aseen vahinkopotentiaalin
     *
     * @return nopat Noppien lkm ja vahinko
     */
    public synchronized int[] getNopat() {
        return this.nopat;
    }

    /**
     * Palauttaa aseen tiedot merkkijonona
     *
     * @return Aseen tiedot merkkijonan
     */
    @Override
    public String tulostaTiedot() {
        String tiedot = this.getNimi() + "\n\n"
                + "Paino: " + this.getPaino() + "\n"
                + "Arvo: " + this.getArvo() + " kultapalaa\n"
                + "Vahinko: " + (this.nopat[0] + this.getPlussat()) + " - "
                + ((this.nopat[0] * this.nopat[1]) + this.getPlussat()) + "\n\n";
        if (this.getPlussat() > 0) {
            tiedot += "+" + this.getPlussat() + " osumiseen.\n\n";
        }
        tiedot += super.getKuvaus();
        return tiedot;
    }
    
     /**
     * Palauttaa aseen vahinkopotentiaalin merkkijonona
     *
     * @return Aseen vahinkopotentialai merkkijona (esim. 1-6)
     */
    public String vahinkoString() {
        if (this.getPlussat() < 1) {
            return "" + this.nopat[0] + "d" + this.nopat[1];
        } else {
            return "" + this.nopat[0] + "d" + this.nopat[1] + " + " + this.getPlussat();
        }
    }
    
    
     /**
     * Mitä tapahtuu kun asetta käytetään / se varustetaan
     *
     * @return Palauttaa true kun ase on otettu käyttöön
     */    
    @Override
    public boolean getToiminto(Hahmo hahmo) {
        hahmo.setAse(this);
        return true;
    }
}
