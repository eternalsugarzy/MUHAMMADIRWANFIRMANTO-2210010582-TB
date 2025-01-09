package controllers;

import dao.StudentDAO;
import dao.ClassDAO;
import dao.RegistrationDAO;
import models.Student;
import models.Class;
import models.Registration;
import views.MainFrame;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import utils.ReportGenerator;

public class MainController {

    private MainFrame view;
    private StudentDAO studentDAO;
    private ClassDAO classDAO;
    private RegistrationDAO registrationDAO;

    public MainController(MainFrame view) {
        this.view = view;
        this.studentDAO = new StudentDAO();
        this.classDAO = new ClassDAO();
        this.registrationDAO = new RegistrationDAO();

        initController();
    }

    private void initController() {

        loadStudentsToComboBox();
        loadClassesToComboBox();

        // Tombol laporan siswa
        view.getBtnStudentReport().addActionListener(e -> generateStudentReport());

        // Tombol laporan kelas
        view.getBtnClassReport().addActionListener(e -> generateClassReport());

        // Tambahkan listener untuk tombol refresh pada tab registrasi
        view.getBtnRefreshRegistrations().addActionListener(e -> {
            loadStudentsToComboBox();
            loadClassesToComboBox();
            view.clearRegistrationForm(); // Kosongkan form
        });

        // Tambahkan listener untuk tombol tambah siswa
        view.getBtnAddStudent().addActionListener(e -> {
            addStudent();
            loadStudentsToComboBox(); // Perbarui ComboBox siswa
        });

        // Tambahkan listener untuk tombol tambah kelas
        view.getBtnAddClass().addActionListener(e -> {
            addClass();
            loadClassesToComboBox(); // Perbarui ComboBox kelas
        });
        // Tambahkan MouseListener untuk tabel siswa
        view.getTblStudents().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fillStudentFormFromTable(); // Panggil metode untuk mengisi form
            }
        });

        // Listener untuk tabel kelas
        view.getTblClasses().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fillClassFormFromTable(); // Panggil metode untuk mengisi form kelas
            }
        });

        // Listener untuk tombol Refresh di tab Kelas
        view.getBtnRefreshClasses().addActionListener(e -> clearClassForm());

        // Tab Students
        view.getBtnRefreshStudents().addActionListener(e -> clearStudentForm());
        view.getBtnAddStudent().addActionListener(e -> addStudent());
        view.getBtnUpdateStudent().addActionListener(e -> updateStudent());
        view.getBtnDeleteStudent().addActionListener(e -> deleteStudent());
        view.getBtnRefreshStudents().addActionListener(e -> loadStudents());

        // Tab Classes
        view.getBtnAddClass().addActionListener(e -> addClass());
        view.getBtnUpdateClass().addActionListener(e -> updateClass());
        view.getBtnDeleteClass().addActionListener(e -> deleteClass());
        view.getBtnRefreshClasses().addActionListener(e -> loadClasses());

        // Tab Registrations
        view.getBtnAddRegistration().addActionListener(e -> addRegistration());
        view.getBtnDeleteRegistration().addActionListener(e -> deleteRegistration());
        view.getBtnRefreshRegistrations().addActionListener(e -> loadRegistrations());
        view.getBtnCetakLaporan().addActionListener(e -> cetakLaporanRegistrasi());

        // Load initial data
        loadStudents();
        loadClasses();
        loadRegistrations();
    }

    // Tab Students
    private void addStudent() {
        String name = view.getTxtName().getText();
        String gender = view.getCmbGender().getSelectedItem().toString();
        Date birthDate = view.getDateChooserBirthDate().getDate(); // Ambil nilai dari JDateChooser
        String phone = view.getTxtPhone().getText();

        // Validasi input
        if (name.isEmpty() || phone.isEmpty() || birthDate == null) {
            view.showError("Semua field harus diisi!");
            return;
        }

        // Konversi tanggal ke format YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedBirthDate = dateFormat.format(birthDate);

        // Tambahkan siswa
        Student student = new Student(0, name, gender, formattedBirthDate, phone);
        studentDAO.addStudent(student);

        // Refresh tabel dan bersihkan form
        loadStudents();
        view.clearStudentForm();
        view.showMessage("Siswa berhasil ditambahkan!");
    }

    private void updateStudent() {
        int selectedRow = view.getTblStudents().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih siswa yang ingin diperbarui!");
            return;
        }

        int studentId = (int) view.getTblStudents().getValueAt(selectedRow, 0);
        String name = view.getTxtName().getText();
        String gender = view.getCmbGender().getSelectedItem().toString();
        Date birthDate = view.getDateChooserBirthDate().getDate(); // Ambil tanggal dari JDateChooser
        String phone = view.getTxtPhone().getText();

        // Validasi input
        if (name.isEmpty() || phone.isEmpty() || birthDate == null) {
            view.showError("Nama, Telepon, dan Tanggal Lahir harus diisi!");
            return;
        }

        // Konversi tanggal ke format YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedBirthDate = dateFormat.format(birthDate);

        // Perbarui data siswa
        Student student = new Student(studentId, name, gender, formattedBirthDate, phone);
        studentDAO.updateStudent(student);

        // Refresh tabel dan bersihkan form
        loadStudents();
        view.clearStudentForm();
        view.showMessage("Siswa berhasil diperbarui!");
    }

    private void deleteStudent() {
        int selectedRow = view.getTblStudents().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih siswa yang ingin dihapus!");
            return;
        }

        int studentId = (int) view.getTblStudents().getValueAt(selectedRow, 0);
        studentDAO.deleteStudent(studentId);
        loadStudents();
        view.showMessage("Siswa berhasil dihapus!");
    }

    private void loadStudents() {
        List<Student> students = studentDAO.getAllStudents();
        DefaultTableModel model = (DefaultTableModel) view.getTblStudents().getModel();
        model.setRowCount(0);
        for (Student student : students) {
            model.addRow(new Object[]{student.getStudentId(), student.getName(), student.getGender(), student.getBirthDate(), student.getPhone()});
        }
    }

    // Tab Classes
    private void addClass() {
        String className = view.getTxtClassName().getText();

        if (className.isEmpty()) {
            view.showError("Nama Kelas harus diisi!");
            return;
        }

        Class newClass = new Class(0, className);
        classDAO.addClass(newClass);
        loadClasses();
        view.clearClassForm();
        view.showMessage("Kelas berhasil ditambahkan!");
    }

    private void updateClass() {
        int selectedRow = view.getTblClasses().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih kelas yang ingin diperbarui!");
            return;
        }

        int classId = (int) view.getTblClasses().getValueAt(selectedRow, 0);
        String className = view.getTxtClassName().getText();

        if (className.isEmpty()) {
            view.showError("Nama Kelas harus diisi!");
            return;
        }

        Class updatedClass = new Class(classId, className);
        classDAO.updateClass(updatedClass);
        loadClasses();
        view.clearClassForm();
        view.showMessage("Kelas berhasil diperbarui!");
    }

    private void deleteClass() {
        int selectedRow = view.getTblClasses().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih kelas yang ingin dihapus!");
            return;
        }

        int classId = (int) view.getTblClasses().getValueAt(selectedRow, 0);
        classDAO.deleteClass(classId);
        loadClasses();
        view.showMessage("Kelas berhasil dihapus!");
    }

    private void loadClasses() {
        List<Class> classes = classDAO.getAllClasses();
        DefaultTableModel model = (DefaultTableModel) view.getTblClasses().getModel();
        model.setRowCount(0);
        for (Class c : classes) {
            model.addRow(new Object[]{c.getClassId(), c.getClassName()});
        }
    }

    // Tab Registrations
    private void addRegistration() {
        // Ambil data siswa dan kelas yang dipilih
        String selectedStudent = (String) view.getCmbStudents().getSelectedItem(); // Ambil nama siswa
        String selectedClass = (String) view.getCmbClasses().getSelectedItem();    // Ambil nama kelas

        if (selectedStudent == null || selectedClass == null) {
            view.showError("Siswa dan Kelas harus dipilih!");
            return;
        }

        // Cari ID berdasarkan nama siswa dan kelas
        int studentId = studentDAO.getStudentIdByName(selectedStudent);
        int classId = classDAO.getClassIdByName(selectedClass);

        // Ambil tanggal registrasi dari JDateChooser di tab Registrasi
        String registrationDate = new SimpleDateFormat("yyyy-MM-dd")
                .format(view.getDateChooserRegistrationDate().getDate());

        // Buat objek registrasi baru
        Registration registration = new Registration(0, studentId, classId, registrationDate);

        // Simpan data registrasi ke database
        registrationDAO.addRegistration(registration);

        // Perbarui tabel dan tampilkan pesan sukses
        loadRegistrations();
        view.showMessage("Registrasi berhasil ditambahkan!");

        // Kosongkan form
        view.clearRegistrationForm();
    }

    private void updateRegistration() {
        int selectedRow = view.getTblRegistrations().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih registrasi yang ingin diperbarui!");
            return;
        }

        int registrationId = (int) view.getTblRegistrations().getValueAt(selectedRow, 0);
        int studentId = view.getSelectedStudentId();
        int classId = view.getSelectedClassId();

        // Mendapatkan tanggal dari JDateChooser
        String registrationDate = new SimpleDateFormat("yyyy-MM-dd")
                .format(view.getDateChooserRegistrationDate().getDate());

        // Validasi tanggal
        if (registrationDate == null || registrationDate.isEmpty()) {
            view.showError("Tanggal Registrasi harus diisi!");
            return;
        }

        // Membuat objek registrasi yang diperbarui
        Registration registration = new Registration(registrationId, studentId, classId, registrationDate);

        // Memperbarui data registrasi di database
        registrationDAO.updateRegistration(registration);

        // Memuat ulang data registrasi di tabel
        loadRegistrations();

        // Membersihkan form input
        view.clearRegistrationForm();

        // Menampilkan pesan sukses
        view.showMessage("Registrasi berhasil diperbarui!");
    }

    private void deleteRegistration() {
        int selectedRow = view.getTblRegistrations().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Pilih registrasi yang ingin dihapus!");
            return;
        }

        int registrationId = (int) view.getTblRegistrations().getValueAt(selectedRow, 0);
        registrationDAO.deleteRegistration(registrationId);
        loadRegistrations();
        view.showMessage("Registrasi berhasil dihapus!");
    }

    private void loadRegistrations() {
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        DefaultTableModel model = (DefaultTableModel) view.getTblRegistrations().getModel();
        model.setRowCount(0);
        for (Registration r : registrations) {
            model.addRow(new Object[]{
                r.getRegistrationId(),
                r.getStudentName(), // Mendapatkan nama siswa
                r.getClassName(), // Mendapatkan nama kelas
                r.getRegistrationDate()
            });
        }
    }

    private void fillStudentFormFromTable() {
        int selectedRow = view.getTblStudents().getSelectedRow(); // Baris yang dipilih
        if (selectedRow != -1) { // Pastikan ada baris yang dipilih
            // Ambil data dari tabel
            int studentId = (int) view.getTblStudents().getValueAt(selectedRow, 0);
            String name = (String) view.getTblStudents().getValueAt(selectedRow, 1);
            String gender = (String) view.getTblStudents().getValueAt(selectedRow, 2);
            String birthDate = (String) view.getTblStudents().getValueAt(selectedRow, 3);
            String phone = (String) view.getTblStudents().getValueAt(selectedRow, 4);

            // Isi field input di MainFrame
            view.getTxtName().setText(name);
            view.getCmbGender().setSelectedItem(gender);
            try {
                // Format tanggal jika diperlukan
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
                view.getDateChooserBirthDate().setDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.getTxtPhone().setText(phone);
        }
    }

    private void fillClassFormFromTable() {
        int selectedRow = view.getTblClasses().getSelectedRow(); // Baris yang dipilih
        if (selectedRow != -1) { // Pastikan ada baris yang dipilih
            // Ambil data dari tabel
            int classId = (int) view.getTblClasses().getValueAt(selectedRow, 0);
            String className = (String) view.getTblClasses().getValueAt(selectedRow, 1);

            // Isi field input di MainFrame
            view.getTxtClassName().setText(className);

            // Opsional: Simpan classId ke dalam field jika diperlukan untuk update
            view.setSelectedClassId(classId);
        }
    }

    private void clearStudentForm() {
        // Kosongkan semua field input di tab siswa
        view.getTxtName().setText("");
        view.getCmbGender().setSelectedIndex(0); // Pilih opsi pertama (misalnya, "Laki-laki" atau "Perempuan")
        view.getDateChooserBirthDate().setDate(null); // Hapus tanggal di JDateChooser
        view.getTxtPhone().setText("");
    }

    private void clearClassForm() {
        // Kosongkan semua field input di tab kelas
        view.getTxtClassName().setText(""); // Kosongkan nama kelas

        // Opsional: Kosongkan variabel yang menyimpan ID kelas yang dipilih
        view.setSelectedClassId(0);
    }

    private void loadStudentsToComboBox() {
        List<Student> students = studentDAO.getAllStudents(); // Ambil data siswa dari database
        JComboBox<String> cmbStudents = view.getCmbStudents();
        cmbStudents.removeAllItems(); // Kosongkan ComboBox sebelum diisi
        for (Student student : students) {
            cmbStudents.addItem(student.getName()); // Tambahkan nama siswa
        }
    }

    private void loadClassesToComboBox() {
        List<Class> classes = classDAO.getAllClasses(); // Ambil data kelas dari database
        JComboBox<String> cmbClasses = view.getCmbClasses();
        cmbClasses.removeAllItems(); // Kosongkan ComboBox sebelum diisi
        for (Class c : classes) {
            cmbClasses.addItem(c.getClassName()); // Tambahkan nama kelas
        }
    }

    private void generateStudentReport() {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateStudentReport("students_report.pdf");
        view.showMessage("Laporan Siswa berhasil dibuat di students_report.pdf!");
    }

    private void generateClassReport() {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateClassReport("classes_report.pdf");
        view.showMessage("Laporan Kelas berhasil dibuat di classes_report.pdf!");
    }

    private void cetakLaporanRegistrasi() {
        try {
            String dest = "laporan_registrasi.pdf"; // Lokasi file PDF yang akan disimpan
            ReportGenerator reportGenerator = new ReportGenerator();
            reportGenerator.generateRegistrationReport(dest);
            JOptionPane.showMessageDialog(view, "Laporan Registrasi berhasil disimpan di: " + dest);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal mencetak laporan registrasi!");
            e.printStackTrace();
        }
    }

}
