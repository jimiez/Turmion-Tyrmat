package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.hahmot.Hahmo;

/**
 * Pelaajan perushyökkäys joka tapahtuu kun pelaaja yrittää liikkua varattuun
 * ruutuun
 *
 */
public class PerusHyokkays extends Hyokkays {

    /**
     * Konstruktoriin määritellään hyökkääjä, puolustaja ja viittaus peli-olioon
     * jonka puitteissa hyökkäys tapahtuu
     *
     * @param hyokkaaja Hyökkäävä hahmo
     * @param puolustaja Puolustava hahmo
     * @param peli Viittaus peli-olioon jonka sisällä hyökkäys tapahtuu
     */
    public PerusHyokkays(Hahmo hyokkaaja, Hahmo puolustaja, Peli peli) {
        super(hyokkaaja, puolustaja, peli);
        setNimi("Perushyokkäys");
        int puhti = getHyokkaaja().getMaksimiPuhti() / 5;

        if (puhti < 1) {
            setPuhti(2);
        } else {
            setPuhti(puhti);
        }
    }

    /**
     * Arvotaan osuuko hyökkäys
     *
     * @return Palauttaa true jos hyökkäys osuu
     */
    @Override
    public boolean haeOsuma() {
        int hyokkaysHeitto = getHyokkaaja().getBAB() + Tyokalupakki.heitaNoppaa(1, 20);
        setHyokkays(hyokkaysHeitto);
        if (hyokkaysHeitto >= getPuolustaja().getAC()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tekee vahinkoa puolustajaan jos hyökkäys osuu
     *
     * @return Palauttaa true jos osuman vastaanottaja kuoli sen seurauksena
     */
    @Override
    public boolean teeVahinko() {
        
        int voimaBonus = getPeli().getPelaaja().getMod(getPeli().getPelaaja().getVoima());
        
        int vahinkoHeitto = Tyokalupakki.heitaNoppaa(getHyokkaaja().getAse().getNopat())
                + getHyokkaaja().getAse().getPlussat() + voimaBonus;
        
        setVahinko(vahinkoHeitto);
        
        getPeli().getLoki().kirjaaAnnettuaDamaa(vahinkoHeitto);
        
        if (getPuolustaja().otaVahinkoa(vahinkoHeitto)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hyökkäyksen kuvaus (ei käytössä toistaiseksi)
     *
     */
    @Override
    public String haeKuvaus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
