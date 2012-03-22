package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import logiikka.Peli;
import logiikka.apuluokat.ToistaAani;
import logiikka.apuluokat.Tyokalupakki;
import logiikka.hahmot.Hahmo;
import logiikka.hyokkaykset.Taikaloitsu;
import logiikka.hyokkaykset.VimmaHyokkays;
import logiikka.maailma.Ruutu;

/**
 * Graafisen käyttöliittymän pääluokka
 *
 */
public class PaaNakyma extends JFrame {

    private Peli peli;
    private MiniKartta minikartta;
    private JDialog reppuNakyma;
    private int[] pelaajanSijainti;
    private Lisatoiminnot lisatoiminnot;
    private JDialog lootIkkuna;

    /**
     * Piirtää päänäkymään kartan kuvina
     *
     */
    private synchronized void piirraKartta() {

        int[] sijainti = peli.getKartta().getSijainti(peli.getPelaaja());

        // kuinka monta ruutua näkökenttä on per ilmansuunta
        int alueX = 4;
        int alueY = 4;
        Nakyma.removeAll();

        for (int y = sijainti[1] - alueY; y <= sijainti[1] + alueY; y++) {
            for (int x = sijainti[0] - alueX; x <= sijainti[0] + alueX; x++) {
                if (x < 0 || y < 0 || x > peli.getKartta().getKartanKoko() - 2 || y > peli.getKartta().getKartanKoko() - 2) {
                    // Mitä ruudun ulkopuolella tapahtuu
                    Ruutu ruutu = peli.getKartta().tunnusRuuduksi(peli.getKartta().getRuutu(0, 0));
                    Nakyma.add(new piirtaja(this.peli, ruutu));
                } else if (peli.getKartta().onkoHahmoa(x, y) > 0) {
                    // Onko ruudussa hahmoa?
                    if (peli.getKartta().getHahmo(x, y).onkoElossa()) {
                        Ruutu ruutu = peli.getKartta().tunnusRuuduksi(peli.getKartta().getRuutu(x, y));
                        Hahmo hahmo = peli.getKartta().getHahmo(x, y);
                        Nakyma.add(new piirtaja(this.peli, ruutu, hahmo)).addMouseListener(new RuudunKuuntelija(x, y, this, this.peli));
                    } else {
                        Ruutu ruutu = peli.getKartta().tunnusRuuduksi(peli.getKartta().getRuutu(x, y));
                        String roiske = "roiske" + Tyokalupakki.heitaNoppaa(1, 3) + ".png";
                        Hahmo hahmo = peli.getKartta().getHahmo(x, y);
                        Nakyma.add(new piirtaja(this.peli, ruutu, hahmo, roiske)).addMouseListener(new RuudunKuuntelija(x, y, this, this.peli));
                    }
                } else {
                    Ruutu ruutu = peli.getKartta().tunnusRuuduksi(peli.getKartta().getRuutu(x, y));
                    Nakyma.add(new piirtaja(this.peli, ruutu)).addMouseListener(new RuudunKuuntelija(x, y, this, this.peli));
                }
            }
        }

        Nakyma.revalidate();
    }

    /**
     * Edistää peliä kierroksella, kutsuu Peli - luokkaa
     *
     * @see Peli
     */
    private synchronized void pelaaKierros() {
        this.peli.pelaaKierros();
        paivitaKaikki();
    }

    /**
     * Päivittää kaikki päänäkymän graafiset elementit
     *
     */
    public synchronized void paivitaKaikki() {
        this.pelaajanSijainti = this.peli.getKartta().getSijainti(this.peli.getPelaaja());
        this.piirraKartta();
        minikartta.repaint();
        this.lokiTeksti.setText(this.peli.getLoki().toString());

        puhtiPalkki.setValue(peli.getPelaaja().getPuhtiNyt());
        puhtiPalkki.setMaximum(peli.getPelaaja().getMaksimiPuhti());
        puhtiPalkki.setString("Puhti: " + peli.getPelaaja().getPuhtiNyt() + " / " + peli.getPelaaja().getMaksimiPuhti());

        hipariPalkki.setValue(peli.getPelaaja().getNytHP());
        hipariPalkki.setMaximum(peli.getPelaaja().getMaksHP());
        hipariPalkki.setString("Hiparit: " + peli.getPelaaja().getNytHP() + " / " + peli.getPelaaja().getMaksHP());

        expaTeksti.setText("Kokemuspisteet: " + peli.getPelaaja().getXp());

        voimaTeksti.setText("Voima: " + peli.getPelaaja().getVoima() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getVoima()) + ")");
        ketteryysTeksti.setText("Ketteryys: " + peli.getPelaaja().getKetteryys() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getKetteryys()) + ")");
        kestavyysTeksti.setText("Kestävyys: " + peli.getPelaaja().getKesto() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getKesto()) + ")");

        panssariTeksti.setText("Panssariluokka: " + peli.getPelaaja().getAC());
        babTeksti.setText("Hyökkäysbonus: " + peli.getPelaaja().getBAB());

        if (this.peli.getKohde() == null) {
            kohdeTeksti.setText("Kohde: ");

        } else if (!this.peli.getKohde().onkoElossa()) {
            this.peli.setKohde(null);
        } else {
            kohdeTeksti.setText("Kohde: " + this.peli.getKohde().getNimi());
        }
     
        if (lootIkkuna == null) {
            perusPaneeli.requestFocus();
        }
    }

    public void kutsuPelinLopetusIkkunaa() {
        paivitaKaikki();
        ToistaAani.KUOLEMA.toista();
        lisatoiminnot.kuolemaStatsit();
    }

    /**
     * Tarkistaa onko reppu-ikkuna avattu, jos ei niin avaa uuden
     *
     */
    public void avaaReppu() {
        if (reppuNakyma == null) {
            reppuNakyma = new ReppuNakyma(this.peli, this);
        }
        reppuNakyma.dispose();
        reppuNakyma = new ReppuNakyma(this.peli, this);
        reppuNakyma.setVisible(true);

    }

    /**
     * Avaa lootti-ikkunan, kutsutaan peli-luokasta
     *
     * @param h Hahmo jonka lootit otetaan
     * @see Peli
     */
    public void avaaLoottiIkkuna(Hahmo h) {
        if (lootIkkuna == null) {
            lootIkkuna = new LootIkkuna(this.peli, this, h);
        }

        lootIkkuna.dispose();
        lootIkkuna = new LootIkkuna(this.peli, this, h);
        lootIkkuna.setVisible(true);

    }

    /**
     * Kutsutaan kun pelaaja saavuttaa uuden tason
     *
     */
    public void kutsuTasonVaihtoIkkunaa() {
        this.peli.luoUusiTaso();
        paivitaKaikki();
    }

    /**
     * Tasonnousun yhteydessä avautuva ikkuna
     */
    public void avaaTasonNousuIkkuna() {
        ToistaAani.LEVELUP.toista();
        lisatoiminnot.avaaTasonNousuIkkuna();
        oikeaPaneeli.setBorder(javax.swing.BorderFactory.createTitledBorder(peli.getPelaaja().getNimi() + " - Lvl " + peli.getPelaaja().getLvl()));
    }

    /**
     * Luo uuden pelin
     *
     */
    public void uusiPeli() {
        this.dispose();
        PaaNakyma paaNakyma = new PaaNakyma();
        paaNakyma.setVisible(true);
    }

    /**
     * Päänäkymän konstruktorissa alustetaan monia asioita ja luodaan mm. peli -
     * luokka ja asetataan sen alkuarvot
     *
     */
    public PaaNakyma() {
        String pelaajanNimi = JOptionPane.showInputDialog("Mikä on nimesi, uljas seikkailija?", "Pelaaja");
        if (pelaajanNimi == null) {
            System.exit(0);
        } else if (pelaajanNimi.equals("")) {
            pelaajanNimi = "Pelaaja";
        }
        this.peli = new Peli(pelaajanNimi, 150, 250, 30, this);
        this.pelaajanSijainti = this.peli.getKartta().getSijainti(this.peli.getPelaaja());
        this.lisatoiminnot = new Lisatoiminnot(this, peli);
        lisatoiminnot.heitaPelaajanStatsit();
        minikartta = new MiniKartta(peli);
        ToistaAani.init();
        ToistaAani.volume = ToistaAani.Volume.MEDIUM;
        ToistaAani.ALOITUS.toista();
        initComponents();

        perusPaneeli.requestFocus();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        perusPaneeli = new javax.swing.JPanel();
        Nakyma = new javax.swing.JPanel();
        lokiPaneeli = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lokiTeksti = new javax.swing.JTextArea();
        oikeaPaneeli = new javax.swing.JPanel();
        voimaTeksti = new javax.swing.JLabel();
        ketteryysTeksti = new javax.swing.JLabel();
        kestavyysTeksti = new javax.swing.JLabel();
        panssariTeksti = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        babTeksti = new javax.swing.JLabel();
        parannusNappi = new javax.swing.JButton();
        puhtiPalkki = new javax.swing.JProgressBar();
        hipariPalkki = new javax.swing.JProgressBar();
        liikkumisPaneeli = new javax.swing.JPanel();
        vYnappi = new javax.swing.JButton();
        yNappi = new javax.swing.JButton();
        oYnappi = new javax.swing.JButton();
        odotaNappi = new javax.swing.JButton();
        vNappi = new javax.swing.JButton();
        oNappi = new javax.swing.JButton();
        vAnappi = new javax.swing.JButton();
        aNappi = new javax.swing.JButton();
        oAnappi = new javax.swing.JButton();
        expaTeksti = new javax.swing.JLabel();
        reppuNappi = new javax.swing.JButton();
        vimmaNappi = new javax.swing.JButton();
        kohdeTeksti = new javax.swing.JLabel();
        taikaLoitsu = new javax.swing.JButton();
        miniKarttaPaneeli = new javax.swing.JPanel();
        menuPalkki = new javax.swing.JMenuBar();
        peliMenu = new javax.swing.JMenu();
        peliMenuItem1 = new javax.swing.JMenuItem();
        peliMenuItem2 = new javax.swing.JMenuItem();
        apuMenu = new javax.swing.JMenu();
        menuOhje = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Turmion Tyrmät (versio " + this.peli.getVersio() + ")");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(640, 680));
        setName("paaNakymaRuutu");
        setResizable(false);

        perusPaneeli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        perusPaneeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                perusPaneeliKeyPressed(evt);
            }
        });

        Nakyma.setMaximumSize(new java.awt.Dimension(450, 450));
        Nakyma.setMinimumSize(new java.awt.Dimension(450, 450));
        Nakyma.setPreferredSize(new java.awt.Dimension(450, 450));
        Nakyma.setRequestFocusEnabled(false);
        Nakyma.setLayout(new java.awt.GridLayout(9, 9));

        lokiPaneeli.setBorder(javax.swing.BorderFactory.createTitledBorder("Lokikirja"));

        lokiTeksti.setColumns(20);
        lokiTeksti.setEditable(false);
        lokiTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lokiTeksti.setRows(5);
        lokiTeksti.setText(peli.getLoki().toString());
        jScrollPane1.setViewportView(lokiTeksti);

        javax.swing.GroupLayout lokiPaneeliLayout = new javax.swing.GroupLayout(lokiPaneeli);
        lokiPaneeli.setLayout(lokiPaneeliLayout);
        lokiPaneeliLayout.setHorizontalGroup(
            lokiPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        lokiPaneeliLayout.setVerticalGroup(
            lokiPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokiPaneeliLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        oikeaPaneeli.setBorder(javax.swing.BorderFactory.createTitledBorder(peli.getPelaaja().getNimi() + " - Lvl " + peli.getPelaaja().getLvl()));

        voimaTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        voimaTeksti.setText("Voima: " + peli.getPelaaja().getVoima() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getVoima()) + ")");

        ketteryysTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        ketteryysTeksti.setText("Ketteryys: " + peli.getPelaaja().getKetteryys() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getKetteryys()) + ")");

        kestavyysTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        kestavyysTeksti.setText("Kestävyys: " + peli.getPelaaja().getKesto() + " (" + peli.getPelaaja().getMod(peli.getPelaaja().getKesto()) + ")");

        panssariTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        panssariTeksti.setText("Panssariluokka: " + peli.getPelaaja().getAC());

        babTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        babTeksti.setText("Hyökkäysbonus: " + peli.getPelaaja().getBAB());

        parannusNappi.setText("Parannus (E)");
        parannusNappi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                parannusNappiMouseEntered(evt);
            }
        });
        parannusNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parannusNappiActionPerformed(evt);
            }
        });

        puhtiPalkki.setForeground(java.awt.Color.YELLOW);
        puhtiPalkki.setMaximum(peli.getPelaaja().getMaksimiPuhti());
        puhtiPalkki.setValue(peli.getPelaaja().getPuhtiNyt());

        hipariPalkki.setBackground(new java.awt.Color(255, 255, 102));
        hipariPalkki.setForeground(java.awt.Color.GREEN);
        hipariPalkki.setMaximum(peli.getPelaaja().getMaksHP());
        hipariPalkki.setValue(peli.getPelaaja().getNytHP());

        liikkumisPaneeli.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        vYnappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/vasenYlosNuoli.png"))); // NOI18N
        vYnappi.setMaximumSize(new java.awt.Dimension(30, 30));
        vYnappi.setMinimumSize(new java.awt.Dimension(30, 30));
        vYnappi.setPreferredSize(new java.awt.Dimension(30, 30));
        vYnappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vYnappiActionPerformed(evt);
            }
        });

        yNappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/ylosNuoli.png"))); // NOI18N
        yNappi.setMaximumSize(new java.awt.Dimension(30, 30));
        yNappi.setMinimumSize(new java.awt.Dimension(30, 30));
        yNappi.setPreferredSize(new java.awt.Dimension(30, 30));
        yNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yNappiActionPerformed(evt);
            }
        });

        oYnappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/oikeaYlosNuoli.png"))); // NOI18N
        oYnappi.setMaximumSize(new java.awt.Dimension(30, 30));
        oYnappi.setMinimumSize(new java.awt.Dimension(30, 30));
        oYnappi.setPreferredSize(new java.awt.Dimension(30, 30));
        oYnappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oYnappiActionPerformed(evt);
            }
        });

        odotaNappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/odotaNappi.png"))); // NOI18N
        odotaNappi.setMaximumSize(new java.awt.Dimension(30, 30));
        odotaNappi.setMinimumSize(new java.awt.Dimension(30, 30));
        odotaNappi.setPreferredSize(new java.awt.Dimension(30, 30));
        odotaNappi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                odotaNappiMouseEntered(evt);
            }
        });
        odotaNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odotaNappiActionPerformed(evt);
            }
        });

        vNappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/vasenNuoli.png"))); // NOI18N
        vNappi.setMaximumSize(new java.awt.Dimension(30, 30));
        vNappi.setMinimumSize(new java.awt.Dimension(30, 30));
        vNappi.setPreferredSize(new java.awt.Dimension(30, 30));
        vNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vNappiActionPerformed(evt);
            }
        });

        oNappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/oikeaNuoli.png"))); // NOI18N
        oNappi.setMaximumSize(new java.awt.Dimension(30, 30));
        oNappi.setMinimumSize(new java.awt.Dimension(30, 30));
        oNappi.setPreferredSize(new java.awt.Dimension(30, 30));
        oNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oNappiActionPerformed(evt);
            }
        });

        vAnappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/vasenAlasNuoli.png"))); // NOI18N
        vAnappi.setMaximumSize(new java.awt.Dimension(30, 30));
        vAnappi.setMinimumSize(new java.awt.Dimension(30, 30));
        vAnappi.setPreferredSize(new java.awt.Dimension(30, 30));
        vAnappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vAnappiActionPerformed(evt);
            }
        });

        aNappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/alasNuoli.png"))); // NOI18N
        aNappi.setMaximumSize(new java.awt.Dimension(30, 30));
        aNappi.setMinimumSize(new java.awt.Dimension(30, 30));
        aNappi.setPreferredSize(new java.awt.Dimension(30, 30));
        aNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aNappiActionPerformed(evt);
            }
        });

        oAnappi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resurssit/kuvat/pienet/oikeaAlasNuoli.png"))); // NOI18N
        oAnappi.setMaximumSize(new java.awt.Dimension(30, 30));
        oAnappi.setMinimumSize(new java.awt.Dimension(30, 30));
        oAnappi.setPreferredSize(new java.awt.Dimension(30, 30));
        oAnappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oAnappiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout liikkumisPaneeliLayout = new javax.swing.GroupLayout(liikkumisPaneeli);
        liikkumisPaneeli.setLayout(liikkumisPaneeliLayout);
        liikkumisPaneeliLayout.setHorizontalGroup(
            liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liikkumisPaneeliLayout.createSequentialGroup()
                .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vAnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(vYnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(vNappi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(liikkumisPaneeliLayout.createSequentialGroup()
                        .addComponent(odotaNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(liikkumisPaneeliLayout.createSequentialGroup()
                        .addComponent(yNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oYnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(liikkumisPaneeliLayout.createSequentialGroup()
                        .addComponent(aNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oAnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        liikkumisPaneeliLayout.setVerticalGroup(
            liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liikkumisPaneeliLayout.createSequentialGroup()
                .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vYnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oYnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(odotaNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(liikkumisPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vAnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aNappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oAnappi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        expaTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        expaTeksti.setText("Kokemuspisteet: " + peli.getPelaaja().getXp());

        reppuNappi.setText("Reppu (R)");
        reppuNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reppuNappiActionPerformed(evt);
            }
        });

        vimmaNappi.setText("Vimmahyökkäys (Q)");
        vimmaNappi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vimmaNappiMouseEntered(evt);
            }
        });
        vimmaNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vimmaNappiActionPerformed(evt);
            }
        });

        kohdeTeksti.setText("Kohde: ");

        taikaLoitsu.setText("Taikaloitsu (W)");
        taikaLoitsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taikaLoitsuMouseEntered(evt);
            }
        });
        taikaLoitsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taikaLoitsuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout oikeaPaneeliLayout = new javax.swing.GroupLayout(oikeaPaneeli);
        oikeaPaneeli.setLayout(oikeaPaneeliLayout);
        oikeaPaneeliLayout.setHorizontalGroup(
            oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                        .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(babTeksti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                                .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(voimaTeksti, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ketteryysTeksti, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(kestavyysTeksti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panssariTeksti))
                                    .addComponent(expaTeksti)
                                    .addComponent(liikkumisPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hipariPalkki, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                        .addComponent(puhtiPalkki, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(parannusNappi)
                            .addComponent(reppuNappi)
                            .addComponent(vimmaNappi)
                            .addComponent(kohdeTeksti)
                            .addComponent(taikaLoitsu))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        oikeaPaneeliLayout.setVerticalGroup(
            oikeaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(oikeaPaneeliLayout.createSequentialGroup()
                .addComponent(hipariPalkki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(puhtiPalkki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(voimaTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ketteryysTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kestavyysTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panssariTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(babTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expaTeksti)
                .addGap(18, 18, 18)
                .addComponent(reppuNappi)
                .addGap(40, 40, 40)
                .addComponent(parannusNappi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vimmaNappi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taikaLoitsu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kohdeTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(liikkumisPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        puhtiPalkki.setStringPainted(true);
        puhtiPalkki.setString("Puhti: " + peli.getPelaaja().getPuhtiNyt() + " / " + peli.getPelaaja().getMaksimiPuhti());
        hipariPalkki.setStringPainted(true);
        hipariPalkki.setString("Hiparit: " + peli.getPelaaja().getNytHP() +" / "+ peli.getPelaaja().getMaksHP());

        miniKarttaPaneeli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        miniKarttaPaneeli.setMaximumSize(new java.awt.Dimension(105, 105));
        miniKarttaPaneeli.setMinimumSize(new java.awt.Dimension(105, 105));
        miniKarttaPaneeli.setPreferredSize(new java.awt.Dimension(105, 105));
        miniKarttaPaneeli.setLayout(new java.awt.BorderLayout());
        miniKarttaPaneeli.add(minikartta);

        javax.swing.GroupLayout perusPaneeliLayout = new javax.swing.GroupLayout(perusPaneeli);
        perusPaneeli.setLayout(perusPaneeliLayout);
        perusPaneeliLayout.setHorizontalGroup(
            perusPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(perusPaneeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(perusPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nakyma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lokiPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(perusPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miniKarttaPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oikeaPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        perusPaneeliLayout.setVerticalGroup(
            perusPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(perusPaneeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(perusPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(perusPaneeliLayout.createSequentialGroup()
                        .addComponent(Nakyma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lokiPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(perusPaneeliLayout.createSequentialGroup()
                        .addComponent(miniKarttaPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oikeaPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(9, 9, 9))
        );

        piirraKartta();

        menuPalkki.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        peliMenu.setText("Peli");

        peliMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        peliMenuItem1.setText("Uusi Peli");
        peliMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peliMenuItem1ActionPerformed(evt);
            }
        });
        peliMenu.add(peliMenuItem1);

        peliMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        peliMenuItem2.setText("Lopeta");
        peliMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peliMenuItem2ActionPerformed(evt);
            }
        });
        peliMenu.add(peliMenuItem2);

        menuPalkki.add(peliMenu);

        apuMenu.setText("Apu");

        menuOhje.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuOhje.setText("Ohje");
        menuOhje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOhjeActionPerformed(evt);
            }
        });
        apuMenu.add(menuOhje);

        menuPalkki.add(apuMenu);

        setJMenuBar(menuPalkki);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(perusPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(perusPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Lopettaa pelin valikosta
     *
     */
    private void peliMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peliMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_peliMenuItem2ActionPerformed

    /**
     * Kutsutaan parannus-nappia painettaessa
     */
    private void parannusNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parannusNappiActionPerformed
        int parannus = this.peli.getPelaaja().getMaksHP() / 2;
        int kulutus = this.peli.getPelaaja().getMaksimiPuhti() / 2;
        if (this.peli.getPelaaja().vahennaPuhtia(kulutus)) {
            ToistaAani.PARANNUS.toista();
            this.peli.getPelaaja().paranna(parannus);
            this.peli.getLoki().kirjaa(peli.getPelaaja().getNimi() + " paransi itseään " + parannus + " pistettä");
            pelaaKierros();
        } else {
            this.peli.getLoki().kirjaa("Ei tarpeeksi puhtia parantamiseen!");
            paivitaKaikki();
        }
    }//GEN-LAST:event_parannusNappiActionPerformed

    /**
     * Näyttää parannusnapin ohjetekstin
     *
     */
    private void parannusNappiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parannusNappiMouseEntered
        parannusNappi.setToolTipText("Parantaa 50 % maksimihipairesta. Käyttää 50 % puhdista.");
        parannusNappi.getToolTipText();
    }//GEN-LAST:event_parannusNappiMouseEntered

    /**
     * Liikkumisnappi
     */
    private void yNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yNappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 0);
        pelaaKierros();
    }//GEN-LAST:event_yNappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void oYnappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oYnappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 1);
        pelaaKierros();
    }//GEN-LAST:event_oYnappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void oNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oNappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 2);
        pelaaKierros();
    }//GEN-LAST:event_oNappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void oAnappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oAnappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 3);
        pelaaKierros();
    }//GEN-LAST:event_oAnappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void aNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aNappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 4);
        pelaaKierros();
    }//GEN-LAST:event_aNappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void vAnappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vAnappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 5);
        pelaaKierros();
    }//GEN-LAST:event_vAnappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void vNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vNappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 6);
        pelaaKierros();
    }//GEN-LAST:event_vNappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void vYnappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vYnappiActionPerformed
        this.peli.getKartta().liikuta(this.peli.getPelaaja(), 7);
        pelaaKierros();
    }//GEN-LAST:event_vYnappiActionPerformed

    /**
     * Liikkumisnappi
     */
    private void odotaNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odotaNappiActionPerformed
        pelaaKierros();
    }//GEN-LAST:event_odotaNappiActionPerformed

    /**
     * Valikosta uusi peli
     */
    private void peliMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peliMenuItem1ActionPerformed
        uusiPeli();
    }//GEN-LAST:event_peliMenuItem1ActionPerformed

    private void reppuNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reppuNappiActionPerformed
        avaaReppu();
    }//GEN-LAST:event_reppuNappiActionPerformed

    private void vimmaNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vimmaNappiActionPerformed
        if (this.peli.getKohde() != null) {
            if (Tyokalupakki.haeEtaisyys(pelaajanSijainti, this.peli.getKartta().getSijainti(this.peli.getKohde())) <= 1) {
                this.peli.hyokkaa(new VimmaHyokkays(this.peli.getPelaaja(), this.peli.getKohde(), this.peli));
                pelaaKierros();
            } else {
                this.peli.getLoki().kirjaa("Kohde on liian kaukana!");
            }
        } else {
            this.peli.getLoki().kirjaa("Ei kohdetta!");
        }
        paivitaKaikki();
    }//GEN-LAST:event_vimmaNappiActionPerformed

    private void perusPaneeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_perusPaneeliKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_NUMPAD1) { // vas alas
            vAnappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD2) {  // alas
            aNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD3) {  // oik alas
            oAnappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD4) {  // vas
            vNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD5) {  // kesk
            odotaNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD6) {  // oik
            oNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD7) {  // vas ylös
            vYnappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD8) {  // ylös
            yNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_NUMPAD9) {  //  oik ylös
            oYnappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_E) {  //  parannus
            parannusNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_R) {  //  avaa repun
            reppuNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_W) {  //  taikaloitsu
            taikaLoitsuActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_Q) {  //  Vimmahyökkäys
            vimmaNappiActionPerformed(null);
        }
    }//GEN-LAST:event_perusPaneeliKeyPressed

    private void vimmaNappiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vimmaNappiMouseEntered
        vimmaNappi.setToolTipText("-3 osumiseen, tekee kaksinkertaisen vahingon osuessaan. Käyttää 25 % puhdista.");
        vimmaNappi.getToolTipText();
    }//GEN-LAST:event_vimmaNappiMouseEntered

    private void taikaLoitsuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taikaLoitsuMouseEntered
        taikaLoitsu.setToolTipText("Etähyökkäys joka osuu aina, mutta käyttää 100 % puhdista. Tekee pelaajan tason * d3 + pelaajan taso vahinkoa.");
        taikaLoitsu.getToolTipText();
    }//GEN-LAST:event_taikaLoitsuMouseEntered

    private void taikaLoitsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taikaLoitsuActionPerformed
        if (this.peli.getKohde() != null) {
            if (this.peli.onkoNakoyhteys(pelaajanSijainti, this.peli.getKartta().getSijainti(this.peli.getKohde()))) {
                this.peli.hyokkaa(new Taikaloitsu(this.peli.getPelaaja(), this.peli.getKohde(), this.peli));
                pelaaKierros();
            } else {
                this.peli.getLoki().kirjaa("Ei näköyhteyttä kohteeseen!");
            }
        } else {
            this.peli.getLoki().kirjaa("Ei kohdetta!");
        }
        paivitaKaikki();
    }//GEN-LAST:event_taikaLoitsuActionPerformed

    private void odotaNappiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_odotaNappiMouseEntered
        odotaNappi.setToolTipText("Odota yksi vuoro tekemättä mitään.");
        odotaNappi.getToolTipText();
    }//GEN-LAST:event_odotaNappiMouseEntered

    private void menuOhjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOhjeActionPerformed
        OhjeIkkuna ohje = new OhjeIkkuna(this, true);
        ohje.setVisible(true);
    }//GEN-LAST:event_menuOhjeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            UIManager.put("ProgressBar.selectionBackground", Color.black);
            UIManager.put("ProgressBar.selectionForeground", Color.black);


        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaaNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaaNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaaNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaaNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new PaaNakyma().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Nakyma;
    private javax.swing.JButton aNappi;
    private javax.swing.JMenu apuMenu;
    private javax.swing.JLabel babTeksti;
    private javax.swing.JLabel expaTeksti;
    private javax.swing.JProgressBar hipariPalkki;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel kestavyysTeksti;
    private javax.swing.JLabel ketteryysTeksti;
    private javax.swing.JLabel kohdeTeksti;
    private javax.swing.JPanel liikkumisPaneeli;
    private javax.swing.JPanel lokiPaneeli;
    private javax.swing.JTextArea lokiTeksti;
    private javax.swing.JMenuItem menuOhje;
    private javax.swing.JMenuBar menuPalkki;
    private javax.swing.JPanel miniKarttaPaneeli;
    private javax.swing.JButton oAnappi;
    private javax.swing.JButton oNappi;
    private javax.swing.JButton oYnappi;
    private javax.swing.JButton odotaNappi;
    private javax.swing.JPanel oikeaPaneeli;
    private javax.swing.JLabel panssariTeksti;
    private javax.swing.JButton parannusNappi;
    private javax.swing.JMenu peliMenu;
    private javax.swing.JMenuItem peliMenuItem1;
    private javax.swing.JMenuItem peliMenuItem2;
    private javax.swing.JPanel perusPaneeli;
    private javax.swing.JProgressBar puhtiPalkki;
    private javax.swing.JButton reppuNappi;
    private javax.swing.JButton taikaLoitsu;
    private javax.swing.JButton vAnappi;
    private javax.swing.JButton vNappi;
    private javax.swing.JButton vYnappi;
    private javax.swing.JButton vimmaNappi;
    private javax.swing.JLabel voimaTeksti;
    private javax.swing.JButton yNappi;
    // End of variables declaration//GEN-END:variables
}
