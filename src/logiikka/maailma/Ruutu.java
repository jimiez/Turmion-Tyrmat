package logiikka.maailma;

/**
 * Pelin kartta muodostuu ruuduista. Tässä luokassa määritellään jokaisen
 * pelissä esiintyvän ruudun ominaisuudet
 *
 * @see Kartta
 */
public class Ruutu {

    private String nimi;
    private char tunnus;
    private boolean lapaistava;
    private String kuva;

    /**
     * Kaikki yksittäisen ruututyypin ominaisuudet määritellään konstruktorissa
     *
     * @param nimi Ruudun nimi. Esim. "Kuusi".
     * @param kuva Ruudun graafinen esitys. Esim. kuusi.png
     * @param lapaistava Kertoo voivatko hahmot liikkua ko. ruudun lävitse
     * @param tunnus Merkki jolla ko. ruutu on esitetty kartan ACII - versiossa.
     * Esim. 'X'.
     */
    public Ruutu(String nimi, char tunnus, boolean lapaistava, String kuva) {
        this.nimi = nimi;
        this.tunnus = tunnus;
        this.lapaistava = lapaistava;
        this.kuva = kuva;
    }

    public String getNimi() {
        return nimi;
    }

    public char getTunnus() {
        return tunnus;
    }

    public String getKuva() {
        return kuva;
    }

    public boolean isLapaistava() {
        return lapaistava;
    }

    @Override
    public String toString() {
        return this.nimi;
    }
}
