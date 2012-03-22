package ui;

import javax.swing.JOptionPane;
import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.esineet.Ase;
import logiikka.esineet.Panssari;

/**
 * Käyttöliittymän lisätoimintoja
 */
public class Lisatoiminnot {

    private PaaNakyma paanakyma;
    private Peli peli;

    public Lisatoiminnot(PaaNakyma paanakyma, Peli peli) {
        this.paanakyma = paanakyma;
        this.peli = peli;
    }

    /**
     * Aktivoi huijausmoodin. Testaustarkoituksia varten.
     *
     */
    public void kooditPaalle() {
        this.peli.getPelaaja().asetaOminaisuudet(25, 25, 25);
        this.peli.getPelaaja().asetaHiparit(500);
        this.peli.getPelaaja().asetaPuhti(500);
        this.peli.getLoki().kirjaa("HUIJAUSMOODI AKTIVOITU, senkin huijarikakkiainen!");
        paanakyma.paivitaKaikki();
    }

    /**
     * Kutsutaan alussa, määrittelee pelaajan alkuominaisuudet
     *
     */
    public void heitaPelaajanStatsit() {

        int kerrat = 3;
        int voima;
        int kesto;
        int ketteryys;
        Ase ase;
        Panssari panssari;

        do {
            kerrat--;
            voima = Tyokalupakki.heitaNoppaa(3, 6);
            kesto = Tyokalupakki.heitaNoppaa(3, 6);
            ketteryys = Tyokalupakki.heitaNoppaa(3, 6);
            ase = this.peli.getEsineTaulukko().getRandomAse(1);
            panssari = this.peli.getEsineTaulukko().getRandomPanssari(1);

            if (kerrat < 1) {

                JOptionPane.showMessageDialog(null, "Yritykset loppuivat!\n"
                        + "Lopulliset ominaisuudet: \n"
                        + "     Voima: " + voima + "\n"
                        + "     Kestävyys: " + kesto + "\n"
                        + "     Ketteryys: " + ketteryys + "\n"
                        + "     Ase: " + ase.getNimi() + " (" + ase.vahinkoString() + ")\n"
                        + "     Panssari: " + panssari.getNimi() + " (+" + panssari.getPanssariArvo() + ")\n\n", "Näillä mennään...", JOptionPane.PLAIN_MESSAGE);
                break;
            }

            Object[] valinnat = {"Pidä nämä", "Heitä uudestaan"};
            int valinta = JOptionPane.showOptionDialog(null,
                    "Heitit seuraavanlaiset ominaisuudet: \n\n"
                    + "     Voima: " + voima + "\n"
                    + "     Kestävyys: " + kesto + "\n"
                    + "     Ketteryys: " + ketteryys + "\n"
                    + "     Ase: " + ase.getNimi() + " (" + ase.vahinkoString() + ")\n"
                    + "     Panssari: " + panssari.getNimi() + " (+" + panssari.getPanssariArvo() + ")\n\n"
                    + "Voit heittää vielä " + kerrat + " kertaa uudestaan.\n"
                    + "HUOM! Jos heität uudestaan, joudut pitämään ne ominaisuudet \n"
                    + "Vaikka ne olisivat huonommat kuin edelliset. Valitse viisaasti!",
                    "Onnenpyörä",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    valinnat,
                    valinnat[1]);

            if (valinta == 0 || kerrat < 1) {
                break;
            }
        } while (true);

        this.peli.getPelaaja().asetaOminaisuudet(voima, kesto, ketteryys);
        this.peli.getPelaaja().asetaHiparit(10 + this.peli.getPelaaja().getMod(kesto));
        this.peli.getPelaaja().asetaPuhti(10 + this.peli.getPelaaja().getMod(kesto));
        this.peli.getPelaaja().setAse(ase);
        this.peli.getPelaaja().setPanssari(panssari);
        this.peli.getReppu().lisaa(ase);
        this.peli.getReppu().lisaa(panssari);
    }

    /**
     * Kutsutaan pelin lopussa, kertaa tilastoja pelaajan seikkailuista
     *
     */
    public void kuolemaStatsit() {

        String kuolonViesti = "*** " + peli.getPelaaja().getNimi() + " ***\n"
                + "Oli kuollessaan tasolla " + peli.getPelaaja().getLvl() + "\n"
                + "Keräsi " + peli.getPelaaja().getXp() + " kokemuspistettä\n"
                + "Tappoi " + peli.getLoki().getTapetutHirviot() + " hirviötä\n"
                + "Teki yhteensä " + peli.getLoki().getAnnettuDama() + " pistettä vahinkoa\n"
                + "Otti yhteensä " + peli.getLoki().getOtettuDama() + " pistettä vahinkoa\n"
                + "Lopullisesti hänet kellisti: " + peli.getLoki().getTappaja() + "\n\n"
                + peli.getLoki().tulostaTapot();


        Object[] valinnat = {"Uusi peli", "Lopeta"};
        int valinta = JOptionPane.showOptionDialog(null, kuolonViesti, "Kuolonraportti",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                valinnat,
                valinnat[1]);

        if (valinta == 0) {
            paanakyma.uusiPeli();
        } else {
            System.exit(0);
        }
    }

    /**
     * Tasonnousun yhteydessä avautuva ikkuna
     */
    public void avaaTasonNousuIkkuna() {
        this.peli.getPelaaja().kasvataTasoa();
        int arvotutHiparit = Tyokalupakki.heitaNoppaa(1, 10) + this.peli.getPelaaja().getMod(this.peli.getPelaaja().getKesto());
        this.peli.getPelaaja().asetaHiparit(this.peli.getPelaaja().getMaksHP() + arvotutHiparit);

        int arvottuPuhti = Tyokalupakki.heitaNoppaa(1, 10) + this.peli.getPelaaja().getMod(this.peli.getPelaaja().getKesto());
        this.peli.getPelaaja().asetaPuhti(this.peli.getPelaaja().getMaksimiPuhti() + arvottuPuhti);

        String tasonNousuviesti = "Tervetuloa tasolle " + this.peli.getPelaaja().getLvl() + "\n\n"
                + "Tällä tasolla saat:\n"
                + arvotutHiparit + " osumapistettä lisää\n"
                + arvottuPuhti + " pistettä lisää puhtia\n"
                + "+1 hyökkäysbonukseen\n\n"
                + "Lisäksi saat valita yhden pisteen lisää: \n";

        Object[] valinnat = {"Voimaa", "Kestävyyttä", "Ketteryyttä"};
        int valinta = JOptionPane.showOptionDialog(null, tasonNousuviesti, "Onneksi olkoon!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                valinnat,
                valinnat[1]);

        int voimaNyt = this.peli.getPelaaja().getVoima();
        int kestoNyt = this.peli.getPelaaja().getKesto();
        int ketteryysNyt = this.peli.getPelaaja().getKetteryys();


        if (valinta == 0) {
            this.peli.getPelaaja().asetaOminaisuudet(voimaNyt + 1, kestoNyt, ketteryysNyt);
        } else if (valinta == 1) {
            this.peli.getPelaaja().asetaOminaisuudet(voimaNyt, kestoNyt + 1, ketteryysNyt);
        } else if (valinta == 2) {
            this.peli.getPelaaja().asetaOminaisuudet(voimaNyt, kestoNyt, ketteryysNyt + 1);
        }
    }

    
}
