package logiikka.esineet;

import java.util.ArrayList;
import logiikka.Peli;
import logiikka.apuluokat.Tyokalupakki;

/**
 * Luokka jossa luodaan ja alustetaan kaikki pelin esineet. Sisältää toimintoja
 * myös niiden noutamiseen ja satunnaisarteiden generoimiseen
 */
public final class EsineTaulukot {

    private ArrayList<Ase> tier1Ase;
    private ArrayList<Ase> tier2Ase;
    private ArrayList<Ase> tier3Ase;
    private ArrayList<Roju> rojuTaulukko;
    private ArrayList<Panssari> tier1Panssari;
    private ArrayList<Panssari> tier2Panssari;
    private ArrayList<Panssari> tier3Panssari;
    private ArrayList<Ase> aseTaulukko;
    private ArrayList<Panssari> panssariTaulukko;

    /**
     * Konstruktorissa alustetaan ja täytetään kaikki taulukot
     */
    public EsineTaulukot() {

        tier1Ase = new ArrayList<Ase>();
        tier2Ase = new ArrayList<Ase>();
        tier3Ase = new ArrayList<Ase>();
        rojuTaulukko = new ArrayList<Roju>();
        tier1Panssari = new ArrayList<Panssari>();
        tier2Panssari = new ArrayList<Panssari>();
        tier3Panssari = new ArrayList<Panssari>();

        this.rojuTaulukko = new ArrayList<Roju>();

        aseTaulukko = new ArrayList<Ase>();
        panssariTaulukko = new ArrayList<Panssari>();
        lisaaAseet();
        lisaaPanssarit();
        lisaaRojut();
    }

    /**
     * Lisää taulukkoon joukon aseita, joita sitten voidaan noutaa erinäisillä
     * käskyillä
     */
    private void lisaaAseet() {

        // Tier 1

        tier1Ase.add(new Ase("Tikari", 1, 2, 1, 4, 0, "Joskus on näppärää jos mukana on vähän pienempikin leuku."));
        tier1Ase.add(new Ase("Lyhytmiekka", 3, 10, 1, 6, 0, "Ei se miekan koko, vaan se miten sitä käyttää!"));
        tier1Ase.add(new Ase("Sapeli", 15, 3, 1, 6, 0, "Käyrä miekka. Puhkoo silti hyvin keuhkoja."));

        // Tier 2

        tier2Ase.add(new Ase("Pitkämiekka", 4, 15, 1, 8, 0, "Kynä on miekkaa mahtavampi... Harhaisten vanhojen miesten kuvitelmissa. Mora keuhkossa lopettaa väittelyt nopeammin kuin paraskaan argumentti!"));
        tier2Ase.add(new Ase("Taistelukirves", 7, 10, 1, 8, 0, "Iso kirves, joka soveltuu erinomaisesti mm. kallojen halkaisuun."));
        tier2Ase.add(new Ase("Käsikirves", 6, 5, 1, 6, 0, "Vähän pienempi kirves, jolla saa näppärästi hakattua halkoja ja vihollisten ruumiinosia."));


        tier3Ase.add(new Ase("Äpärämiekka", 10, 35, 1, 10, 0, "Tunnetaan myös puolentoistakäden miekkana. Tappaa talossa ja puutarhassa."));
        tier3Ase.add(new Ase("Suurmiekka", 15, 50, 2, 6, 0, "Miehisten miesten miehinen miekka"));
        tier3Ase.add(new Ase("Suurkirves", 20, 20, 1, 12, 0, "Kirves joka huomataan. Viimeistään siinä vaiheessa kun se halkoo sinun ja koko naapurustosi kallot."));
        tier3Ase.add(new Ase("Tuskanuija", 5, 10, 1, 10, 0, "Iso nuija, joka on suunniteltu tuottamaan maksimaalista tuskaa."));

        for (Ase a : tier1Ase) {
            aseTaulukko.add(a);
        }

        for (Ase a : tier2Ase) {
            aseTaulukko.add(a);
        }

        for (Ase a : tier3Ase) {
            aseTaulukko.add(a);
        }
    }

    /**
     * Hakee asetaulukosta uuden aseen ja luo sen pohjalta uuden ase-olion
     *
     * @param ase Ase joka halutaan
     * @return Palauttaa aseen jos se löytyy luettelosta, muuten palauttaa null
     */
    public Ase getAse(String ase) {

        for (Ase a : aseTaulukko) {
            if (a.getNimi().equals(ase)) {
                return new Ase(a);
            }
        }
        return null;
    }

    /**
     * Arpoo taulukosta aseen ja luo sen pohjalta uuden ase-olion
     *
     * @param tier Minkä luokan aseita arvotaan
     * @return Arvottu ase
     */
    public Ase getRandomAse(int tier) {

        ArrayList<Ase> arvottavaLista;

        if (tier == 1) {
            arvottavaLista = tier1Ase;
        } else if (tier == 2) {
            arvottavaLista = tier2Ase;
        } else if (tier == 3) {
            arvottavaLista = tier3Ase;
        } else {
            arvottavaLista = null;
        }

        int arvottuAse = Tyokalupakki.heitaNoppaa(1, arvottavaLista.size()) - 1;
        Ase ase = arvottavaLista.get(arvottuAse);
        return new Ase(ase);
    }

      /**
     * Lisää taulukkoon joukon panssareita, joita sitten voidaan noutaa erinäisillä
     * käskyillä
     */
    private void lisaaPanssarit() {

        // Kevyet
        tier1Panssari.add(new Panssari("Toppatakki", 10, 5, "Lievästi topattu takki. Ei suojaa kovin hyvin.", 1));
        tier1Panssari.add(new Panssari("Nahkanuttu", 15, 10, "'Voin tehdä mitä haluan, koska näytän hyvältä nahkavaatteissa.'\n-Cody Chestnutt", 2));
        tier1Panssari.add(new Panssari("Niitattu nahkanuttu", 20, 25, "Uuuu, nahkaa.... OOooooo, niitattua nahkaa. :3", 3));
        tier1Panssari.add(new Panssari("Rengaspaita", 25, 100, "Pienistä silmukoista koostettu paita. Torppaa hyvin teriä.", 4));

        // Keski

        tier2Panssari.add(new Panssari("Suomupanssari", 30, 50, "Limittäisistä levyistä koostettu metallipanssari.", 4));
        tier2Panssari.add(new Panssari("Peltipaita", 40, 150, "Ruma, mutta toimiva.", 5));

        // Raskas

        tier3Panssari.add(new Panssari("Bändipanssari", 35, 250, "Isoista teräsrenkaista koostettu raskas haarniska. Toimii!", 6));
        tier3Panssari.add(new Panssari("Puolilevypanssari", 50, 600, "Melkein yhtä hyvä kuin täysi levypanssari.", 7));
        tier3Panssari.add(new Panssari("Levypanssari", 50, 600, "Keskiaikafantasian M1A1 Abrams", 8));

        for (Panssari p : tier1Panssari) {
            panssariTaulukko.add(p);
        }
        for (Panssari p : tier2Panssari) {
            panssariTaulukko.add(p);
        }
        for (Panssari p : tier3Panssari) {
            panssariTaulukko.add(p);
        }
    }

      /**
     * Lisää taulukkoon joukon rojua, joita sitten voidaan noutaa erinäisillä
     * käskyillä
     */
    private void lisaaRojut() {

        rojuTaulukko.add(new Roju("Rotanhäntä", 1, 1, "Miksi joku kantaa mukanaan kuolleen rotan häntää?"));
        rojuTaulukko.add(new Roju("Tonnikalapurkki", 1, 5, "Purkillinen täynnä jonkun myyttisen kauan sitten kuolleen jalon villieläimen lihaa. Aika hyvää."));
        rojuTaulukko.add(new Roju("Kivan näköinen kivi", 1, 15, "Nätti kivi."));
        rojuTaulukko.add(new Roju("Kirja", 1, 10, "Nahkaisten kansien sisälle suljettu läjä paperisivuja, jotka ovat täynnä koukeroita jotka eivät sano sinulle mitään."));
        rojuTaulukko.add(new Roju("Kuollut hamsteri", 1, 1, "Marsu, joka on kuollut."));
        rojuTaulukko.add(new Roju("Elävä hamsteri", 1, 1, "Marsu, joka ei ole kuollut. Se katsoo sinua merkitsevästi."));
        rojuTaulukko.add(new Roju("Kultakalamalja", 5, 20, "On todellinen mysteeri miten tämä on säilynyt ehjänä."));
        rojuTaulukko.add(new Roju("Rikkinäinen jalokivi", 1, 10, "Jalokivi, joka on ehkä joskus muinoin ollut hieno."));
    }

    /**
     * Palauttaa panssari - olion nimen perusteella
     *
     * @param panssari Panssarin nimi, joka halutaan hakea
     * @return Palauttaa panssarin listalta jos se löytyy
     */
    public Panssari getPanssari(String panssari) {

        for (Panssari p : panssariTaulukko) {
            if (p.getNimi().equals(panssari)) {
                return new Panssari(p);
            }
        }
        return null;
    }

    /**
     * Palauttaa satunnaisen panssarin
     *
     * @param tier Minkä tasoisia esineitä arvotaan
     * @return Satunnainen panssari
     */
    public Panssari getRandomPanssari(int tier) {

        ArrayList<Panssari> arvottavaLista;

        if (tier == 1) {
            arvottavaLista = tier1Panssari;
        } else if (tier == 2) {
            arvottavaLista = tier2Panssari;
        } else if (tier == 3) {
            arvottavaLista = tier3Panssari;
        } else {
            arvottavaLista = null;
        }

        int arvottuPanssari = Tyokalupakki.heitaNoppaa(1, arvottavaLista.size()) - 1;
        Panssari panssari = arvottavaLista.get(arvottuPanssari);
        return new Panssari(panssari);
    }

    /**
     * Palauttaa satunnaisen sälänm
     *
     * @return Satunnainen sälä
     */
    public Roju getRandomRoju() {
        int arvottuRoju = Tyokalupakki.heitaNoppaa(1, rojuTaulukko.size()) - 1;
        Roju roju = rojuTaulukko.get(arvottuRoju);
        return new Roju(roju);
    }

      /**
     * Arpoo loottia. Käytetään pääasiassa kartalle luotavia hirviöitä varten. 
     * @see Peli
     * @param tier Minkä tasoista loottia arvotaan (1-3)
     * @return Listauksen loottia (jos arpaonni on suonut)
     */
    public synchronized ArrayList<Esine> arvoLoottia(int tier) {

        if (tier > 3) {
            return null;
        }
        ArrayList<Esine> arvottuRoina = new ArrayList<Esine>();

        int noppa = Tyokalupakki.heitaNoppaa(1, 100);
        int lkm;
        if (noppa >= 95) {
            lkm = 3;
        } else if (noppa > 85 && noppa < 95) {
            lkm = 2;
        } else if (noppa > 50 && noppa <= 85) {
            lkm = 1;
        } else {
            lkm = 0;
        }

        for (int i = 0; i < lkm; i++) {

            int noppa2 = Tyokalupakki.heitaNoppaa(1, 20);

            // Jos heittää tasan 20 saa taika-aseen
            if (noppa2 == 20) {
                arvottuRoina.add(teeTaikaAse(getRandomAse(tier), tier));
            } else if (noppa2 > 14 && noppa2 < 20) {
                arvottuRoina.add(getRandomAse(tier));
            } else if (noppa2 > 9 && noppa2 < 15) {
                arvottuRoina.add(getRandomPanssari(tier));
            } else {
                arvottuRoina.add(getRandomRoju());
            }
        }
        return arvottuRoina;
    }

    /**
     * Tekee normaalista aseesta plussa-version
     *
     * @param ase Ase josta halutaan tehdä plussaversio
     * @param tier Kuinka suuren plussan esine saa
     * @return Plussalla varustettu ase
     */
    public Ase teeTaikaAse(Ase ase, int tier) {
        return new Ase(ase, tier);
    }
}
