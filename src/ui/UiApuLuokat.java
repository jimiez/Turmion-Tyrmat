package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import logiikka.Peli;
import logiikka.hahmot.Hahmo;
import logiikka.maailma.Ruutu;

/**
 * Käyttöliittymän apuluokkia jotka avustavat erinäisissä toiminnoissa
 *
 */
public class UiApuLuokat {
}

/**
 * Luokka kartan piirtämistä varten
 *
 */
class piirtaja extends JPanel {

    private BufferedImage img1;
    private BufferedImage img2;
    private BufferedImage img3;
    private Peli peli;
    private Hahmo hahmo;

    /**
     * Konstruktorin yksinkertaisin versio, jossa piirretään pelkkä tausta.
     *
     * @param tausta Ruutu joka halutaan piirtää
     * @param peli Peli johon piirtäjä liittyy
     */
    public piirtaja(Peli peli, Ruutu tausta) {
        this.peli = peli;
        img1 = peli.getLataaja().haeKuva(tausta.getKuva());
    }

    /**
     * Konstruktori jossa piirretään tausta ja sen päälle hahmo
     *
     * @param tausta Ruutu joka halutaan piirtää
     * @param hahmo Hahmo joka halutaan piirtää
     * @param peli Peli johon piirtäjä liittyy
     */
    public piirtaja(Peli peli, Ruutu tausta, Hahmo hahmo) {
        this.peli = peli;
        this.hahmo = hahmo;
        img1 = peli.getLataaja().haeKuva(tausta.getKuva());
        img2 = peli.getLataaja().haeKuva(hahmo.getKuva());
    }

    /**
     * Konstruktorin viimienen versio, jossa piirretään tausta, hahmo ja
     * veriefekti
     *
     * @param tausta Ruutu joka halutaan piirtää
     * @param hahmo Hahmo joka halutaan piirtää
     * @param efekti Efekti joka halutaan piirtää
     * @param peli Peli johon piirtäjä liittyy
     *
     */
    public piirtaja(Peli peli, Ruutu tausta, Hahmo hahmo, String efekti) {
        this.peli = peli;
        this.hahmo = hahmo;
        img1 = peli.getLataaja().haeKuva(tausta.getKuva());
        img2 = peli.getLataaja().haeKuva(hahmo.getKuva());
        img3 = peli.getLataaja().haeKuva(efekti);
    }

    /**
     * Varsinainen piirtäjä joka piirtää konstruktor(e)issa määritellyt asiat.
     * Jos piirretään hahmo ja hahmo valittu kohde, piirretään keltainen neliö
     * ruudun reunoille
     *
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img1, 0, 0, this);
        g.drawImage(img2, 0, 0, this);
        g.drawImage(img3, 0, 0, this);

        if (this.hahmo == this.peli.getKohde() && this.peli.getKohde() != null) {
            g.setColor(Color.YELLOW);
            g.drawRect(0, 0, 49, 49);
        }
    }
}

/**
 * Luokka minikartan piirtämistä varten
 *
 */
class MiniKartta extends JPanel {

    Peli peli;

    /**
     * Konstruktorissa määritellään vain minkä pelin minikartta piirretään
     *
     */
    public MiniKartta(Peli peli) {

        this.peli = peli;

    }

    /**
     * Metodi jossa määritellään millä värillä minikartta piirretään. Väri
     * määräytyy sen perusteella onko ruudut läpäistäviä vai ei. Värit vaihtuvat
     * sen perusteella ollaanko maan alla vai maan päällä
     *
     * @param lapaistava Onko piirrettävä ruutu läpäistävä
     * @return Viite väri-olioon
     *
     */
    private Color vari(Boolean lapaistava) {
        if (lapaistava) {
            if (this.peli.getSyvyys() == 0) {
                return new Color(0, 150, 0);
            } else {
                return new Color(50, 50, 250);

            }
        } else {
            if (this.peli.getSyvyys() == 0) {
                return new Color(100, 100, 0);
            } else {
                return new Color(50, 50, 50);
            }
        }
    }

    /**
     * Piirtää varsinaisen minikartan
     *
     */
    @Override
    public synchronized void paintComponent(Graphics g) {

        int sijX = this.peli.getKartta().getSijainti(this.peli.getPelaaja())[0] - 10;
        int sijY = this.peli.getKartta().getSijainti(this.peli.getPelaaja())[1] - 10;

        for (int y = 0; y <= 20; y++) {
            for (int x = 0; x <= 20; x++) {

                if (x == 10 && y == 10) {
                    g.setColor(new Color(255, 0, 0));
                } else if (x + sijX < 0 || x + sijX > this.peli.getKartta().getKartanKoko() - 2 || y + sijY < 0 || y + sijY > this.peli.getKartta().getKartanKoko() - 2) {
                    g.setColor(vari(false));
                } else {
                    g.setColor(vari(this.peli.getKartta().onkoKuljettava(x + sijX, y + sijY)));
                }
                g.fillRect(x * 5, y * 5, 5, 5);

            }
        }
    }
}

/**
 * Luokka joka liittää piirrettäviin ruutuihin hiirikuuntelijan mm. kohteen
 * valitsemista varten
 *
 * @see piirtaja
 */
class RuudunKuuntelija implements MouseListener {

    private int x;
    private int y;
    private Peli peli;
    private PaaNakyma paanakyma;

    /**
     * Konstruktorissa määritellään peli ja päänäkymä joita kuunnellaan, lisäksi
     * ruudun koordinaatit johon kuuntelina lisätään
     *
     * @param paanakyma Päänäkymä johon kuuntelija liittyy
     * @param peli Peli johon kuuntelija liittyy
     * @param x Ruudun johon kuuntelija lisätään x koordinaatti
     * @param y Ruudun johon kuuntelija lisätään y koordinaatti
     */
    public RuudunKuuntelija(int x, int y, PaaNakyma paanakyma, Peli peli) {
        this.x = x;
        this.y = y;
        this.paanakyma = paanakyma;
        this.peli = peli;
    }

    /**
     * Kun kuunneltua ruutua klikataan ja ruudusta löytyy elossa oleva hirviö,
     * asetataan se kohteeksi
     * @param e Hiiritapahtuma
     * @see Peli
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (this.peli.getKartta().onkoHahmoa(x, y) > 0) {

            Hahmo kohde = this.peli.getKartta().getHahmo(x, y);
            if (kohde.getTyyppi().equals("hirviö") && kohde.onkoElossa()) {
                this.peli.setKohde(kohde);
                this.paanakyma.paivitaKaikki();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
