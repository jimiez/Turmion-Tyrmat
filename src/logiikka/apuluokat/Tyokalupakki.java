package logiikka.apuluokat;

/**
 * Yleisille aputyökaluille tarkoitettu luokka. Ei voi periä.
 *
 */
public final class Tyokalupakki {


    /**
     * Palauttaa etäisyyden kahden ruudun välillä
     *
     * @param x1 ensimmäisen ruudun rivi
     * @param y1 ensimmäisen ruudun sarake
     * @param x2 toisen ruudun rivi
     * @param y2 toisen ruudun sarake
     * @return Kahden ruudun etäisyys toisistaan ruutuina
     *
     */
    public static int haeEtaisyys(int x1, int y1, int x2, int y2) {
        int etaisyys;
        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            etaisyys = Math.abs(x1 - x2);
        } else {
            etaisyys = Math.abs(y1 - y2);
        }
        return etaisyys;
    }

    /**
     * Palauttaa etäisyyden kahden ruudun välillä
     *
     * @param ekaRuutu Ensimmäisen ruudun sijainti int[] = {x, y};
     * @param tokaRuutu Ensimmäisen ruudun sijainti int[] = {x, y};
     * @return Kahden ruudun etäisyys toisistaan ruutuina
     *
     */
    public synchronized static int haeEtaisyys(int[] ekaRuutu, int[] tokaRuutu) {
        int etaisyys;
        if (Math.abs(ekaRuutu[0] - tokaRuutu[0]) > Math.abs(ekaRuutu[1] - tokaRuutu[1])) {
            etaisyys = Math.abs(ekaRuutu[0] - tokaRuutu[0]);
        } else {
            etaisyys = Math.abs(ekaRuutu[1] - tokaRuutu[1]);
        }
        return etaisyys;
    }

    /**
     * Heittää noppaa, eli arpoo luvun, esim. 2d6 = 2 <-> 12.
     *
     * @param noppienMaara Montako kertaa noppaa heitetään, yleensä 1 tai 2.
     * @param silmaLuku Nopan maksimisilmäluku. Esim. 6, 12 tai 20.
     * @return Kaikkien heittojen lopputulos
     */
    public static int heitaNoppaa(int noppienMaara, int silmaLuku) {

        int lopputulos = 0;

        for (int i = 0; i < noppienMaara; i++) {
            int noppa = (int) (silmaLuku * Math.random() + 1);
            lopputulos += noppa;
        }
        return lopputulos;
    }

    /**
     * Heittää noppaa, eli arpoo luvun, esim. 2d6 = 2 <-> 12.
     *
     * @param nopat Noppien määrä ja silmäluku int - taulukossa. Esim. 1d6 =
     * int[] = {1,6}
     * @return Kaikkien heittojen lopputulos
     */
    public static int heitaNoppaa(int[] nopat) {

        int lopputulos = 0;

        for (int i = 0; i < nopat[0]; i++) {
            int noppa = (int) (nopat[1] * Math.random() + 1);
            lopputulos += noppa;
        }
        return lopputulos;
    }
}
