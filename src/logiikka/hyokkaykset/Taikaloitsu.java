package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.hahmot.Hahmo;

public class Taikaloitsu extends Hyokkays {

    /**
     * Konstruktoriin määritellään hyökkääjä, puolustaja ja viittaus peli-olioon
     * jonka puitteissa hyökkäys tapahtuu
     *
     * @param hyokkaaja Hyökkäävä hahmo
     * @param puolustaja Puolustava hahmo
     * @param peli Viittaus peli-olioon jonka sisällä hyökkäys tapahtuu
     */
    public Taikaloitsu(Hahmo hyokkaaja, Hahmo puolustaja, Peli peli) {
        super(hyokkaaja, puolustaja, peli);
        setNimi("Taikaloitsu");
        setPuhti(getHyokkaaja().getMaksimiPuhti());
    }

    /**
     * Taikaloitsu osuu aina.
     *
     * @return Palauttaa aina true, koska taikaloitsu osuu aina.
     */
    @Override
    public boolean haeOsuma() {
        return true;
    }

    /**
     * Tekee puolustajaan pelaajan taso * 1d3 + taso verran vahinkoa
     *
     * @return Palauttaa true jos osuman vastaanottaja kuoli sen seurauksena
     */
    @Override
    public boolean teeVahinko() {
        int lvl = getPeli().getPelaaja().getLvl();
        int vahinkoHeitto = Tyokalupakki.heitaNoppaa(lvl, 3) + lvl;
        setVahinko(vahinkoHeitto);
        getPeli().getLoki().kirjaaAnnettuaDamaa(vahinkoHeitto);
        if (getPuolustaja().otaVahinkoa(vahinkoHeitto)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String haeKuvaus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
