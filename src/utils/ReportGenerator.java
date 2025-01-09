package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ClassDAO;
import dao.RegistrationDAO;
import dao.StudentDAO;
import models.Class;
import models.Registration;
import models.Student;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    private StudentDAO studentDAO;
    private ClassDAO classDAO;
    private RegistrationDAO registrationDAO;

    public ReportGenerator() {
        studentDAO = new StudentDAO();
        classDAO = new ClassDAO();
        registrationDAO = new RegistrationDAO();
    }

    public void generateStudentReport(String dest) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Judul Laporan
            Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Data Siswa", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Spasi

            // Membuat Tabel
            PdfPTable table = new PdfPTable(5); // 5 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Header Tabel
            table.addCell("ID");
            table.addCell("Nama");
            table.addCell("Gender");
            table.addCell("Tanggal Lahir");
            table.addCell("Telepon");

            // Data Tabel
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                table.addCell(String.valueOf(student.getStudentId()));
                table.addCell(student.getName());
                table.addCell(student.getGender());
                table.addCell(student.getBirthDate());
                table.addCell(student.getPhone());
            }

            document.add(table);

            System.out.println("Laporan Siswa berhasil dibuat: " + dest);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public void generateClassReport(String dest) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Judul Laporan
            Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Data Kelas", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Spasi

            // Membuat Tabel
            PdfPTable table = new PdfPTable(2); // 2 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Header Tabel
            table.addCell("ID Kelas");
            table.addCell("Nama Kelas");

            // Data Tabel
            List<Class> classes = classDAO.getAllClasses();
            for (Class c : classes) {
                table.addCell(String.valueOf(c.getClassId()));
                table.addCell(c.getClassName());
            }

            document.add(table);

            System.out.println("Laporan Kelas berhasil dibuat: " + dest);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public void generateRegistrationReport(String dest) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Judul Laporan
            Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Registrasi", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Spasi

            // Membuat Tabel
            PdfPTable table = new PdfPTable(4); // 4 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Header Tabel
            PdfPCell header1 = new PdfPCell(new Phrase("ID Registrasi"));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header1);

            PdfPCell header2 = new PdfPCell(new Phrase("Nama Siswa"));
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header2);

            PdfPCell header3 = new PdfPCell(new Phrase("Nama Kelas"));
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header3);

            PdfPCell header4 = new PdfPCell(new Phrase("Tanggal Registrasi"));
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header4);

            // Data Tabel
            List<Registration> registrations = registrationDAO.getAllRegistrations();
            for (Registration registration : registrations) {
                table.addCell(String.valueOf(registration.getRegistrationId())); // Menggunakan `getRegistrationId()`
                table.addCell(registration.getStudentName()); // Nama siswa
                table.addCell(registration.getClassName());   // Nama kelas
                table.addCell(registration.getRegistrationDate()); // Tanggal registrasi
            }

            document.add(table);

            System.out.println("Laporan Registrasi berhasil dibuat: " + dest);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}
