package logiikka.hahmot;

import java.util.ArrayList;
import logiikka.esineet.Ase;
import logiikka.esineet.Esine;

/**
 * Kaikkien hirviöiden perusluokka
 *
 */
public class Hirvio extends Hahmo {

    private int AC;
    private int BAB;
    private int xpArvo;

    /**
     * Konstruktorissa määritellään lähes kaikki hirviön ominaisuudet
     *
     * @param nimi Hirviön nimi pelin sisällä
     * @param AC Hirviön panssariluokka, eli kuinka vaikea siihen on osua
     * @param BAB Hirviön hyökkäsbonus, eli kuinka helposti se osuu
     * @param xpArvo Kuinka paljon kokemuspisteitä hirviön tappamisesta saa
     * @param hiparit Kuinka monta osumapistettä hirviöllä on
     *
     */
    public Hirvio(String nimi, int AC, int BAB, int xpArvo, String kuva, int hiparit, Ase ase) {
        super(nimi, kuva, ase);
        this.AC = AC;
        this.BAB = BAB;
        this.xpArvo = xpArvo;
        asetaHiparit(hiparit);
    }

    /**
     * Konstruktorin kuormitettu versio hirviöiden kopioimista varten
     *
     * @param h kopioitava hirviö
     * @param loot hirviölle asetettava loot
     *
     */
    public Hirvio(Hirvio h, ArrayList<Esine> loot) {
        super(h.getNimi(), h.getKuva(), new Ase(h.getAse()));
        this.AC = h.getAC();
        this.BAB = h.getBAB();
        this.xpArvo = h.getXpArvo();
        asetaHiparit(h.getMaksHP());
        asetaLoot(loot);
    }

    /**
     * Ei tee mitään koska hirviöt eivät käytä puhtia
     *
     * @return Palauttaa aina 0
     */
    @Override
    public synchronized int getPuhtiNyt() {
        return 0;
    }

    /**
     * Ei tee mitään koska hirviöt eivät käytä puhtia
     *
     * @return Palauttaa aina 0
     */
    @Override
    public synchronized int getMaksimiPuhti() {
        return 0;
    }

    /**
     * Ei tee mitään koska hirviöt eivät käytä puhtia
     *
     * @return Palauttaa aina true
     */
    @Override
    public synchronized boolean vahennaPuhtia(int puhti) {
        return true;
    }

    /**
     * Ei tee mitään koska hirviöt eivät käytä puhtia
     *
     * @return Palauttaa aina true
     */
    @Override
    public synchronized boolean lisaaPuhtia(int muutos) {
        return true;
    }

    @Override
    public synchronized String getTyyppi() {
        return "hirviö";
    }

    @Override
    public synchronized int getXpArvo() {
        return this.xpArvo;
    }
        
    @Override
    public synchronized int getAC() {
        return this.AC;
    }

    @Override
    public synchronized int getBAB() {
        return this.BAB;
    }
    
}
