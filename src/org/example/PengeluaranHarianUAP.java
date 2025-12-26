package org.example;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PengeluaranHarianUAP extends JFrame {

    
    Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
    Font font = new Font("Segoe UI", Font.PLAIN, 14);

    Color bgBlack   = new Color(22, 22, 24);
    Color cardBlack = new Color(36, 36, 40);
    Color softPink  = new Color(240, 180, 200);
    Color white     = new Color(245, 245, 245);
    Color gray      = new Color(180, 180, 180);

    
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    JTextField txtTanggal, txtKeterangan, txtJumlah, txtSearch;
    JLabel lblTotal, lblJumlahData;
    JTable table;
    DefaultTableModel model;

    int selectedIndex = -1;

    ArrayList<Pengeluaran> dataList = new ArrayList<>();
    final String FILE_NAME = "pengeluaran.csv";

    public PengeluaranHarianUAP() {
        setTitle("Pengeluaran Harian");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.setBackground(bgBlack);

        mainPanel.add(loginPanel(), "LOGIN");
        mainPanel.add(dashboard(), "DASHBOARD");
        mainPanel.add(formInput(), "FORM");
        mainPanel.add(listData(), "LIST");
        mainPanel.add(laporan(), "LAPORAN");

        add(mainPanel);
        loadData();
        cardLayout.show(mainPanel, "LOGIN");
    }

 
    private JPanel loginPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(bgBlack);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(360, 320));
        card.setBackground(cardBlack);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("LOGIN");
        title.setFont(titleFont);
        title.setForeground(softPink);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = label("Username");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUser = field("");
        txtUser.setMaximumSize(new Dimension(260, 35));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPass = label("Password");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(font);
        txtPass.setBorder(new LineBorder(softPink));
        txtPass.setMaximumSize(new Dimension(260, 35));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = actionButton("Masuk");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addActionListener(e -> {
            if (txtUser.getText().equals("admin")
                    && String.valueOf(txtPass.getPassword()).equals("admin")) {
                cardLayout.show(mainPanel, "DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username / Password salah");
            }
        });

        card.add(title);
        card.add(Box.createVerticalStrut(25));
        card.add(lblUser);
        card.add(txtUser);
        card.add(Box.createVerticalStrut(12));
        card.add(lblPass);
        card.add(txtPass);
        card.add(Box.createVerticalStrut(30));
        card.add(btn);

        root.add(card);
        return root;
    }


    private JPanel dashboard() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(bgBlack);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(380, 420));
        card.setBackground(cardBlack);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Pengeluaran Harian");
        title.setFont(titleFont);
        title.setForeground(softPink);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("catat keuangan kamu");
        sub.setForeground(gray);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(sub);
        card.add(Box.createVerticalStrut(30));

        card.add(menuButton("Input Data", "FORM"));
        card.add(Box.createVerticalStrut(15));
        card.add(menuButton("Lihat Data", "LIST"));
        card.add(Box.createVerticalStrut(15));
        card.add(menuButton("Laporan", "LAPORAN"));
        card.add(Box.createVerticalStrut(15));
        card.add(menuButton("Logout", "LOGIN"));

        root.add(card);
        return root;
    }


    private JPanel formInput() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(bgBlack);
        panel.setBorder(new EmptyBorder(30, 220, 30, 220));

        JLabel title = new JLabel("Input Pengeluaran");
        title.setFont(titleFont);
        title.setForeground(softPink);

        JPanel form = new JPanel(new GridLayout(6, 1, 10, 10));
        form.setBackground(cardBlack);
        form.setBorder(new EmptyBorder(25, 25, 25, 25));

        txtTanggal = field(LocalDate.now().toString());
        txtKeterangan = field("");
        txtJumlah = field("");

        form.add(label("Tanggal"));
        form.add(txtTanggal);
        form.add(label("Keterangan"));
        form.add(txtKeterangan);
        form.add(label("Jumlah"));
        form.add(txtJumlah);

        JButton save = actionButton("Simpan");
        JButton back = actionButton("Kembali");

        save.addActionListener(e -> simpanData());
        back.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        JPanel btn = new JPanel();
        btn.setBackground(bgBlack);
        btn.add(save);
        btn.add(back);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        return panel;
    }

   
    private JPanel listData() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(bgBlack);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        model = new DefaultTableModel(
                new String[]{"Tanggal", "Keterangan", "Jumlah"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);

        table.getSelectionModel().addListSelectionListener(e -> {
            selectedIndex = table.getSelectedRow();
            if (selectedIndex >= 0) {
                txtTanggal.setText(model.getValueAt(selectedIndex, 0).toString());
                txtKeterangan.setText(model.getValueAt(selectedIndex, 1).toString());
                txtJumlah.setText(model.getValueAt(selectedIndex, 2).toString());
            }
        });
        txtSearch = field("");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchData();
            }
        });

        JButton update = actionButton("Update");
        JButton delete = actionButton("Delete");
        JButton back = actionButton("Kembali");

        update.addActionListener(e -> updateData());
        delete.addActionListener(e -> deleteData());
        back.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        JPanel btn = new JPanel();
        btn.setBackground(bgBlack);
        btn.add(update);
        btn.add(delete);
        btn.add(back);

        panel.add(txtSearch, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        return panel;
    }

    // ================= LAPORAN =================
    private JPanel laporan() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(bgBlack);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Laporan Pengeluaran");
        title.setFont(titleFont);
        title.setForeground(softPink);

        lblTotal = new JLabel();
        lblTotal.setFont(titleFont);
        lblTotal.setForeground(white);

        lblJumlahData = new JLabel();
        lblJumlahData.setForeground(gray);

        JButton back = actionButton("Kembali");
        back.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        panel.add(title);
        panel.add(lblTotal);
        panel.add(lblJumlahData);
        panel.add(back);
        return panel;
    }

   
    private void simpanData() {
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText());
            dataList.add(new Pengeluaran(
                    LocalDate.parse(txtTanggal.getText()),
                    txtKeterangan.getText(),
                    jumlah
            ));
            saveData();
            refreshTable();
            JOptionPane.showMessageDialog(this,
                    "Data berhasil disimpan");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah harus berupa angka");
        }
    }

    private void updateData() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data yang ingin diupdate");
            return;
        }
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText());
            Pengeluaran p = dataList.get(selectedIndex);
            p.tanggal = LocalDate.parse(txtTanggal.getText());
            p.keterangan = txtKeterangan.getText();
            p.jumlah = jumlah;

            saveData();
            refreshTable();
            JOptionPane.showMessageDialog(this,
                    "Data berhasil diupdate");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah harus berupa angka");
        }
    }

    private void deleteData() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data yang ingin dihapus");
            return;
        }
        dataList.remove(selectedIndex);
        saveData();
        refreshTable();
        JOptionPane.showMessageDialog(this,
                "Data berhasil dihapus");
    }

    private void searchData() {
        model.setRowCount(0);
        for (Pengeluaran p : dataList)
            if (p.keterangan.toLowerCase()
                    .contains(txtSearch.getText().toLowerCase()))
                model.addRow(new Object[]{
                        p.tanggal, p.keterangan, p.jumlah});
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Pengeluaran p : dataList)
            model.addRow(new Object[]{
                    p.tanggal, p.keterangan, p.jumlah});
    }

    private void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Pengeluaran p : dataList)
                pw.println(p.tanggal + "," + p.keterangan + "," + p.jumlah);
        } catch (IOException ignored) {}
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(",");
                dataList.add(new Pengeluaran(
                        LocalDate.parse(d[0]),
                        d[1],
                        Integer.parseInt(d[2])
                ));
            }
        } catch (IOException ignored) {}
    }


    private JButton menuButton(String text, String target) {
        JButton b = actionButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(e -> {
            if (target.equals("LIST")) {
                refreshTable();
            }
            if (target.equals("LAPORAN")) {
                refreshLaporan(); // 🔥 INI YANG BIKIN LAPORAN MUNCUL
            }
            cardLayout.show(mainPanel, target);
        });
        return b;
    }

    private void refreshLaporan() {
        int total = 0;
        for (Pengeluaran p : dataList) {
            total += p.jumlah;
        }

        lblTotal.setText("Total Pengeluaran: Rp " + total);
        lblJumlahData.setText("Jumlah Data: " + dataList.size());
    }


    private JButton actionButton(String t) {
        JButton b = new JButton(t);
        b.setBackground(softPink);
        b.setForeground(Color.BLACK);
        b.setFont(font);
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        return b;
    }

    private JTextField field(String v) {
        JTextField f = new JTextField(v);
        f.setFont(font);
        f.setBorder(new LineBorder(softPink));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return f;
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setForeground(white);
        l.setFont(font);
        return l;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new PengeluaranHarianUAP().setVisible(true));
    }
}


class Pengeluaran {
    LocalDate tanggal;
    String keterangan;
    int jumlah;

    Pengeluaran(LocalDate t, String k, int j) {
        tanggal = t;
        keterangan = k;
        jumlah = j;
    }
}
        
