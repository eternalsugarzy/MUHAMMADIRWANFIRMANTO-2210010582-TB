
# Aplikasi Pendaftaran Siswa (PPDB)

## Deskripsi
Aplikasi PPDB adalah sistem berbasis desktop untuk membantu pengelolaan data siswa, kelas, dan registrasi siswa secara efisien. Aplikasi ini memungkinkan pengguna untuk menambah, mengubah, menghapus, serta mencetak laporan data siswa, kelas, dan registrasi dalam format PDF.

## Fitur Utama
1. **Pengelolaan Data Siswa**  
   - Menambahkan data siswa baru.
   - Mengedit data siswa yang sudah ada.
   - Menghapus data siswa.
   - Menampilkan data siswa dalam tabel.
   - Mencetak laporan data siswa dalam format PDF.

2. **Pengelolaan Data Kelas**  
   - Menambahkan data kelas baru.
   - Mengedit data kelas yang sudah ada.
   - Menghapus data kelas.
   - Menampilkan data kelas dalam tabel.
   - Mencetak laporan data kelas dalam format PDF.

3. **Pengelolaan Data Registrasi**  
   - Menambahkan registrasi siswa ke dalam kelas.
   - Mengedit data registrasi.
   - Menghapus data registrasi.
   - Menampilkan data registrasi dalam tabel.
   - Mencetak laporan data registrasi dalam format PDF.

4. **Laporan**  
   - Semua data dapat diekspor menjadi laporan PDF:
     - Laporan Data Siswa
     - Laporan Data Kelas
     - Laporan Data Registrasi

## Teknologi yang Digunakan
- **Java Swing** untuk GUI (Graphical User Interface).
- **MySQL** untuk database.
- **iText PDF Library** untuk pembuatan laporan PDF.
- **NetBeans** sebagai IDE untuk pengembangan aplikasi.

## Struktur Direktori
```
src/
├── controllers/
│   ├── MainController.java           # Pengendali utama untuk aplikasi
│   ├── ClassController.java          # (opsional) Tambahkan jika ingin pengendali terpisah untuk Kelas
│   ├── StudentController.java        # (opsional) Tambahkan jika ingin pengendali terpisah untuk Siswa
│   ├── RegistrationController.java   # (opsional) Tambahkan jika ingin pengendali terpisah untuk Registrasi
├── dao/
│   ├── ClassDAO.java                 # Data Access Object untuk kelas
│   ├── RegistrationDAO.java          # Data Access Object untuk registrasi
│   ├── StudentDAO.java               # Data Access Object untuk siswa
├── models/
│   ├── Class.java                    # Model data untuk kelas
│   ├── Registration.java             # Model data untuk registrasi
│   ├── Student.java                  # Model data untuk siswa
├── utils/
│   ├── DBConnection.java             # Koneksi ke database
│   ├── ReportGenerator.java          # Untuk pembuatan laporan
│   ├── ComboBoxItem.java             # Utilitas untuk elemen ComboBox
├── views/
│   ├── MainFrame.java                # Form utama aplikasi
│   ├── FormClass.java                # (opsional) Form untuk manajemen data kelas
│   ├── FormStudent.java              # (opsional) Form untuk manajemen data siswa
│   ├── FormRegistration.java         # (opsional) Form untuk manajemen data registrasi
└── resources/
    └── reports/                      # Folder untuk menyimpan laporan PDF

```


## Prasyarat
1. **Java JDK 8 atau versi lebih baru.**
2. **MySQL** sudah terinstal dan dikonfigurasi.
3. **Library tambahan:**
   - `mysql-connector-java-x.x.x.jar`
   - `itextpdf-x.x.x.jar`
4. Pastikan MySQL database sudah dibuat dengan nama `db_ppdb` dan tabel-tabel berikut sudah tersedia:
   - **Tabel `students`:**
     ```
     CREATE TABLE students (
         student_id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(100),
         gender ENUM('Laki-Laki', 'Perempuan'),
         birth_date DATE,
         phone VARCHAR(15)
     );
     ```
   - **Tabel `classes`:**
     ```
     CREATE TABLE classes (
         class_id INT AUTO_INCREMENT PRIMARY KEY,
         class_name VARCHAR(50)
     );
     ```
   - **Tabel `registrations`:**
     ```
     CREATE TABLE registrations (
         registration_id INT AUTO_INCREMENT PRIMARY KEY,
         student_id INT,
         class_id INT,
         registration_date DATE,
         FOREIGN KEY (student_id) REFERENCES students(student_id),
         FOREIGN KEY (class_id) REFERENCES classes(class_id)
     );
     ```

## Cara Menjalankan
1. **Kloning atau unduh repository ini.**
2. **Konfigurasi Database:**
   - Buka file `utils/DBConnection.java` dan sesuaikan kredensial database Anda:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/ppdb";
     private static final String USER = "root";
     private static final String PASSWORD = "";
     ```
3. **Import library tambahan ke dalam project.**
4. **Build dan jalankan aplikasi melalui IDE Anda (NetBeans).**

## Cara Menggunakan
1. **Main Menu:**
   - Pilih salah satu menu: `Siswa`, `Kelas`, atau `Registrasi`.
2. **Pengelolaan Data:**
   - Tambahkan, ubah, atau hapus data sesuai kebutuhan.
3. **Laporan:**
   - Klik tombol "Cetak Laporan" untuk mengekspor data ke dalam file PDF.

## Catatan Penting
- Pastikan database MySQL dalam kondisi berjalan.
- Untuk menambahkan data registrasi, data siswa dan data kelas harus tersedia terlebih dahulu.
- Direktori tempat laporan PDF disimpan dapat disesuaikan sesuai kebutuhan.


