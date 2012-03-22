package logiikka.hahmot;

import java.util.ArrayList;
import logiikka.esineet.Ase;
import logiikka.esineet.Esine;
import logiikka.esineet.EsineTaulukot;
import logiikka.esineet.Panssari;

/**
 * Yliluokka kaikille pelin toimijoille
 *
 * @see Pelaaja
 * @see Hirvio
 */
public abstract class Hahmo {

    private String nimi;
    private int maksHP;
    private int nytHP;
    private String kuva;
    private Ase ase;
    private boolean elossa;
    private Panssari panssari;
    private ArrayList<Esine> lootit;

    /**
     * Konstruktori vaatii ainoastaan pelaajan nimen. Uuden pelaajan hiparit
     * ovat täysissä.
     *
     * @param nimi Pelaajahahmon nimi
     */
    public Hahmo(String nimi, String kuva, Ase ase) {
        // TODO hakee listasta hirviön nimen perusteella statsit
        this.ase = ase;
        this.nimi = nimi;
        this.kuva = kuva;
        this.elossa = true;
        this.lootit = new ArrayList<Esine>();
    }

    /**
     * Abstrakti luokka panssarille, eli hahmojen kyvylle välttää osumia
     *
     * @return Pelaajan/hirviön sen hetkinen panssariluokitus
     */
    public abstract int getAC();

    /**
     * Abstrakti luokka hyökkäysbonukselle
     *
     * @return Pelaajan/hirviön sen hetkinen hyökkäysbonus
     */
    public abstract int getBAB();

    /**
     * Puhtia käsittelevät toiminnot ovat abstrakteja, koska pelaajalla on puhti
     * siinä missä hirviöillä ei
     *
     * @return Palauttaa puhdin määrän tällä hetkellä
     */
    public abstract int getPuhtiNyt();

    /**
     * Puhtia käsittelevät toiminnot ovat abstrakteja, koska pelaajalla on puhti
     * siinä missä hirviöillä ei
     *
     * @return Palauttaa puhdin maksimimäärän
     */
    public abstract int getMaksimiPuhti();

    /**
     * Puhtia käsittelevät toiminnot ovat abstrakteja, koska pelaajalla on puhti
     * siinä missä hirviöillä ei
     *
     * @param muutos Vähennettävän puhdin määrä positiivisena kokonaislukuna
     * @return true jos vähennys onnistui
     */
    public abstract boolean vahennaPuhtia(int muutos);

    /**
     * Puhtia käsittelevät toiminnot ovat abstrakteja, koska pelaajalla on puhti
     * siinä missä hirviöillä ei
     *
     * @param muutos Lisättävän puhdin määrä positiivisena kokonaislukuna
     * @return Palauttaa true jos puhdin lisäys onnistui
     */
    public abstract boolean lisaaPuhtia(int muutos);

    /**
     * Palauttaa olennon voittamisesta saatavan kokemuspistemäärän
     */
    public abstract int getXpArvo();

    /**
     * Tarvitaan pelaajien ja hiviöiden erotteluun
     *
     * @return Palauttaa (alaluokan) tyypin; hirviö tai pelaaja
     */
    public abstract String getTyyppi();

    /**
     * Osumapisteiden asettamista varten.
     *
     * @param hiparit Kyseisen hahmon maksimihiparit
     *
     */
    public void asetaHiparit(int hiparit) {
        this.maksHP = this.nytHP = hiparit;
    }

    /**
     * Parantaa menetettyjä osumapisteitä. Ei hyväksy negatiivisia
     * parannusmääriä.
     *
     * @param parannus Palautettavien hiparien määrä positiivisena
     * kokonaislukuna
     * @return Maksimihipareiden ylitse menevän parannuksen osuus
     */
    public int paranna(int parannus) {

        if (parannus < 0) {
            return 0;
        } else if (this.nytHP + parannus > this.maksHP) {
            int yliJaama = (this.nytHP + parannus) - this.maksHP;
            this.nytHP = this.maksHP;
            return yliJaama;
        } else {
            this.nytHP += parannus;
            return 0;
        }
    }

    /**
     * Vähentää osumapisteitä. Ei hyväksy negatiivisia vahinkomääriä.
     *
     * @param vahinko Otettavan vahingon määrä positiivisena kokonaislukuna
     * @return Palauttaa true jos vahingon ottaja kuolee saamastaan vahingosta
     */
    public boolean otaVahinkoa(int vahinko) {

        if (vahinko < 0) {
            return false;
        } else if (vahinko > this.nytHP) {
            this.nytHP -= vahinko;
            return true;
        } else {
            this.nytHP -= vahinko;
            return false;
        }
    }

    /**
     * Jokaisella hahmolla on oltava viittaus johonkin .png kuvaan jota
     * käytetään kun ne piirretään kartalle
     *
     * @return Hahmoa vastaava .png kuva esim. "pelaaja.png"
     */
    public String getKuva() {
        return this.kuva;
    }

    @Override
    public String toString() {
        return this.nimi;
    }

    /**
     * Mitä asetta hahmo käyttää. Pelajaa voi vaihtaa asettaan, hirviöille
     * konstruktoidaan geneerinen hirviöase
     *
     * @return Palauttaa hahmon käyttämän aseen
     */
    public synchronized Ase getAse() {
        return this.ase;
    }

    /**
     * Asettaa hahmolle aseen
     *
     * @param ase Ase joka halutaan asettaa hahmolle
     */
    public void setAse(Ase ase) {
        this.ase = ase;
    }

    /**
     * Hahmoilla on olemassa muuttuja, joka kertoo ovatko ne pelin kannalta
     * aktiivisia toimijoita (elossa) vai ei (kuolleita)
     *
     * @return True jos hahmo on elossa
     */
    public synchronized boolean onkoElossa() {
        return elossa;
    }

    /**
     * Palauttaa hahmon panssarin, jos sellainen on asetettu
     *
     * @return True jos hahmo on elossa
     */
    public synchronized Panssari getPanssari() {
        if (this.panssari == null) {
            return new Panssari("Ei mitään", 0, 0, "", 0);
        } else {
            return this.panssari;
        }
    }

    /**
     * Asettaa hahmolle panssarin
     *
     * @param panssari Panssari joka halutaan asettaa hahmolle
     */
    public void setPanssari(Panssari panssari) {
        this.panssari = panssari;
    }

    /**
     * Nirhaa hahmon, eli lopettaa hirviöiden tapauksessa AI-rutiinien ajamisen
     * ja kertoo myös, että hahmo on lootattavissa
     *
     */
    public void tapaHahmo() {
        this.elossa = false;
    }

    /**
     * Asettaa hahmolla listan esineistä (jos arpa on suosinut)
     *
     * @param lootit Hahmolle asetettavat lootit ArrayList - muodossa, yleensä
     * tulevat EsineTaulukko luokan generaattoreista
     * @see EsineTaulukot
     */
    public void asetaLoot(ArrayList<Esine> lootit) {
        this.lootit = lootit;
    }

    /**
     * Palauttaa hahmolle arvotun lootin
     *
     */
    public ArrayList<Esine> getLoot() {
        return this.lootit;
    }

    public int getLootinMaara() {
        return this.lootit.size();
    }

    public int getMaksHP() {
        return maksHP;
    }

    public int getNytHP() {
        return nytHP;
    }

    public String getNimi() {
        return this.nimi;
    }
}
