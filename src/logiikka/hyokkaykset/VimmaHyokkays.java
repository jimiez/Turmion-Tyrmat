package logiikka.hyokkaykset;

import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.hahmot.Hahmo;

/**
 * Vimmahyökkäys, joka tekee paljon vahinkoa, mutta ei osu yhtä helposti kuin
 * tavallinen hyökkäys
 *
 */
public class VimmaHyokkays extends Hyokkays {

    /**
     * Konstruktoriin määritellään hyökkääjä, puolustaja ja viittaus peli-olioon
     * jonka puitteissa hyökkäys tapahtuu
     *
     * @param hyokkaaja Hyökkäävä hahmo
     * @param puolustaja Puolustava hahmo
     * @param peli Viittaus peli-olioon jonka sisällä hyökkäys tapahtuu
     */
    public VimmaHyokkays(Hahmo hyokkaaja, Hahmo puolustaja, Peli peli) {
        super(hyokkaaja, puolustaja, peli);
        setNimi("Vimmahyökkäys");
        setPuhti(getHyokkaaja().getMaksimiPuhti() / 3);
    }

    /**
     * Arvotaan osuuko hyökkäys
     *
     * @return Palauttaa true jos hyökkäys osuu
     */
    @Override
    public boolean haeOsuma() {
        int hyokkaysHeitto = getHyokkaaja().getBAB() + Tyokalupakki.heitaNoppaa(1, 20) - 3;
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
        int vahinkoHeitto = (Tyokalupakki.heitaNoppaa(getHyokkaaja().getAse().getNopat()) + getHyokkaaja().getAse().getPlussat()) * 2;
        setVahinko(vahinkoHeitto);
        getPeli().getLoki().kirjaaAnnettuaDamaa(vahinkoHeitto);
        if (getPuolustaja().otaVahinkoa(vahinkoHeitto)) {
            getPeli().getLoki().kirjaa(getPuolustaja() + " kuoli.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String haeKuvaus() {
        return "Vimmaisa hyökkäys joka tekee valtavasti vahinkoa... jos osuuu";
    }
}
