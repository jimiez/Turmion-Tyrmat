package logiikka.maailma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import logiikka.Peli;
import logiikka.apuluokat.ToistaAani;
import logiikka.esineet.Esine;
import logiikka.hahmot.Hahmo;
import logiikka.hyokkaykset.HirvioHyokkays;
import logiikka.hyokkaykset.PerusHyokkays;

/**
 * Luokka joka lataa kartan ja pitää kirjaa sillä liikkuvista hahmoista.
 *
 */
public class Kartta {

    private char[][] kartta;
    private ArrayList<Ruutu> ruudut;
    private Map<Hahmo, int[]> hahmot;
    private Karttageneraattori generaattori;
    private Peli peli;

    /**
     * Konstruktorissa alustetaan hahmo - ja ruutulistaukset, sekä määritellään
     * kartan ominaisuuksia
     *
     * @see Karttageneraattori
     * @param koko Määrittelee kartan maksimikoon
     * @param monimutkaisuus Kuinka paljon huoneita ja käytäviä luodaan. Mitä
     * @param peli Peli-luokan ilmentymä, johon liitettynä tämä kartta on isompi
     * @param tyyppi Kartan tyyppi. 0 = metsä, 1 = maanalainen luku, sen
     * monimutkaisempi kartta. Hyvä luku on noin 3 x koko.
     */
    public Kartta(int koko, int monimutkaisuus, int tyyppi, Peli peli) {
        this.peli = peli;
        this.ruudut = this.peli.getLataaja().haeRuudut();
        this.hahmot = new ConcurrentHashMap<Hahmo, int[]>();
        this.generaattori = new Karttageneraattori(koko, tyyppi);
        this.kartta = generaattori.luoKartta(monimutkaisuus);

    }

    /**
     * Konstruktorin kuormitettu versio, jossa satunnaisesti luodun kartan
     * sijasta käytetään jotain valmista karttaa
     *
     * @param kartta Kartta char - taulukkomuodossa.
     */
    public Kartta(char[][] kartta, Peli peli) {
        this.peli = peli;
        this.ruudut = this.peli.getLataaja().haeRuudut();
        this.hahmot = new ConcurrentHashMap<Hahmo, int[]>();
        this.kartta = kartta;
    }

    /**
     * Käy läpi kaikki ruututyypit ja pelauttaa karttatunnusta vastaavan olion.
     *
     * @param tunnus Char - tyyppinen tunnus, jota vastaava ruutu tahdotaan
     * löytää. Esim. 'X' = kuusi.
     * @return Ruutu, jos se löytyy kartta-luokan kirjastosta, null jos ei
     */
    public synchronized Ruutu tunnusRuuduksi(char tunnus) {

        for (Ruutu r : this.ruudut) {
            if (tunnus == r.getTunnus()) {
                return r;
            }
        }
        return null;
    }

    /**
     * Palauttaa jonkun karttakoordinaatin sisältämän char - merkkisenä
     *
     * @param x Etsittävän ruudun X-koordinaatti
     * @param y Etsittävän ruudun Y-koordinaatti
     * @return Etsittävä ruutu char - merkkisenä
     */
    public char getRuutu(int x, int y) {
        return this.kartta[x][y];
    }

    /**
     * Palauttaa jonkun karttakoordinaatin sisältämän char - merkkisenä
     *
     * @param koordinaatit Etsittävän ruudun koordinaatit
     * @return Etsittävä ruutu char - merkkisenä
     */
    public char getRuutu(int[] koordinaatit) {
        return this.kartta[koordinaatit[0]][koordinaatit[1]];
    }

    /**
     * Tarkistaa onko joissakin koordinaateissa hahmoja
     *
     * @param x Etsittävän hahmon X-koordinaatti
     * @param y Etsittävän hahmon Y-koordinaatti
     * @return Ruudusta löydettyjen hahmojen määrä
     */
    public synchronized int onkoHahmoa(int x, int y) {
        int hahmojenMaara = 0;

        int[] koordinaatit = {x, y};
        for (Hahmo h : this.hahmot.keySet()) {
            if (Arrays.equals(this.hahmot.get(h), koordinaatit)) {
                hahmojenMaara++;
            }
        }
        return hahmojenMaara;
    }

    /**
     * Tarkistaa onko joissakin koordinaateissa hahmo(j)a
     *
     * @param sijainti Sijainti kokonaisluku-taulukkona {x, y}
     * @return Ruudusta löydettyjen hahmojen määrä
     */
    public synchronized int onkoHahmoa(int[] sijainti) {
        int hahmojenMaara = 0;

        int[] koordinaatit = sijainti;
        for (Hahmo h : this.hahmot.keySet()) {
            if (Arrays.equals(this.hahmot.get(h), koordinaatit)) {
                hahmojenMaara++;
            }
        }
        return hahmojenMaara;
    }

    /**
     * Palauttaa tietyissä koordinaateissa sijaitsevan hahmon (jos löytyy)
     *
     * @param x Etsittävän hahmon X-koordinaatti
     * @param y Etsittävän hahmon Y-koordinaatti
     * @return Palauttaa suoraan viitteen hahmo-olioon
     */
    public synchronized Hahmo getHahmo(int x, int y) {

        int[] koordinaatit = {x, y};
        for (Hahmo h : this.hahmot.keySet()) {
            if (Arrays.equals(this.hahmot.get(h), koordinaatit)) {
                return h;
            }
        }
        return null;
    }

    /**
     * Palauttaa tietyissä koordinaateissa sijaitsevan hahmon (jos löytyy)
     *
     * @param koordinaatti Koordinaatit josta hahmo etsitään
     * @return Palauttaa suoraan viitteen hahmo-olioon
     */
    public synchronized Hahmo getHahmo(int[] koordinaatti) {

        int[] koordinaatit = koordinaatti;
        for (Hahmo h : this.hahmot.keySet()) {
            if (Arrays.equals(this.hahmot.get(h), koordinaatit)) {
                return h;
            }
        }
        return null;
    }

    /**
     * Palauttaa kaikki ruudussa olevat hahmot. Jos ruudussa on vain yksi hahmo,
     * käytä metodia getHahmo()
     *
     * @param koordinaatti Koordinaatit josta hahmo etsitään
     * @return Palauttaa viitteen kaikkiin ruudussa oleviin hahmoihin
     */
    public synchronized ArrayList<Hahmo> getKaikkiHahmot(int[] koordinaatti) {

        ArrayList<Hahmo> hahmotRuudussa = new ArrayList<Hahmo>();

        int[] koordinaatit = koordinaatti;
        for (Hahmo h : this.hahmot.keySet()) {
            if (Arrays.equals(this.hahmot.get(h), koordinaatit)) {
                hahmotRuudussa.add(h);
            }
        }
        return hahmotRuudussa;
    }

    /**
     * Metodi jolla liikutetaan kartalla olevia hahmoja. Ensin tarkistetaan,
     * että liikuteltava hahmo löytyy kartalta, sitten liikutetaan jos valitulla
     * suunnlla sijaitseva ruutu on läpäistävissä. Jos ruudussa on ennestäänt
     * toinen hahmo, hyökätään sen kimppuun automaattisesti.
     *
     * Jos liikkuva hahmo on pelaaja, liikkuminen palauttaa puhtia tahdilla
     * 1/vuoro.
     *
     * @see Peli Perushyökkäys
     * @param hahmo Hahmo, jota tahdotaan liikutta
     * @param suunta suunta johon hahmoa liikutetaan; 0 = ylös, 1 = oikealle
     * ylös, 2 = oikealle, 3 = oikealle alas, 4 = alas, 5 = vasemmalle alas, 6 =
     * vasemmalle, 7 = vasemmalle ylös
     * @return Kertoo onnistuiko liikkuminen vai ei
     */
    public synchronized boolean liikuta(Hahmo hahmo, int suunta) {

        if (this.hahmot.containsKey(hahmo)) {

            // sen jälkeen katsotaan ko. hahmo nykyiset koordinaatit
            int[] uusiRuutu = {this.hahmot.get(hahmo)[0], this.hahmot.get(hahmo)[1]};

            if (suunta == 0) { // ylös
                uusiRuutu[1] -= 1;
            } else if (suunta == 1) { // ylös oikealle
                uusiRuutu[0] += 1;
                uusiRuutu[1] -= 1;
            } else if (suunta == 2) { // oikealle
                uusiRuutu[0] += 1;
            } else if (suunta == 3) { // oikealle alas
                uusiRuutu[0] += 1;
                uusiRuutu[1] += 1;
            } else if (suunta == 4) { // alas
                uusiRuutu[1] += 1;
            } else if (suunta == 5) { // vasemmalle alas
                uusiRuutu[0] -= 1;
                uusiRuutu[1] += 1;
            } else if (suunta == 6) { // vasemmalle
                uusiRuutu[0] -= 1;
            } else if (suunta == 7) { // vasemmalle ylös
                uusiRuutu[0] -= 1;
                uusiRuutu[1] -= 1;
            }

            if (hahmo.getTyyppi().equals("pelaaja") && getRuutu(uusiRuutu) == 'P') {
                this.peli.kutsuTasonVaihtoIkkunaa();
                return true;
            }

            // Tarkistetaan onko ruudussa hahmoa
            if (this.onkoHahmoa(uusiRuutu[0], uusiRuutu[1]) > 0) {
                // Tarkistetaan onko ruudussa oleva hahmo hengissä
                if (this.getHahmo(uusiRuutu[0], uusiRuutu[1]).onkoElossa()) {
                    // Tarkistetaan onko liikkuja pelaaja vai hirviö, ja sen perusteella tehdään liikkuminen
                    if (hahmo.getTyyppi().equals("pelaaja")) {
                        this.peli.hyokkaa(new PerusHyokkays(hahmo, getHahmo(uusiRuutu[0], uusiRuutu[1]), this.peli));
                    } else {
                        this.peli.hyokkaa(new HirvioHyokkays(hahmo, getHahmo(uusiRuutu[0], uusiRuutu[1]), this.peli));
                    }
                    return true;
                } else {

                    // Liikutetaan hahmo uuteen ruutuun
                    this.hahmot.put(hahmo, uusiRuutu);
                    // Jos liikkuva hahmo on pelaaja, toistetaan ääni
                    if (hahmo.getTyyppi().equals("pelaaja")) {
                        ToistaAani.ONNISTUNUTLIIKKUMINEN.toista();
                        poimiLoottia(uusiRuutu);
                    }
                    return true;
                }
            } else if (tunnusRuuduksi(getRuutu(uusiRuutu)).isLapaistava()) {
                // liikutetaan hahmoa, jos hahmo on pelaaja 
                this.hahmot.put(hahmo, uusiRuutu);

                if (hahmo.getTyyppi().equals("pelaaja")) {
                    ToistaAani.ONNISTUNUTLIIKKUMINEN.toista();
                }

                return true;
            } else {
                // Jos liikkuminen epäonnistuu ja hahmo on pelaaja, 
                // toistetaan epäonnistuneesta liikkumisesta ilmoittava ääni

                if (hahmo.getTyyppi().equals("pelaaja")) {
                    ToistaAani.EPAONNISTUNUTLIIKKUMINEN.toista();
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Lisää kartalle Hahmo - luokan olion
     *
     * @param hahmo Hahmo, joka tahdotaan lisätä
     * @param x Ruutu jolle hahmo tahdotaan sijoitaa sivuttaissuunnassa
     * @param y Ruutu jolle hahmo tahdotaan sijoitaa pystysuunnassa
     *
     * @return Kertoo onnistuiko lisäys vai ei
     */
    public boolean lisaaHahmo(Hahmo hahmo, int x, int y) {
        int[] koordinaatit = {x, y};

        if (onkoKuljettava(koordinaatit)) {
            this.hahmot.put(hahmo, koordinaatit);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Poistaa kartalta hahmo - olion jos kyseinen olio löytyy kartalta
     *
     * @param hahmo Hahmo, joka tahdotaan poistaa
     * @return Palauttaa false jos hahmoa ei löytynyt, true jos poisto onnistui
     */
    public synchronized boolean poistaHahmo(Hahmo hahmo) {

        if (this.hahmot.containsKey(hahmo)) {
            this.hahmot.remove(hahmo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kertoo onko joku tietty ruutu kuljettavissa
     *
     * @param x Ruudun x - koordinaatti
     * @param y Ruudun y - koordinaatti
     *
     * @return Palauttaa true jos ruutu on kuljettavissa
     */
    public synchronized boolean onkoKuljettava(int x, int y) {

        if (tunnusRuuduksi(getRuutu(x, y)).isLapaistava()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kertoo onko joku tietty ruutu kuljettavissa
     *
     * @param koordinaatit Ruudun koordinaatit kokonaislukutaulukkossa {x, y}
     *
     * @return Palauttaa true jos ruutu on kuljettavissa
     */
    public boolean onkoKuljettava(int[] koordinaatit) {

        if (tunnusRuuduksi(getRuutu(koordinaatit)).isLapaistava()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tulostaa kartan char - merkkimuodossa. Testaustarkoituksiin.
     */
    public String palautaKartta() {
        String tuloskartta = "";
        for (int y = 0; y <= this.kartta.length - 1; y++) {
            for (int x = 0; x <= this.kartta.length - 1; x++) {

                tuloskartta += kartta[x][y];
            }
            tuloskartta += "\n";
        }
        return tuloskartta;
    }

    /**
     * Jos hahmo löytyy, palauttaa ko. hahmon sijainnin
     *
     * @return palauttaa kokonaislukutaulukkona hahmon sijainnin, ensimmäisessä
     * alkiossa x koordinaatti, toisessa y
     */
    public synchronized int[] getSijainti(Hahmo hahmo) {

        if (this.hahmot.containsKey(hahmo)) {
            return this.hahmot.get(hahmo);
        }
        return null;
    }

    /**
     * Palattaa kartan koon
     *
     * @return palauttaa kokonaislukutaulukkona hahmon sijainnin, ensimmäisessä
     */
    public int getKartanKoko() {
        return this.kartta.length;
    }

    /**
     * Palattaa viitteet kaikkiin kartalle lisättyihin hahmoihin.
     * Tekoälyrutiineja varten.
     *
     * @return Kaikki kartalle lisätyt hahmot ja niiden koordinaatit
     */
    public Map<Hahmo, int[]> getHahmot() {
        return this.hahmot;
    }

    /**
     * Palauttaa jossakin tietyssä ruudussa olevat hahmot ja niiden lootit
     *
     * @param koordinaatit Koordinaatit joiden loottisisältöä halutaan
     * tarkastella
     */
    private void poimiLoottia(int[] koordinaatit) {

        ArrayList<Hahmo> loydetytHahmot = getKaikkiHahmot(koordinaatit);
        for (Hahmo h : loydetytHahmot) {
            ArrayList<Esine> loottia = h.getLoot();
            if (loottia.size() > 0) {
                peli.kutsuLoottiIkkuna(h);
            }
        }
    }
}
