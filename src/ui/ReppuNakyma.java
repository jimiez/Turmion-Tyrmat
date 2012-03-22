package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import logiikka.Peli;
import logiikka.esineet.Esine;

/**
 * Kutsutaan kun pelaaja haluaa tarkastella reppunsa sisältöä
 *
 */
public class ReppuNakyma extends javax.swing.JDialog {

    private Peli peli;
    private DefaultListModel listModelJasen;
    private PaaNakyma paanakyma;

    /**
     * Täyttää reppulistan repun sisällöllä
     *
     */
    public synchronized void AlustaReppu() {

        Map<String, ArrayList<Esine>> palautaKaikki = this.peli.getReppu().palautaKaikki();
        listModelJasen.removeAllElements();
        for (String n : palautaKaikki.keySet()) {
            for (Esine e : palautaKaikki.get(n)) {
                if (e.equals(this.peli.getPelaaja().getAse())) {
                    this.aseTeksti.revalidate();
                } else if (e.equals(this.peli.getPelaaja().getPanssari())) {
                    this.panssariTeksti.revalidate();
                } else {
                    listModelJasen.addElement(e);
                }
            }
        }
    }

    /**
     * Peruskonstruktori, ei pidä kutsua koska johtaa NullPointerExceptioniin
     *
     */
    public ReppuNakyma() {

        listModelJasen = new DefaultListModel();
        initComponents();
        getRootPane().setDefaultButton(poistuNappi);
    }

    /**
     * Varsinainen konstruktori jossa määritellään peli ja päänäkymä joihin
     * reppu liittyy
     * @param peli Peli (ja sitä kautta pelaaja) johon reppu liittyy
     * @param paanakyma Päänäkymä josta reppu avataan
     *
     */
    public ReppuNakyma(Peli peli, PaaNakyma paanakyma) {
        listModelJasen = new DefaultListModel();

        this.peli = peli;
        this.paanakyma = paanakyma;

        initComponents();
        getRootPane().setDefaultButton(poistuNappi);

        reppuList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public synchronized void valueChanged(ListSelectionEvent e) {
                muutosListalla();
            }
        });

        // tuho

        tuhoaNappi.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        tuhoa();
                    }
                });

        // käyttö
        kayttoNappi.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        kayta();
                    }
                });
        reppuPaneeli.requestFocus();

    }

    /**
     * Päivittää kaikki graafiset elementit reppu-ikkunassa
     *
     */
    public synchronized void paivitaKaikki() {

        painoTeksti.setText("Paino: " + this.peli.getReppu().painoString());
        aseTeksti.setText("Ase: " + this.peli.getPelaaja().getAse().getNimi() + " (" + this.peli.getPelaaja().getAse().vahinkoString() + ")");
        panssariTeksti.setText("Panssari: " + this.peli.getPelaaja().getPanssari().getNimi() + " (+" + this.peli.getPelaaja().getPanssari().getPanssariArvo() + ")");
        reppuPaneeli.requestFocus();
    }

    /**
     * Metodi jota kutsutaan kun listalla tapahtuu muutoksia
     *
     */
    public synchronized void muutosListalla() {
        int ind = reppuList.getSelectedIndex();
        if (ind < 0) {
            return;
        }
        Esine valittu = ((Esine) listModelJasen.getElementAt(reppuList.getSelectedIndex()));
        kuvausText.setText(valittu.tulostaTiedot());
        paivitaKaikki();
    }

    /**
     * Metodi jota kutsutaan kun käytä-nappia painetaan
     *
     */
    public synchronized void kayta() {
        int ind = reppuList.getSelectedIndex();

        if (ind >= 0) {
            Esine valittu = ((Esine) listModelJasen.getElementAt(ind));

            if (valittu.getToiminto(this.peli.getPelaaja())) {
                listModelJasen.removeElementAt(ind);
            } else {
                this.peli.getLoki().kirjaa(valittu + " ei tunnu tekevän mitään!");
            }

            paanakyma.paivitaKaikki();
            AlustaReppu();
            paivitaKaikki();
        }
    }

    /**
     * Metodi jota kutsutaan kun tuhoa-nappia painetaan
     *
     */
    public synchronized void tuhoa() {

        int ind = reppuList.getSelectedIndex();

        if (ind >= 0) {
            Esine valittu = ((Esine) listModelJasen.getElementAt(ind));
            this.peli.getReppu().tuhoa(valittu.getNimi());
            listModelJasen.removeElementAt(ind);
            paanakyma.paivitaKaikki();
            paivitaKaikki();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        poistuNappi = new javax.swing.JButton();
        reppuPaneeli = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reppuList = new javax.swing.JList(listModelJasen);
        jScrollPane1 = new javax.swing.JScrollPane();
        kuvausText = new javax.swing.JTextArea();
        painoTeksti = new javax.swing.JLabel();
        aseTeksti = new javax.swing.JLabel();
        panssariTeksti = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        kayttoNappi = new javax.swing.JButton();
        tuhoaNappi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reppu");
        setAlwaysOnTop(true);

        poistuNappi.setText("Poistu (R)");
        poistuNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poistuNappiActionPerformed(evt);
            }
        });

        reppuPaneeli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reppuPaneeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                reppuPaneeliKeyPressed(evt);
            }
        });

        reppuList.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        reppuList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        reppuList.setRequestFocusEnabled(false);
        reppuList.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(reppuList);

        javax.swing.GroupLayout reppuPaneeliLayout = new javax.swing.GroupLayout(reppuPaneeli);
        reppuPaneeli.setLayout(reppuPaneeliLayout);
        reppuPaneeliLayout.setHorizontalGroup(
            reppuPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        );
        reppuPaneeliLayout.setVerticalGroup(
            reppuPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reppuPaneeliLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        kuvausText.setColumns(20);
        kuvausText.setEditable(false);
        kuvausText.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        kuvausText.setLineWrap(true);
        kuvausText.setRows(5);
        kuvausText.setWrapStyleWord(true);
        jScrollPane1.setViewportView(kuvausText);

        painoTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        painoTeksti.setText("Paino: " + this.peli.getReppu().painoString());

        aseTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        aseTeksti.setText("Ase: " + this.peli.getPelaaja().getAse().getNimi() + " (" + this.peli.getPelaaja().getAse().vahinkoString() + ")");

        panssariTeksti.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        panssariTeksti.setText("Panssari: " + this.peli.getPelaaja().getPanssari().getNimi() + " (+" + this.peli.getPelaaja().getPanssari().getPanssariArvo() + ")");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel1.setText("Tällä hetkellä käytössä:");

        kayttoNappi.setText("Käytä");

        tuhoaNappi.setText("Tuhoa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reppuPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(kayttoNappi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tuhoaNappi)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(poistuNappi)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(aseTeksti)
                                    .addComponent(jLabel1)
                                    .addComponent(panssariTeksti)))))
                    .addComponent(painoTeksti))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aseTeksti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panssariTeksti)
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reppuPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painoTeksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poistuNappi)
                    .addComponent(kayttoNappi)
                    .addComponent(tuhoaNappi))
                .addContainerGap())
        );

        AlustaReppu();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void poistuNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistuNappiActionPerformed
        paanakyma.paivitaKaikki();
        this.dispose();
    }//GEN-LAST:event_poistuNappiActionPerformed

    private void reppuPaneeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_reppuPaneeliKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_R) {
            poistuNappiActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            poistuNappiActionPerformed(null);
        }
    }//GEN-LAST:event_reppuPaneeliKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());


        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReppuNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReppuNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReppuNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReppuNakyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ReppuNakyma().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aseTeksti;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton kayttoNappi;
    private javax.swing.JTextArea kuvausText;
    private javax.swing.JLabel painoTeksti;
    private javax.swing.JLabel panssariTeksti;
    private javax.swing.JButton poistuNappi;
    private javax.swing.JList reppuList;
    private javax.swing.JPanel reppuPaneeli;
    private javax.swing.JButton tuhoaNappi;
    // End of variables declaration//GEN-END:variables
}
