package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.hahmot.Hahmo;

/**
 * Hirviöiden perushyökkäys joka tapahtuu aina kun hirviö hyökkää pelaajan tai
 * toisen hirviön kimpppuun
 */
public class HirvioHyokkays extends Hyokkays {

    /**
     * Konstruktoriin määritellään hyökkääjä, puolustaja ja viittaus peli-olioon
     * jonka puitteissa hyökkäys tapahtuu
     *
     * @param hyokkaaja Hyökkäävä hahmo
     * @param puolustaja Puolustava hahmo
     * @param peli Viittaus peli-olioon jonka sisällä hyökkäys tapahtuu
     */
    public HirvioHyokkays(Hahmo hyokkaaja, Hahmo puolustaja, Peli peli) {
        super(hyokkaaja, puolustaja, peli);
        setNimi("Perushyökkäys");
        setPuhti(0);
    }

    /**
     * Hyökkäyksen kuvaus merkkijonona
     *
     */
    @Override
    public String haeKuvaus() {
        return "Hirmuisa hyökkäys";
    }

    /**
     * Tekee vahinkoa puolustajaan jos hyökkäys osuu
     *
     * @return Palauttaa true jos osuman vastaanottaja kuoli sen seurauksena
     */
    @Override
    public boolean teeVahinko() {
        int vahinkoHeitto = Tyokalupakki.heitaNoppaa(getHyokkaaja().getAse().getNopat());
        setVahinko(vahinkoHeitto);
        getPeli().getLoki().kirjaaOtettuaDamaa(vahinkoHeitto);
        if (getPuolustaja().otaVahinkoa(vahinkoHeitto)) {
            getPeli().getLoki().kirjaa(getPuolustaja() + " kuoli.");
            getPeli().getLoki().setTappaja(getHyokkaaja());
            return true;
        } else {
            return false;
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
}