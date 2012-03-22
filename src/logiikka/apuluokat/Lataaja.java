package logiikka.apuluokat;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import logiikka.maailma.Ruutu;

/**
 * Luokka joka lataa asioita pelin alussa muistiin, jotta niitä ei tarvitse
 * lukea levyltä suoritusaikana
 */
public class Lataaja {

    private HashMap<String, BufferedImage> kuvat;
    private ArrayList<String> kuvienNimet;
    private ArrayList<Ruutu> ruudut;

    /**
     * Konstruktorissa luetaan asioita muistiin
     */
    public Lataaja() {
        this.ruudut = new ArrayList<Ruutu>();
        kuvienNimet = new ArrayList<String>();
        kuvat = new HashMap<String, BufferedImage>();
        lisaaRuudut();
        lisaaKuvat();
        lataaKuvat();
    }

    /**
     * Lisää taulukkoon peligrafiikkatiedostojen nimiä myöhempää lataamista
     * varten. Tämä on pakko tehdä näin typerästi koska javassa ei ole mitään
     * luotettavaa tapaa lukea kaikkia jonkun kansion sisällä olevia tiedostoja
     * .jarin sisältä
     */
    private void lisaaKuvat() {
        kuvienNimet.add("gobbyliini.png");
        kuvienNimet.add("kuusi.png");
        kuvienNimet.add("liskomies.png");
        kuvienNimet.add("ninja.png");
        kuvienNimet.add("pelaaja.png");
        kuvienNimet.add("kuusi.png");
        kuvienNimet.add("roiske1.png");
        kuvienNimet.add("roiske2.png");
        kuvienNimet.add("roiske3.png");
        kuvienNimet.add("rosmo.png");
        kuvienNimet.add("ruoho.png");
        kuvienNimet.add("kiviseina.png");
        kuvienNimet.add("kivilattia.png");
        kuvienNimet.add("portaat.png");
        kuvienNimet.add("lurkki.png");
        kuvienNimet.add("rotta.png");
        kuvienNimet.add("loppupomo.png");
        kuvienNimet.add("kyyla.png");
        kuvienNimet.add("kiukkumoykky.png");
        kuvienNimet.add("karmes.png");
        kuvienNimet.add("velho.png");
        kuvienNimet.add("kivihirvio.png");
        kuvienNimet.add("amppari.png");
    }

    /**
     * Lukee kuvatiedostot levyltä, tallettaa taulukkoon viitteinä kuvaolioihin
     */
    private void lataaKuvat() {

        for (String tiedosto : kuvienNimet) {
            try {
                URL url = getClass().getResource("/resurssit/kuvat/" + tiedosto);
                BufferedImage img = ImageIO.read(url);
                kuvat.put(tiedosto, img);
            } catch (IOException e) {
            }
        }
    }

    /**
     * Palauttaa viitteen kuva-olioon
     *
     * @param kuva Kuva joka halutaan piirtää
     * @return Palauttaa viitteen BufferedImage - kuvaolioon
     */
    public BufferedImage haeKuva(String kuva) {
        if (kuvat.containsKey(kuva)) {
            return kuvat.get(kuva);
        } else {
            return null;
        }
    }

    /**
     * Lukee pelin eri ruututyypit ja lisää ne taulukkoon myöhempää käyttöä
     * varten
     *
     */
    private void lisaaRuudut() {

        this.ruudut.add(new Ruutu("Kuusi", 'X', false, "kuusi.png"));
        this.ruudut.add(new Ruutu("Ruoho", '.', true, "ruoho.png"));
        this.ruudut.add(new Ruutu("Kiviseina", 'O', false, "kiviseina.png"));
        this.ruudut.add(new Ruutu("Kivilattia", '_', true, "kivilattia.png"));
        this.ruudut.add(new Ruutu("Portaat", 'P', true, "portaat.png"));
    }

    /**
     * Palauttaa kaikki pelin ruututyypit.
     *
     * @return Kaikki pelin ruutuoliot
     */
    public ArrayList<Ruutu> haeRuudut() {
        return this.ruudut;
    }
}