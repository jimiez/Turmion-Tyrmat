/**
 * Rajapinta joka kaikkien hyökkäysten tulee toteuttaa
 */
package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.apuluokat.ToistaAani;
import logiikka.hahmot.Hahmo;

/**
 * Kaikkien hyökkäysten yliluokka
 */
public abstract class Hyokkays {

    private String nimi;
    private Hahmo hyokkaaja;
    private Hahmo puolustaja;
    private Peli peli;
    private int hyokkays;
    private int vahinko;
    private int puhti;

        /**
     * Konstruktoriin määritellään hyökkääjä, puolustaja ja viittaus peli-olioon
     * jonka puitteissa hyökkäys tapahtuu
     *
     * @param hyokkaaja Hyökkäävä hahmo
     * @param puolustaja Puolustava hahmo
     * @param peli Viittaus peli-olioon jonka sisällä hyökkäys tapahtuu
     */
    public Hyokkays(Hahmo hyokkaaja, Hahmo puolustaja, Peli peli) {
        this.hyokkaaja = hyokkaaja;
        this.puolustaja = puolustaja;
        this.peli = peli;
    }

    /**
     * Metodi jonka sisällä varsinainen hyökkäys tapahtuu
     *
     */
    public void teeHyokkays() {

        if (hyokkaaja.vahennaPuhtia(puhti)) {
            boolean osuma = haeOsuma();

            if (hyokkaaja.getTyyppi().equals("pelaaja")) {
                if (osuma) {
                    ToistaAani.OSUMA.toista();
                } else {
                    ToistaAani.HUTI.toista();
                }
            }

            kirjaaHeitto();
            if (osuma) {
                if (teeVahinko() && hyokkaaja.getTyyppi().equals("pelaaja")) {
                    kirjaaOsuma();
                    kirjaaTappo();
                } else {
                    kirjaaOsuma();
                }
            } else {
                kirjaaHuti();
            }
        } else {
            peli.getLoki().kirjaa("Ei tarpeeksi puhtia hyökkäyksen " + nimi + " ");
        }
    }

    /**
     * Abstrakti metodi, määrittelee hyökkäyksen osumaehdot
     *
     * @return Palauttaa true jos hyökkäys osuu
     */
    public abstract boolean haeOsuma();

    /**
     * Abstrakti metodi, määrittelee miten hyökkäys tekee vahinkoa
     *
     * @return Palauttaa true jos hyökkäyksen kohde kuolee
     */
    public abstract boolean teeVahinko();

    public abstract String haeKuvaus();

    public String getNimi() {
        return nimi;
    }

    /**
     * Kirjaa lokiin heiton
     *
     */
    private void kirjaaHeitto() {
        this.peli.getLoki().kirjaa(hyokkaaja + " yrittää osua kohteeseen " + puolustaja + " hyökkäyksellä " + getNimi() + ": " + hyokkays + " vs. AC " + puolustaja.getAC());
    }

    /**
     * Kirjaa lokiin vahingon
     *
     */
    private void kirjaaOsuma() {
        this.peli.getLoki().kirjaa("Osuma! " + vahinko + " pistettä vahinkoa kohteeseen " + puolustaja + " hyökkäyksellä  " + getNimi());
    }

    /**
     * Kirjaa lokiin hudin
     *
     */
    private void kirjaaHuti() {
        this.peli.getLoki().kirjaa(hyokkaaja + " ei osunut kohteeseen " + puolustaja + " hyökkäyksellä " + getNimi());
    }

    /**
     * Kirjaa lokiin pelaajan tekemän tapon. Jos pelaaja saa kukistamastaan
     * vihollisesta tasonnousun, kutsuu tasonnousuikkunan
     *
     */
    private void kirjaaTappo() {
        int xp = puolustaja.getXpArvo();
        peli.getLoki().kirjaa(puolustaja.getNimi() + " kuoli. " + xp + " kokemuspistettä!");
        if (peli.getPelaaja().lisaaExpaa(xp)) {
            this.peli.kutsuTasoIkkunaa();
        }
        peli.getLoki().kirjaaTappo(puolustaja);
        puolustaja.tapaHahmo();
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setHyokkays(int hyokkays) {
        this.hyokkays = hyokkays;
    }

    public void setVahinko(int vahinko) {
        this.vahinko = vahinko;
    }

    public Hahmo getHyokkaaja() {
        return hyokkaaja;
    }

    public Hahmo getPuolustaja() {
        return puolustaja;
    }

    public Peli getPeli() {
        return peli;
    }

    public void setPuhti(int puhti) {
        this.puhti = puhti;
    }
}
