package logiikka;

import logiikka.apuluokat.Lataaja;
import logiikka.apuluokat.Loki;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.esineet.EsineTaulukot;
import logiikka.hahmot.Hahmo;
import logiikka.hahmot.HirvioTaulukko;
import logiikka.hahmot.Pelaaja;
import logiikka.hyokkaykset.HirvioHyokkays;
import logiikka.hyokkaykset.Hyokkays;
import logiikka.maailma.Kartta;
import ui.PaaNakyma;

/**
 * Varsinaisesta pelilogiikasta huolehtiva keskusluokka joka alustaa myös
 * käytännössä kaikki pelin keskeiset oliot ja pitää huolta pelin edistymisestä
 *
 */
public class Peli {

    private Loki loki;
    private Reppu reppu;
    private Kartta kartta;
    private Pelaaja pelaaja;
    private HirvioTaulukko hirviot;
    private EsineTaulukot esineTaulukko;
    private PaaNakyma paanakyma;
    private Lataaja lataaja;
    private Hahmo kohde;
    private int syvyys;
    private String versio;

    /**
     * Peli-luokan konstruktorissa määritellään pelaajan nimi ja ensimmäisen
     * kartan ominaisuudet.
     *
     * @param pelaajanNimi Pelaajan nimi
     * @param kartanKoko Ensimmäisen kartan koko kokonaislukuna (esim. 50)
     * @param kartanMonimutkaisuus Huoneiden määrä
     * @param vihujenMaara Kuinka monta vihollista kartalle luodaan
     * @param paanakyma Koukku UI:ta varten
     *
     */
    public Peli(String pelaajanNimi, int kartanKoko, int kartanMonimutkaisuus, int vihujenMaara, PaaNakyma paanakyma) {
        this.syvyys = 0;
        this.lataaja = new Lataaja();
        this.loki = new Loki();
        this.esineTaulukko = new EsineTaulukot();
        this.kohde = null;
        this.hirviot = new HirvioTaulukko();
        this.kartta = new Kartta(kartanKoko, kartanMonimutkaisuus, 0, this);
        this.pelaaja = new Pelaaja(pelaajanNimi, "pelaaja.png", esineTaulukko.getAse("Nyrkki"));
        this.reppu = new Reppu(this);
        this.paanakyma = paanakyma;
        this.kartta.lisaaHahmo(this.pelaaja, kartanKoko / 2, kartanKoko / 2);
        vihollista(vihujenMaara, 1);
        this.versio = "1.0";
    }

    /**
     * Metodi hyökkäysten käsittelyyn
     *
     * @param hyokkays Hyökkäys jota käytetään
     * @see Hyokkays
     */
    public synchronized void hyokkaa(Hyokkays hyokkays) {
        hyokkays.teeHyokkays();
    }

    /**
     * Lisää kartalle satunnaisiin paikkoihin satunnaisia vihollisia
     *
     * @param vihujenMaara Montako vihollista arvotaan
     * @param vihujenTaso Kuinka kovia vihollisia arvotaan
     *
     */
    private void vihollista(int vihujenMaara, int vihujenTaso) {

        int x = 0;
        int y = 0;

        for (int i = 0; i < vihujenMaara; i++) {

            do {
                x = Tyokalupakki.heitaNoppaa(1, this.kartta.getKartanKoko() - 2);
                y = Tyokalupakki.heitaNoppaa(1, this.kartta.getKartanKoko() - 2);
            } while (!this.kartta.onkoKuljettava(x, y));
            this.kartta.lisaaHahmo(hirviot.getRandomHirvio(vihujenTaso), x, y);
        }
    }

    /**
     * Kutsuttaessa luo uuden (maanalaisen) tason, uusine vihollisineen. Mitä
     * korkeammalla tasolla pelaaja on, sitä kovempia vihollisia uuteen karttaan
     * arvotaan.
     *
     */
    public void luoUusiTaso() {
        syvyys++;
        this.kartta = new Kartta(150, 100, 1, this);
        int x = 0;
        int y = 0;
        do {
            x = Tyokalupakki.heitaNoppaa(1, this.kartta.getKartanKoko() - 2);
            y = Tyokalupakki.heitaNoppaa(1, this.kartta.getKartanKoko() - 2);
        } while (!this.kartta.onkoKuljettava(x, y));
        this.kartta.lisaaHahmo(this.pelaaja, x, y);

        if (syvyys > 3) {
            vihollista(50, 4);
            return;
        }

        if (pelaaja.getLvl() <= 3) {
            vihollista(20, 1);
            vihollista(10, 2);
        } else if (pelaaja.getLvl() > 3 && pelaaja.getLvl() < 6) {
            vihollista(15, 1);
            vihollista(15, 2);
            vihollista(5, 3);
        } else if (pelaaja.getLvl() >= 6 && pelaaja.getLvl() < 10) {
            vihollista(5, 1);
            vihollista(20, 2);
            vihollista(15, 3);
        }
    }

    /**
     * Edistää pelilogiikka yhdellä kierroksella, suorittaa tekoälytoiminnot.
     *
     */
    public synchronized void pelaaKierros() {

        int[] pelaajanSijainti = this.kartta.getSijainti(this.pelaaja);

        for (Hahmo h : this.kartta.getHahmot().keySet()) {

            if (h.getTyyppi().equals("hirviö") && h.onkoElossa()) {
                int[] hirvioSijainti = this.kartta.getSijainti(h);
                if (Tyokalupakki.haeEtaisyys(pelaajanSijainti, hirvioSijainti) == 1) {
                    this.hyokkaa(new HirvioHyokkays(h, this.pelaaja, this));
                } else if (Tyokalupakki.haeEtaisyys(pelaajanSijainti, hirvioSijainti) < 5) {
                    liikuKohti(h, hirvioSijainti);
                }
            }
        }
        
        int puhti = pelaaja.getMaksimiPuhti() / 10;
        if (puhti < 1) {
            pelaaja.lisaaPuhtia(1);
        } else {
            pelaaja.lisaaPuhtia(puhti);
        }
        
        if (pelaaja.getNytHP() < 1) {
            paanakyma.kutsuPelinLopetusIkkunaa();
        }
    }

    /**
     * Tekoälyn apurutiini, tarkistaa pelaajan sijainnin ja pyrkii liikuttamaan
     * hirviötä pelaajaa kohti
     *
     * @param h Hirviö jota liikutetaan
     * @param hirvioSijainti liikutettavan hirviön sijainti
     *
     */
    private synchronized void liikuKohti(Hahmo h, int[] hirvioSijainti) {

        int[] pelaajanSijainti = this.kartta.getSijainti(this.pelaaja);

        // pelaaja oikealla ylhäällä
        if (pelaajanSijainti[0] > hirvioSijainti[0] && pelaajanSijainti[1] < hirvioSijainti[1]) {
            this.kartta.liikuta(h, 1);

            // pelaaja oikealla alhaalla
        } else if (pelaajanSijainti[0] > hirvioSijainti[0] && pelaajanSijainti[1] > hirvioSijainti[1]) {
            this.kartta.liikuta(h, 3);

            // pelaaja vasemmalla alhaalla
        } else if (pelaajanSijainti[0] < hirvioSijainti[0] && pelaajanSijainti[1] > hirvioSijainti[1]) {
            this.kartta.liikuta(h, 5);
            // pelaaja vasemmalla ylhäällä
        } else if (pelaajanSijainti[0] < hirvioSijainti[0] && pelaajanSijainti[1] < hirvioSijainti[1]) {
            this.kartta.liikuta(h, 7);

        } else if (pelaajanSijainti[0] > hirvioSijainti[0]) {
            // pelaaja on oikealla hirviöstä nähden
            this.kartta.liikuta(h, 2);
            // pelaaja on vasemmalla hirviöstä nähden
        } else if (pelaajanSijainti[0] < hirvioSijainti[0]) {
            this.kartta.liikuta(h, 6);
            // pelaaja on hirviön yläpuolella
        } else if (pelaajanSijainti[1] < hirvioSijainti[1]) {
            this.kartta.liikuta(h, 0);
            // pelaaja on hirviön alapuolella
        } else if (pelaajanSijainti[1] > hirvioSijainti[1]) {
            this.kartta.liikuta(h, 4);
            // pelaaja oikealla ylhäällä
        }
    }

    /**
     * Tarkistaa onko kahden kohteen välillä blokkaavia ruutuja. kts.
     * Bresenham's Line Algorithm
     *
     * @param koord1 Ensimmäisen pisteen koordinaatit
     * @param koord2 Toisen pisteen koordinaatit
     * @return Kaikkien heittojen lopputulos
     */
    public synchronized boolean onkoNakoyhteys(int[] koord1, int[] koord2) {

        int x = koord1[0];
        int y = koord1[1];
        int x2 = koord2[0];
        int y2 = koord2[1];

        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) {
            dx1 = -1;
        } else if (w > 0) {
            dx1 = 1;
        }
        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }
        if (w < 0) {
            dx2 = -1;
        } else if (w > 0) {
            dx2 = 1;
        }
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {

            if (!this.kartta.onkoKuljettava(x, y)) {
                return false;
            }
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
        return true;
    }

    /**
     * Kutsuu päänäkymässä lootti-ikkunaa
     *
     * @see PaaNakyma
     * @param h Hirviö jota liikutetaan
     *
     */
    public void kutsuLoottiIkkuna(Hahmo h) {
        this.paanakyma.avaaLoottiIkkuna(h);
    }

    /**
     * Kutsuu päänäkymässä tasonnousu-ikkunaa
     *
     * @see PaaNakyma
     */
    public void kutsuTasoIkkunaa() {
        this.paanakyma.avaaTasonNousuIkkuna();
    }

    /**
     * Kutsuu päänäkymässä tasonvaihto-ikkunaa
     *
     * @see PaaNakyma
     *
     */
    public void kutsuTasonVaihtoIkkunaa() {
        this.paanakyma.kutsuTasonVaihtoIkkunaa();
    }

    /**
     * Asettaa kohteeksi valitun hahmon
     *
     * @param kohde Hahmo joka halutaan asettaa kohteeksi
     */
    public synchronized void setKohde(Hahmo kohde) {
        this.kohde = kohde;
    }

    // Iso liuta yleisiä, yksiselitteisiä gettereitä
    public synchronized Hahmo getKohde() {
        return kohde;
    }

    public synchronized Kartta getKartta() {
        return kartta;
    }

    public synchronized Reppu getReppu() {
        return reppu;
    }

    public synchronized Loki getLoki() {
        return loki;
    }

    public synchronized Pelaaja getPelaaja() {
        return pelaaja;
    }

    public synchronized EsineTaulukot getEsineTaulukko() {
        return esineTaulukko;
    }

    public Lataaja getLataaja() {
        return this.lataaja;
    }

    public int getSyvyys() {
        return syvyys;
    }
    
    public String getVersio() {
        return versio;
    }
}
