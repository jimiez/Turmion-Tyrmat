package logiikka.hahmot;

import logiikka.esineet.Ase;

/**
 * Luokka joka pitää kirja pelaajan erinäisistä ominaisuuksista
 *
 */
public class Pelaaja extends Hahmo {

    private int voima;
    private int ketteryys;
    private int kesto;
    private int xp;
    private int lvl;
    private int maksimiPuhti;
    private int puhtiNyt;

    /**
     * Konstruktori asettaa pelaajan ominaisuudet alkutiloihinsa
     *
     * @param nimi Yläluokan konstruktorin vaatima parametri
     */
    public Pelaaja(String nimi, String kuva, Ase ase) {
        super(nimi, kuva, ase);
        this.voima = this.ketteryys = this.kesto = 10;
        this.maksimiPuhti = this.puhtiNyt = 10 + getMod(kesto);
        this.xp = 0;
        this.lvl = 1;
        super.asetaHiparit(10 + getMod(kesto));

    }

    /**
     * Laskee pelaajan ominaisuuksien suomia bonuksia. Kts. Dungeons & Dragons
     * Ability Modifiers
     *
     * @param kykyPiste Ominaisuus jonka bonus halutaan laskea
     * @return Palauttaa kykybonuksen väliltä -5 +5
     */
    public final int getMod(int kykyPiste) {

        if (kykyPiste < 1) {
            return 0;
        } else {
            return (kykyPiste / 2) - 5;
        }
    }

    /**
     * Laskee kuinka paljon esineitä pelaaja voi yhteensä kantaa
     *
     * @return Palauttaa maksimikantokyvyn ukulele-yksiköissä
     */
    public double getMaksimipaino() {
        return this.voima * 10;
    }

    /**
     * Laskee pelaajan perus-panssariluokan ketteryyden perusteella jos
     * panssaria ei ole päällä Muussa tapauksessa palauttaa panssarin kanssa.
     *
     *
     * @return Palauttaa pelaajan panssariluokan
     */
    @Override
    public synchronized int getAC() {
        if (super.getPanssari() == null) {
            return 10 + getMod(this.ketteryys);
        } else {
            return 10 + getMod(this.ketteryys) + super.getPanssari().getPanssariArvo();
        }
    }

    /**
     * Laskee pelaajan hyökkäysbonuksen voimakkuuden ja tason perusteella
     *
     * @return Palauttaa pelaajan perushyökkäysbonuksen.
     */
    @Override
    public synchronized int getBAB() {
        return this.lvl + getMod(this.voima) + super.getAse().getPlussat();
    }

    /**
     * Vähtentää puhtia halutun määrän, olettaen että puhti ei mene alle nollan
     * Ei hyväksy negatiivisia lukuja
     *
     * @param muutos Vähennettävän puhdin määrä positiivisena kokonaislukuna
     * @return true jos vähennys onnistui
     */
    @Override
    public synchronized boolean vahennaPuhtia(int muutos) {

        if (muutos < 0) {
            return false;
        } else if (this.puhtiNyt - muutos >= 0) {
            this.puhtiNyt -= muutos;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lisää puhtia halutun määrän. Jos puhti menisi yli, täyttää vain
     * puhtipalkin Ei hyväksy negatiivisia lukuja
     *
     * @param muutos Lisättävän puhdin määrä positiivisena kokonaislukuna
     * @return true jos lisäys onnistui
     */
    @Override
    public synchronized boolean lisaaPuhtia(int muutos) {
        if (muutos < 0) {
            return false;
        } else if (this.puhtiNyt + muutos > this.maksimiPuhti) {
            this.puhtiNyt = this.maksimiPuhti;
            return true;
        } else {
            this.puhtiNyt += muutos;
            return true;
        }
    }

    @Override
    public synchronized String getTyyppi() {
        return "pelaaja";
    }

    /**
     * Lisää halutun määrän kokemuspisteitä pelaajalle, ei hyväksy negatiivisia
     * lukuja. Jos pelaaja ylittää tietyn kokemuspisterajan, hän nousee tasoissa
     *
     * @param expaMuutos Lisättävän expan määrä positiivisena kokonaislukuna
     * @return palauttaa true jos pelaaja saa levelin
     */
    public synchronized boolean lisaaExpaa(int expaMuutos) {
        if (expaMuutos > 0) {
            this.xp += expaMuutos;
        }

        if (this.xp >= 500 && this.lvl == 1) {
            return true;
        } else if (this.xp >= 1000 && this.lvl == 2) {
            return true;
        } else if (this.xp >= 2000 && this.lvl == 3) {
            return true;
        } else if (this.xp >= 4000 && this.lvl == 4) {
            return true;
        } else if (this.xp >= 6000 && this.lvl == 5) {
            return true;
        } else if (this.xp >= 10000 && this.lvl == 6) {
            return true;
        } else if (this.xp >= 15000 && this.lvl == 7) {
            return true;
        } else if (this.xp >= 25000 && this.lvl == 8) {
            return true;
        } else if (this.xp >= 40000 && this.lvl == 9) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hirviöt eivät voi saaja pelaajasta ekspaa.
     *
     * @return palauttaa aina 0
     */
    @Override
    public synchronized int getXpArvo() {
        return 0;
    }

    /**
     * Asettaa pelaajan ominaisuudet
     * @param kesto Asetettava kesto kokonaislukuna
     * @param voima Asetettava voima kokonaislukuna
     * @param ketteryys  Asetettava ketteryys kokonaislukuna
     */
    public void asetaOminaisuudet(int voima, int kesto, int ketteryys) {
        this.voima = voima;
        this.kesto = kesto;
        this.ketteryys = ketteryys;

    }

    /**
     * Puhdin asettamista varten.
     *
     * @param puhti Kyseisen hahmon maksimihiparit
     *
     */
    public void asetaPuhti(int puhti) {
        this.maksimiPuhti = this.puhtiNyt = puhti;
    }

    /**
     * Kertoo mikä ase pelaajalla on käytössä. Jos jonkin virheen takia
     * pelaajalla ei ole asetta kädessä (ei pitäisi tapahtua), palauttaa nyrkin
     *
     * @return Pelaajan käytössä oleva ase
     */
    @Override
    public synchronized Ase getAse() {
        if (super.getAse() == null) {
            return new Ase("Nyrkki", 0, 0, 1, 2, 0, "");
        } else {
            return super.getAse();
        }
    }

    /**
     * Kasvattaa pelaajan tasoa yhdellä
     *
     */
    public void kasvataTasoa() {
        this.lvl += 1;
    }

    public int getLvl() {
        return lvl;
    }

    public int getVoima() {
        return voima;
    }

    public int getKesto() {
        return kesto;
    }

    public int getKetteryys() {
        return ketteryys;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public int getMaksimiPuhti() {
        return this.maksimiPuhti;
    }

    @Override
    public int getPuhtiNyt() {
        return this.puhtiNyt;
    }
}