     # Pengeluaran Harian – Java Swing

Aplikasi *Pengeluaran Harian* merupakan aplikasi desktop berbasis *Java Swing* yang digunakan untuk mencatat dan mengelola data pengeluaran harian.
Aplikasi ini menyediakan fitur pencatatan, pengelolaan data, serta laporan pengeluaran dengan penyimpanan lokal menggunakan file CSV.

---

## Fitur

* Autentikasi login pengguna
* Input data pengeluaran (tanggal, keterangan, jumlah)
* Menampilkan data pengeluaran dalam bentuk tabel
* Update dan hapus data pengeluaran
* Pencarian data berdasarkan keterangan
* Laporan total pengeluaran dan jumlah data
* Penyimpanan data otomatis ke file CSV

---

## Teknologi

* Java
* Java Swing (GUI)
* CardLayout, GridLayout, BorderLayout
* Penyimpanan data: CSV
* Java 8 atau lebih baru

---

## Struktur Project


PengeluaranHarianUAP/
├── PengeluaranHarianUAP.java
├── pengeluaran.csv
└── README.md


---

## Cara Menjalankan

1. Pastikan Java Development Kit (JDK) telah terpasang
2. Compile program:

   bash
   javac PengeluaranHarianUAP.java

3. Jalankan aplikasi:

   bash
   java PengeluaranHarianUAP

4. Login menggunakan:

    * Username: admin
    * Password: admin

---

## Catatan

* Input jumlah pengeluaran harus berupa angka
* Data akan tetap tersimpan selama file pengeluaran.csv tersedia
* Aplikasi menggunakan CardLayout untuk navigasi antar halaman

---

## Author

Khansa & Aulia
UAP – Aplikasi Pengeluaran Harian Java Swing