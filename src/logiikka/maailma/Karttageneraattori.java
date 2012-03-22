package logiikka.maailma;

import java.util.Random;
import logiikka.apuluokat.Tyokalupakki;

/**
 * Luokka joka luo satunnaisista käytävistä ja huoneista koostuvan kartan
 *
 */
public class Karttageneraattori {

    private int osX;
    private int osY;
    private char[][] kartta;
    private Random arpoja;
    private char lattia;
    private char seina;

    /**
     * Konstruktorissa alustetaan tarpeettoman suuri kartta ja asetetaan osoitin
     * osoittamaan kartan keskelle
     *
     * @param koko Määrittelee kuinka kauan kartan generointia jatketaan. Mitä
     * @param tyyppi 0 = metsäkartta, 1 = maanalainen kartta suurempi luku, sen
     * suurempi kartta
     *
     */
    public Karttageneraattori(int koko, int tyyppi) {

        if (tyyppi == 0) {
            this.lattia = '.';
            this.seina = 'X';
        } else {
            this.lattia = '_';
            this.seina = 'O';
        }

        this.kartta = new char[koko][koko];
        this.osX = this.osY = (this.kartta.length / 2);
        this.alustaKartta();
        this.arpoja = new Random();

    }

    /**
     * Täyttää koko kartan läpäisemättömillä ruuduilla
     */
    private void alustaKartta() {

        for (int i = 0; i < this.kartta.length - 1; i++) {
            for (int j = 0; j < this.kartta.length - 1; j++) {
                this.kartta[i][j] = this.seina;
            }
        }
    }

    /**
     * Metodi joka aloittaa kartan luomisen
     *
     * @param huoneidenMaara Montako huone-käytävä yhdistelmää tehdään. Mitä
     * korkeampi sen monimutkaisempia karttoja keskimäärin luodaan.
     *
     */
    public char[][] luoKartta(int huoneidenMaara) {

        for (int i = 0; i < huoneidenMaara; i++) {
            teeHuone();
            teeKaytava();
        }

        teePortaat();

        return this.kartta;
    }

    /**
     * Metodi joka tekee 3x3 - 5x5 kokoisen huoneen osoittimen osoittamaan
     * kohtaan
     */
    private void teeHuone() {

        int koko = arpoja.nextInt(4) + 1;

        if (this.osX + koko < this.kartta.length - 1 && this.osX - koko > 1 && this.osY + koko < this.kartta.length - 1 && this.osY - koko > 1) {
            for (int x = (this.osX - koko); x < (this.osX + koko); x++) {
                for (int y = (this.osY - koko); y < (this.osY + koko); y++) {
                    this.kartta[x][y] = this.lattia;
                }
            }
        }
    }

    /**
     * Metodi joka tekee osoittimesta lähtevän käytävän, jolle arvotaan pituus
     * ja suunta
     *
     */
    private void teeKaytava() {
        int suunta = arpoja.nextInt(4);
        int pituus = arpoja.nextInt(7) + 5;

        if (this.osX + pituus < this.kartta.length - 1 && this.osX - pituus > 1 && this.osY + pituus < this.kartta.length - 1 && this.osY - pituus > 1) {
            if (suunta == 0) {
                // oikealle
                for (int x = this.osX; x < (this.osX + pituus); x++) {
                    this.kartta[x][this.osY] = this.lattia;
                }
                this.osX += pituus;
            } else if (suunta == 1) {
                // vasemmalle
                for (int x = this.osX; x > (this.osX - pituus); x--) {
                    this.kartta[x][this.osY] = this.lattia;
                }
                this.osX -= pituus;
            } else if (suunta == 2) {
                // alas
                for (int y = this.osY; y < (this.osY + pituus); y++) {
                    this.kartta[this.osX][y] = this.lattia;
                }
                this.osY += pituus;
            } else if (suunta == 3) {
                // ylös
                for (int y = this.osY; y > (this.osY - pituus); y--) {
                    this.kartta[this.osX][y] = this.lattia;
                }
                this.osY -= pituus;
            }
        }
    }

    /**
     * Metodi joka tekee alaspäin johtavat portaat joista pääsee seuraavalle tasolle
     *
     */
    private void teePortaat() {
        
        int x = 0;
        int y = 0;
         do {
                x = Tyokalupakki.heitaNoppaa(1, this.kartta.length - 2);
                y = Tyokalupakki.heitaNoppaa(1, this.kartta.length - 2);
            } while (this.kartta[x][y] != lattia);
         
         this.kartta[x][y] = 'P';
    }
}
