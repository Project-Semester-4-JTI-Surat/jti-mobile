package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.AnggotaDetailAdapter;
import com.example.projectsemester4.Keys.AnggotaDetailSurat;
import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.DetailSuratRequest;
import com.example.projectsemester4.Keys.DetailSuratResponse;
import com.example.projectsemester4.Keys.DetailSuratService;
import com.example.projectsemester4.Keys.MyPreferences;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DetailSurat extends AppCompatActivity {
    private TextView txJenisSurat, txDetailPengaju;
    private TextView dtNamaDosen, dtNamaMitra, dtAlamatMitra, dtTanggalPelaksanaan, dtKeterangan, dtStatus;
    private TextView dtKoordinator, dtJudulTA, dtTanggalDibuat, dtTanggalSelesai, dtKebutuhan, dtAlasanPenolakan;
    private TextView isiNamaDosen, isiNamaMitra, isiAlamatMitra, isiTanggalPelaksanaan, isiKeterangan, isiStatus;
    private TextView isiKoordinator, isiJudulTA, isiTanggalDibuat, isiTanggalSelesai, isiKebutuhan, isiAlasanPenolakan;
    private RecyclerView recyclerView;
    private AnggotaDetailAdapter adapter;
    private String suratId, jenis_surat;
    private Button btn_download;
    private String namaDosen = "";
    private String namaKoordinator = "";
    private String noHpKoordinator = "";
    private String tglPelaksanaan = "";
    private String tglSelesai = "";
    private String judulTA = "";
//    private String programStudiDosen = "";
//    private String programStudiKoordinator = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_surat_activity);

        // Inisialisasi TextView dengan ID dari layout XML
        txJenisSurat = findViewById(R.id.tx_jenisSurat);
        dtNamaDosen = findViewById(R.id.dt_namaDosen);
        isiNamaDosen = findViewById(R.id.isi_namaDosen);
        dtNamaMitra = findViewById(R.id.dt_namaMitra);
        isiNamaMitra = findViewById(R.id.isi_namaMitra);
        dtAlamatMitra = findViewById(R.id.dt_alamatMitra);
        isiAlamatMitra = findViewById(R.id.isi_alamatMitra);
        dtTanggalPelaksanaan = findViewById(R.id.dt_tanggalPelaksanaan);
        isiTanggalPelaksanaan = findViewById(R.id.isi_tanggalPelaksanaan);
        dtKeterangan = findViewById(R.id.dt_keterangan);
        isiKeterangan = findViewById(R.id.isi_keterangan);
        dtStatus = findViewById(R.id.dt_status);
        isiStatus = findViewById(R.id.isi_status);
        txDetailPengaju = findViewById(R.id.tx_detailPengaju);

        dtKoordinator = findViewById(R.id.dt_namaKoordinator);
        isiKoordinator = findViewById(R.id.isi_namaKoordinator);
        dtJudulTA = findViewById(R.id.dt_judulTA);
        isiJudulTA = findViewById(R.id.isi_judulTA);
        dtTanggalDibuat = findViewById(R.id.dt_tanggalDibuat);
        isiTanggalDibuat = findViewById(R.id.isi_tanggalDibuat);
        dtTanggalSelesai = findViewById(R.id.dt_tanggalSelesai);
        isiTanggalSelesai = findViewById(R.id.isi_tanggalSelesai);
        dtKebutuhan = findViewById(R.id.dt_kebutuhan);
        isiKebutuhan = findViewById(R.id.isi_kebutuhan);
        dtAlasanPenolakan = findViewById(R.id.dt_alasanPenolakan);
        isiAlasanPenolakan = findViewById(R.id.isi_alasanPenolakan);

        btn_download = findViewById(R.id.downloadDocument);

        recyclerView = findViewById(R.id.recycle_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AnggotaDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        // get the intent that started this activity
        Intent intent = getIntent();
//
//        // get the data from the intent
        jenis_surat = intent.getStringExtra("jenis_surat");

        txJenisSurat.setText(jenis_surat);
        suratId = getIntent().getStringExtra("surat_id");

        loadDetailSurat();
        System.out.println(suratId);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jenisSurat = txJenisSurat.getText().toString();
                String status = intent.getStringExtra("keterangan"); // Mengambil status dari suatu sumber data, misalnya database atau variabel lain

                if (status.equals("Selesai")) {
                    btn_download.setEnabled(true); // Aktifkan tombol download jika status selesai
                    if (jenisSurat.equals("Pengajuan PKL")) {
                        DownloadPDFPKL(jenisSurat);
                    } else if (jenisSurat.equals("Lain Lain")) {
                        DownloadPDFLL(jenisSurat);
                    } else if (jenisSurat.equals("Observasi Penelitian")) {
                        DownloadPDFObservasi(jenisSurat);
                    } else if (jenisSurat.equals("Tugas Akhir")) {
                        DownloadPDFTA(jenisSurat);
                    } else if (jenisSurat.equals("Mata Kuliah")) {
                        DownloadPDFMK(jenisSurat);
                    }
                } else {
                    btn_download.setEnabled(false); // Nonaktifkan tombol download jika status bukan selesai
                }
            }
        });

    }

    private void DownloadPDFMK(String jenisSurat) {
        // Membuat dokumen PDF menggunakan iTextPDF
        Document document = new Document();

        // Mendapatkan direktori penyimpanan eksternal
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Membuat file PDF dengan nama yang unik
        String fileName = "Surat_" + jenis_surat + "-" + suratId + ".pdf";
        File file = new File(directory, fileName);

        try {
            // Membuat penulis PDF menggunakan PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // Membuat objek PdfPageEventHelper untuk menambahkan nomor halaman
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                private int totalPages;

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    // Membuat objek PdfContentByte untuk menggambar konten pada halaman PDF
                    PdfContentByte canvas = writer.getDirectContent();

                    // Membuat font untuk nomor halaman
                    Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

                    // Mengambil nomor halaman saat ini
                    int pageNumber = writer.getPageNumber();

                    // Menyusun teks dengan format "Halaman X/Y + "/" + totalPages"
                    Phrase pageNumberPhrase = new Phrase("Halaman " + pageNumber, pageNumberFont);

                    // Menggambar teks nomor halaman di pojok kanan bawah
                    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pageNumberPhrase, document.right(), document.bottom(), 0);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {
                    // Menyimpan jumlah halaman total saat dokumen ditutup
                    totalPages = writer.getPageNumber();
                }
            };

            // Mendaftarkan objek PdfPageEventHelper pada PdfWriter
            writer.setPageEvent(eventHelper);

            // Membuka dokumen PDF
            document.open();

            // Menambahkan judul surat ke dokumen PDF
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Surat " + jenisSurat, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Menambahkan tanggal dan nomor surat ke dokumen PDF
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm a");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(formattedDate), 40, 820, 0);

            // Menambahkan isi surat ke dokumen PDF
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph("Nomor  : "));
            document.add(new Paragraph("Perihal : " + isiKeterangan.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Kepada Yth.", createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiNamaMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiAlamatMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Di", createBoldFont(contentFont)));
            document.add(new Paragraph("      Tempat", createBoldFont(contentFont)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dengan hormat,", createBoldFont(contentFont)));
            document.add(new Paragraph("Sehubungan dengan adanya tugas mahasiswa maka bersama ini kami menugaskan mahasiswa Program Studi Teknik Informatika Jurusan Teknologi Informasi yang namanya tersebut di bawah ini untuk melakukan survey pada perusahaan/instansi yang Bapak/Ibu pimpin.", contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Adapun nama - nama anggota kelompok yang ditugaskan adalah sebagai berikut :", contentFont));

            // Menambahkan anggota ke dokumen PDF dalam bentuk tabel
            List<AnggotaDetailSurat> anggotaList = adapter.getAnggotaList();
            if (!anggotaList.isEmpty()) {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);
                table.setSpacingAfter(5f);

                // Menambahkan border pada seluruh sel di tabel
                table.getDefaultCell().setBorder(Rectangle.BOX);

                table.addCell(createTableCell("No.", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Nama Mahasiswa", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("NIM", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Jurusan / Prodi", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("No Tlp.", createBoldFont(contentFont), Element.ALIGN_CENTER));

                int no = 1;
                for (AnggotaDetailSurat anggota : anggotaList) {
                    table.addCell(createTableCell(String.valueOf(no), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNama(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNim(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getProdi().getKeterangan(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNo_hp(), contentFont, Element.ALIGN_CENTER));
                    no++;
                }

                document.add(table);
            }

            document.add(new Paragraph("Konfirmasi ijin survey mahasiswa kami dapat disampaikan pada "+namaDosen+" dengan no Hp. selaku Dosen Bidang Tugas Mata Kuliah Program Studi D4 Teknik Informatika Politeknik Negeri Jember.", contentFont));

            // Menambahkan jarak kosong antara paragraf "konfirmasi ijin ..." dan "atas perhatian ..."
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Atas perhatian dan kerjasamanya dalam ikut peningkatan keterampilan anak didik kami, diucapkan terima kasih.", contentFont));

            // Menambahkan tanda tangan
            document.add(new Paragraph(" "));
            // Pindah ke kanan align right dan rata kanan
            Paragraph paragraph1 = new Paragraph("a n Direktur                                   ", contentFont);
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Wakil Direktur Bidang Akademik", contentFont);
            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph2);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph paragraph3 = new Paragraph("Surateno, S.Kom, M.Kom           ", createBoldFont(contentFont));
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("NIP. 19790703 200312 1 001        ", createBoldFont(contentFont));
            paragraph4.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph4);

            // Menutup dokumen PDF
            document.close();

            Toast.makeText(this, "File PDF berhasil di-generate dan didownload", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal menggenerate dan mendownload file PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void DownloadPDFLL(String jenisSurat) {
        // Membuat dokumen PDF menggunakan iTextPDF
        Document document = new Document();

        // Mendapatkan direktori penyimpanan eksternal
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Membuat file PDF dengan nama yang unik
        String fileName = "Surat_" + jenis_surat + "-" + suratId + ".pdf";
        File file = new File(directory, fileName);

        try {
            // Membuat penulis PDF menggunakan PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // Membuat objek PdfPageEventHelper untuk menambahkan nomor halaman
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                private int totalPages;

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    // Membuat objek PdfContentByte untuk menggambar konten pada halaman PDF
                    PdfContentByte canvas = writer.getDirectContent();

                    // Membuat font untuk nomor halaman
                    Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

                    // Mengambil nomor halaman saat ini
                    int pageNumber = writer.getPageNumber();

                    // Menyusun teks dengan format "Halaman X/Y + "/" + totalPages"
                    Phrase pageNumberPhrase = new Phrase("Halaman " + pageNumber, pageNumberFont);

                    // Menggambar teks nomor halaman di pojok kanan bawah
                    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pageNumberPhrase, document.right(), document.bottom(), 0);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {
                    // Menyimpan jumlah halaman total saat dokumen ditutup
                    totalPages = writer.getPageNumber();
                }
            };

            // Mendaftarkan objek PdfPageEventHelper pada PdfWriter
            writer.setPageEvent(eventHelper);

            // Membuka dokumen PDF
            document.open();

            // Menambahkan judul surat ke dokumen PDF
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Surat " + jenisSurat, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Menambahkan tanggal dan nomor surat ke dokumen PDF
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm a");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(formattedDate), 40, 820, 0);

            // Menambahkan isi surat ke dokumen PDF
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph("Nomor  : "));
            document.add(new Paragraph("Perihal : " + isiKeterangan.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Kepada Yth.", createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiNamaMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiAlamatMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Di", createBoldFont(contentFont)));
            document.add(new Paragraph("      Tempat", createBoldFont(contentFont)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dengan hormat,", createBoldFont(contentFont)));
            document.add(new Paragraph("Sehubungan dengan adanya tugas mahasiswa maka bersama ini kami menugaskan mahasiswa Program Studi Teknik Informatika Jurusan Teknologi Informasi yang namanya tersebut di bawah ini untuk melakukan survey pada perusahaan/instansi yang Bapak/Ibu pimpin.", contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Adapun nama - nama anggota kelompok yang ditugaskan adalah sebagai berikut :", contentFont));

            // Menambahkan anggota ke dokumen PDF dalam bentuk tabel
            List<AnggotaDetailSurat> anggotaList = adapter.getAnggotaList();
            if (!anggotaList.isEmpty()) {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);
                table.setSpacingAfter(5f);

                // Menambahkan border pada seluruh sel di tabel
                table.getDefaultCell().setBorder(Rectangle.BOX);

                table.addCell(createTableCell("No.", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Nama Mahasiswa", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("NIM", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Jurusan / Prodi", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("No Tlp.", createBoldFont(contentFont), Element.ALIGN_CENTER));

                int no = 1;
                for (AnggotaDetailSurat anggota : anggotaList) {
                    table.addCell(createTableCell(String.valueOf(no), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNama(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNim(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getProdi().getKeterangan(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNo_hp(), contentFont, Element.ALIGN_CENTER));
                    no++;
                }

                document.add(table);
            }

            document.add(new Paragraph("Konfirmasi ijin survey mahasiswa kami dapat disampaikan pada "+namaDosen+" dengan no Hp. selaku Dosen Bidang Tugas Mata Kuliah Program Studi D4 Teknik Informatika Politeknik Negeri Jember.", contentFont));

            // Menambahkan jarak kosong antara paragraf "konfirmasi ijin ..." dan "atas perhatian ..."
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Atas perhatian dan kerjasamanya dalam ikut peningkatan keterampilan anak didik kami, diucapkan terima kasih.", contentFont));

            // Menambahkan tanda tangan
            document.add(new Paragraph(" "));
            // Pindah ke kanan align right dan rata kanan
            Paragraph paragraph1 = new Paragraph("a n Direktur                                   ", contentFont);
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Wakil Direktur Bidang Akademik", contentFont);
            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph2);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph paragraph3 = new Paragraph("Surateno, S.Kom, M.Kom           ", createBoldFont(contentFont));
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("NIP. 19790703 200312 1 001        ", createBoldFont(contentFont));
            paragraph4.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph4);

            // Menutup dokumen PDF
            document.close();

            Toast.makeText(this, "File PDF berhasil di-generate dan didownload", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal menggenerate dan mendownload file PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void DownloadPDFObservasi(String jenisSurat) {
        // Membuat dokumen PDF menggunakan iTextPDF
        Document document = new Document();

        // Mendapatkan direktori penyimpanan eksternal
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Membuat file PDF dengan nama yang unik
        String fileName = "Surat_" + jenis_surat + "-" + suratId + ".pdf";
        File file = new File(directory, fileName);

        try {
            // Membuat penulis PDF menggunakan PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // Membuat objek PdfPageEventHelper untuk menambahkan nomor halaman
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                private int totalPages;

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    // Membuat objek PdfContentByte untuk menggambar konten pada halaman PDF
                    PdfContentByte canvas = writer.getDirectContent();

                    // Membuat font untuk nomor halaman
                    Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

                    // Mengambil nomor halaman saat ini
                    int pageNumber = writer.getPageNumber();

                    // Menyusun teks dengan format "Halaman X/Y + "/" + totalPages"
                    Phrase pageNumberPhrase = new Phrase("Halaman " + pageNumber, pageNumberFont);

                    // Menggambar teks nomor halaman di pojok kanan bawah
                    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pageNumberPhrase, document.right(), document.bottom(), 0);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {
                    // Menyimpan jumlah halaman total saat dokumen ditutup
                    totalPages = writer.getPageNumber();
                }
            };

            // Mendaftarkan objek PdfPageEventHelper pada PdfWriter
            writer.setPageEvent(eventHelper);

            // Membuka dokumen PDF
            document.open();

            // Menambahkan judul surat ke dokumen PDF
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Surat " + jenisSurat, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Menambahkan tanggal dan nomor surat ke dokumen PDF
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm a");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(formattedDate), 40, 820, 0);

            // Menambahkan isi surat ke dokumen PDF
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph("Nomor  : "));
            document.add(new Paragraph("Perihal : " + isiKeterangan.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Kepada Yth.", createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiNamaMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiAlamatMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Di", createBoldFont(contentFont)));
            document.add(new Paragraph("      Tempat", createBoldFont(contentFont)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dengan hormat,", createBoldFont(contentFont)));
            document.add(new Paragraph("Sehubungan dengan adanya tugas mahasiswa maka bersama ini kami menugaskan mahasiswa Program Studi Teknik Informatika Jurusan Teknologi Informasi yang namanya tersebut di bawah ini untuk melakukan survey pada perusahaan/instansi yang Bapak/Ibu pimpin.", contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Adapun nama - nama anggota kelompok yang ditugaskan adalah sebagai berikut :", contentFont));

            // Menambahkan anggota ke dokumen PDF dalam bentuk tabel
            List<AnggotaDetailSurat> anggotaList = adapter.getAnggotaList();
            if (!anggotaList.isEmpty()) {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);
                table.setSpacingAfter(5f);

                // Menambahkan border pada seluruh sel di tabel
                table.getDefaultCell().setBorder(Rectangle.BOX);

                table.addCell(createTableCell("No.", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Nama Mahasiswa", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("NIM", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Jurusan / Prodi", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("No Tlp.", createBoldFont(contentFont), Element.ALIGN_CENTER));

                int no = 1;
                for (AnggotaDetailSurat anggota : anggotaList) {
                    table.addCell(createTableCell(String.valueOf(no), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNama(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNim(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getProdi().getKeterangan(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNo_hp(), contentFont, Element.ALIGN_CENTER));
                    no++;
                }

                document.add(table);
            }

            document.add(new Paragraph("Konfirmasi ijin survey mahasiswa kami dapat disampaikan pada "+namaKoordinator+" dengan no Hp. "+noHpKoordinator+" selaku Dosen Bidang Tugas Mata Kuliah Program Studi D4 Teknik Informatika Politeknik Negeri Jember.", contentFont));

            // Menambahkan jarak kosong antara paragraf "konfirmasi ijin ..." dan "atas perhatian ..."
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Atas perhatian dan kerjasamanya dalam ikut peningkatan keterampilan anak didik kami, diucapkan terima kasih.", contentFont));

            // Menambahkan tanda tangan
            document.add(new Paragraph(" "));
            // Pindah ke kanan align right dan rata kanan
            Paragraph paragraph1 = new Paragraph("a n Direktur                                   ", contentFont);
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Wakil Direktur Bidang Akademik", contentFont);
            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph2);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph paragraph3 = new Paragraph("Surateno, S.Kom, M.Kom           ", createBoldFont(contentFont));
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("NIP. 19790703 200312 1 001        ", createBoldFont(contentFont));
            paragraph4.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph4);

            // Menutup dokumen PDF
            document.close();

            Toast.makeText(this, "File PDF berhasil di-generate dan didownload", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal menggenerate dan mendownload file PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void DownloadPDFPKL(String jenisSurat) {
        // Membuat dokumen PDF menggunakan iTextPDF
        Document document = new Document();

        // Mendapatkan direktori penyimpanan eksternal
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Membuat file PDF dengan nama yang unik
        String fileName = "Surat_" + jenis_surat + "-" + suratId + ".pdf";
        File file = new File(directory, fileName);

        try {
            // Membuat penulis PDF menggunakan PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // Membuat objek PdfPageEventHelper untuk menambahkan nomor halaman
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                private int totalPages;

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    // Membuat objek PdfContentByte untuk menggambar konten pada halaman PDF
                    PdfContentByte canvas = writer.getDirectContent();

                    // Membuat font untuk nomor halaman
                    Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

                    // Mengambil nomor halaman saat ini
                    int pageNumber = writer.getPageNumber();

                    // Menyusun teks dengan format "Halaman X/Y + "/" + totalPages"
                    Phrase pageNumberPhrase = new Phrase("Halaman " + pageNumber, pageNumberFont);

                    // Menggambar teks nomor halaman di pojok kanan bawah
                    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pageNumberPhrase, document.right(), document.bottom(), 0);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {
                    // Menyimpan jumlah halaman total saat dokumen ditutup
                    totalPages = writer.getPageNumber();
                }
            };

            // Mendaftarkan objek PdfPageEventHelper pada PdfWriter
            writer.setPageEvent(eventHelper);

            // Membuka dokumen PDF
            document.open();

            // Menambahkan judul surat ke dokumen PDF
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Surat " + jenisSurat, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Menambahkan tanggal dan nomor surat ke dokumen PDF
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm a");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(formattedDate), 40, 820, 0);

            // Menambahkan isi surat ke dokumen PDF
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph("Nomor   : "));
            document.add(new Paragraph("Lampiran : 1 (satu) bendel"));
            document.add(new Paragraph("Perihal  : " + isiKeterangan.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Kepada Yth.", createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiNamaMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiAlamatMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Di", createBoldFont(contentFont)));
            document.add(new Paragraph("      Tempat", createBoldFont(contentFont)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dalam rangka penyelenggaraan pendidikan Politeknik Negeri Jember yang berorientasi pada pendidikan vokasional, " +
                    "mahasiswa Politeknik Negeri Jember wajib melaksanakan Praktik Kerja Lapangan (PKL) di perusahaan/industri/instansi " +
                    "dan/atau strategic business unit lainnya selama 1 semester dengan bobot 20 SKS pada semester akhir sebagai salah satu " +
                    "syarat kelulusan.", contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Sehubungan dengan hal tersebut mohon dapatnya Bapak/Ibu berkenan mengijinkan beberapa mahasiswa kami dari Jurusan " +
                    "Teknologi Informasi Program Studi  guna melaksanakan Praktik Kerja Lapang (PKL) di " +
                    "perusahaan yang Bapak/Ibu pimpin dari tanggal "+tglPelaksanaan+" sampai "+tglSelesai+" dengan materi yang akan " +
                    "dipelajari sesuai disiplin ilmu diperoleh (sesuai dengan Curriculum Vitae).", contentFont));
            document.add(new Paragraph("Adapun nama - nama anggota kelompok yang ditugaskan adalah sebagai berikut :", contentFont));

            // Menambahkan anggota ke dokumen PDF dalam bentuk tabel
            List<AnggotaDetailSurat> anggotaList = adapter.getAnggotaList();
            if (!anggotaList.isEmpty()) {
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);
                table.setSpacingAfter(5f);

                // Menambahkan border pada seluruh sel di tabel
                table.getDefaultCell().setBorder(Rectangle.BOX);

                table.addCell(createTableCell("No.", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Nama Mahasiswa", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("NIM", createBoldFont(contentFont), Element.ALIGN_CENTER));

                int no = 1;
                for (AnggotaDetailSurat anggota : anggotaList) {
                    table.addCell(createTableCell(String.valueOf(no), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNama(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNim(), contentFont, Element.ALIGN_CENTER));
                    no++;
                }

                document.add(table);
            }

            document.add(new Paragraph("Konfirmasi kesediaan Bapak/Ibu untuk menerima Program PKL mahasiswa kami dapat disampaikan pada "+namaKoordinator+" dengan No. Hp. "+noHpKoordinator+" selaku Koordinator Bidang PKL Program Studi D4 Teknik Informatika " +
                    "Politeknik Negeri Jember.", contentFont));

            // Menambahkan jarak kosong antara paragraf "konfirmasi ijin ..." dan "atas perhatian ..."
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Demikian atas kebijakan dan kerjasama yang baik dari Bapak/Ibu dalam turut serta menunjang peningkatan keterampilan anak didik kami, diucapkan terima kasih.", contentFont));

            // Menambahkan tanda tangan
            document.add(new Paragraph(" "));
            // Pindah ke kanan align right dan rata kanan
            Paragraph paragraph1 = new Paragraph("a n Direktur                                   ", contentFont);
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Wakil Direktur Bidang Akademik", contentFont);
            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph2);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph paragraph3 = new Paragraph("Surateno, S.Kom, M.Kom           ", createBoldFont(contentFont));
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("NIP. 19790703 200312 1 001        ", createBoldFont(contentFont));
            paragraph4.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph4);

            // Menutup dokumen PDF
            document.close();

            Toast.makeText(this, "File PDF berhasil di-generate dan didownload", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal menggenerate dan mendownload file PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void DownloadPDFTA(String jenisSurat) {
        // Membuat dokumen PDF menggunakan iTextPDF
        Document document = new Document();

        // Mendapatkan direktori penyimpanan eksternal
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Membuat file PDF dengan nama yang unik
        String fileName = "Surat_" + jenis_surat + "-" + suratId + ".pdf";
        File file = new File(directory, fileName);

        try {
            // Membuat penulis PDF menggunakan PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // Membuat objek PdfPageEventHelper untuk menambahkan nomor halaman
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                private int totalPages;

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    // Membuat objek PdfContentByte untuk menggambar konten pada halaman PDF
                    PdfContentByte canvas = writer.getDirectContent();

                    // Membuat font untuk nomor halaman
                    Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

                    // Mengambil nomor halaman saat ini
                    int pageNumber = writer.getPageNumber();

                    // Menyusun teks dengan format "Halaman X/Y + "/" + totalPages"
                    Phrase pageNumberPhrase = new Phrase("Halaman " + pageNumber, pageNumberFont);

                    // Menggambar teks nomor halaman di pojok kanan bawah
                    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pageNumberPhrase, document.right(), document.bottom(), 0);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {
                    // Menyimpan jumlah halaman total saat dokumen ditutup
                    totalPages = writer.getPageNumber();
                }
            };

            // Mendaftarkan objek PdfPageEventHelper pada PdfWriter
            writer.setPageEvent(eventHelper);

            // Membuka dokumen PDF
            document.open();

            // Menambahkan judul surat ke dokumen PDF
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Surat " + jenisSurat, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Menambahkan tanggal dan nomor surat ke dokumen PDF
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm a");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(formattedDate), 40, 820, 0);

            // Menambahkan isi surat ke dokumen PDF
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph("Nomor   : "));
            document.add(new Paragraph("Perihal  : " + isiKeterangan.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Kepada Yth.", createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiNamaMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph(""+ isiAlamatMitra.getText().toString(), createBoldFont(contentFont)));
            document.add(new Paragraph("Di", createBoldFont(contentFont)));
            document.add(new Paragraph("      Tempat", createBoldFont(contentFont)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dalam rangka penyelenggaraan pendidikan Politeknik Negeri Jember yang berorientasi pada pendidikan profesional, " +
                    "mahasiswa wajib melaksanakan Tugas Akhir / Skripsi sebagai salah satu syarat kelulusan.", contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Sehubungan dengan hal tersebut mohon Bapak / Ibu berkenan mengijinkan mahasiswa kami dari Program Studi D4 " +
                    "Teknik Informatika melakukan Survei guna mendapatkan data dan informasi yang kompeten sesuai dengan bidang " +
                    "kajiannya di Instansi / perusahaan yang Bapak / Ibu pimpin.", contentFont));
            document.add(new Paragraph("Adapun nama mahasiswa yang dimaksud adalah :", contentFont));

            // Menambahkan anggota ke dokumen PDF dalam bentuk tabel
            List<AnggotaDetailSurat> anggotaList = adapter.getAnggotaList();
            if (!anggotaList.isEmpty()) {
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);
                table.setSpacingAfter(5f);

                // Menambahkan border pada seluruh sel di tabel
                table.getDefaultCell().setBorder(Rectangle.BOX);

                table.addCell(createTableCell("Nama Mahasiswa", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("NIM", createBoldFont(contentFont), Element.ALIGN_CENTER));
                table.addCell(createTableCell("Judul Skripsi", createBoldFont(contentFont), Element.ALIGN_CENTER));

//                int no = 1;
                for (AnggotaDetailSurat anggota : anggotaList) {
                    table.addCell(createTableCell(anggota.getNama(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(anggota.getNim(), contentFont, Element.ALIGN_CENTER));
                    table.addCell(createTableCell(judulTA, contentFont, Element.ALIGN_CENTER));
//                    no++;
                }

                document.add(table);
            }

            document.add(new Paragraph("Konfirmasi kesediaan Bapak/Ibu untuk menerima ijin survei mahasiswa kami dapat disampaikan pada "+namaKoordinator+" dengan No. Hp. "+noHpKoordinator+" selaku Koordinator Bidang Tugas Akhir/Skripsi Program Studi " +
                    "D4 Teknik Informatika Politeknik Negeri Jember.", contentFont));

            // Menambahkan jarak kosong antara paragraf "konfirmasi ijin ..." dan "atas perhatian ..."
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Demikian atas kebijakan dan kerjasama yang baik dari Bapak/Ibu dalam turut serta menunjang peningkatan keterampilan " +
                    "anak didik kami, diucapkan terima kasih.", contentFont));

            // Menambahkan tanda tangan
            document.add(new Paragraph(" "));
            // Pindah ke kanan align right dan rata kanan
            Paragraph paragraph1 = new Paragraph("a n Direktur                                   ", contentFont);
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Wakil Direktur Bidang Akademik", contentFont);
            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph2);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph paragraph3 = new Paragraph("Surateno, S.Kom, M.Kom           ", createBoldFont(contentFont));
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("NIP. 19790703 200312 1 001        ", createBoldFont(contentFont));
            paragraph4.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph4);

            // Menutup dokumen PDF
            document.close();

            Toast.makeText(this, "File PDF berhasil di-generate dan didownload", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal menggenerate dan mendownload file PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Font createBoldFont(Font font) {
        Font boldFont = new Font(font);
        boldFont.setStyle(Font.BOLD);
        return boldFont;
    }

    private PdfPCell createTableCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.BOX); // Menggunakan Rectangle.BOX untuk menambahkan border pada sel
        return cell;
    }

    private PdfPCell createTableCell(String content, Font font) {
        return createTableCell(content, font, Element.ALIGN_LEFT);
    }

    private String getToken() {
        MyPreferences preferences = new MyPreferences(this);
        return preferences.getString("token", "");
    }
    private void loadDetailSurat() {
        String token = getToken();
        DetailSuratService detailSuratService = ApiClient.getDetailSurats(this);
        Call<DetailSuratResponse> call = detailSuratService.getDetailSurat("Bearer " + token, suratId);
        call.enqueue(new Callback<DetailSuratResponse>() {
            @Override
            public void onResponse(Call<DetailSuratResponse> call, Response<DetailSuratResponse> response) {
                if (response.isSuccessful()) {
                    DetailSuratResponse detailSuratResponse = response.body();
                    if (detailSuratResponse != null && detailSuratResponse.isSuccess()) {
                        List<DetailSuratRequest> dataList = detailSuratResponse.getData();
                        if (dataList != null && !dataList.isEmpty()) {
                            DetailSuratRequest data = dataList.get(0);

                            // Set data detail surat ke komponen UI
                            if (data.getKoordinator().getNama()==null){
                                isiKoordinator.setText("-");
                                namaKoordinator = "-";
                            }else{
                                isiKoordinator.setText(data.getKoordinator().getNama());
                                namaKoordinator = data.getKoordinator().getNama();
                            }
                            if (data.getDosen().getNama() == null) {
                                isiNamaDosen.setText("-");
                                namaDosen = "-";
                            } else {
                                isiNamaDosen.setText(data.getDosen().getNama());
                                namaDosen = data.getDosen().getNama();
                            }
                            if (data.getKoordinator().getNo_hp() == null) {
                                noHpKoordinator = "-";
                            } else {
                                noHpKoordinator = data.getKoordinator().getNo_hp();
                            }
                            if (data.getAlasan_penolakan()==null){
                                isiAlasanPenolakan.setText("-");
                            }else{
                                isiAlasanPenolakan.setText(data.getAlasan_penolakan());
                            }
                            if (data.getJudul_ta()==null){
                                isiJudulTA.setText("-");
                                judulTA = "-";
                            }else{
                                isiJudulTA.setText(data.getJudul_ta());
                                judulTA = data.getJudul_ta();
                            }

                            isiNamaMitra.setText(data.getNama_mitra());
                            isiAlamatMitra.setText(data.getAlamat_mitra());
                            isiTanggalDibuat.setText(data.getTanggal_dibuat());
                            if (data.getTanggal_pelaksanaan()==null){
                                isiTanggalPelaksanaan.setText("-");
                                tglPelaksanaan = "-";
                            }else{
                                isiTanggalPelaksanaan.setText(data.getTanggal_pelaksanaan());
                                tglPelaksanaan = formatTanggal(data.getTanggal_pelaksanaan());
                            }
                            if (data.getTanggal_selesai()==null){
                                isiTanggalSelesai.setText("-");
                                tglSelesai = "-";
                            }else{
                                isiTanggalSelesai.setText(data.getTanggal_selesai());
                                tglSelesai = formatTanggal(data.getTanggal_selesai());
                            }
                            isiKebutuhan.setText(data.getKebutuhan());
                            isiKeterangan.setText(data.getKeterangan());
                            Intent intent = getIntent();
                            String namaMhs = intent.getStringExtra("keterangan");
                            isiStatus.setText(namaMhs);

                            // Set data anggota pengaju surat ke adapter
                            adapter.setAnggotaList(data.getAnggota());
                        } else {
                            // Tampilkan pesan error jika data tidak ada
                            Toast.makeText(DetailSurat.this, "Data detail surat tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Tampilkan pesan error jika respon tidak berhasil
                        Toast.makeText(DetailSurat.this, "Gagal memuat detail surat", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tampilkan pesan error jika gagal memuat respon
                    Toast.makeText(DetailSurat.this, "Gagal memuat respon", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSuratResponse> call, Throwable t) {
                // Tampilkan pesan error jika terjadi kesalahan jaringan
                Toast.makeText(DetailSurat.this, "Terjadi kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Kesalahan Jaringan", "Terjadi kesalahan jaringan", t);
            }
        });
    }
    private String formatTanggal(String tanggal) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(tanggal);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tanggal; // Jika terjadi kesalahan, kembalikan tanggal asli
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Mengatur fungsi tombol back pada appbar
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//                            if (data.getDosen().getNama() == null) {
//                                isiNamaDosen.setText("-");
//                                namaDosen = "-";
//                            } else {
//                                isiNamaDosen.setText(data.getDosen().getNama());
//                                namaDosen = data.getDosen().getNama();
//                            }
//                            if (data.getKoordinator().getNo_hp() == null) {
//                                noHpKoordinator = "-";
//                            } else {
//                                noHpKoordinator = data.getKoordinator().getNo_hp();
//                            }
//                            if (data.getDosen().getProdi_id().getNote() == null) {
//                                programStudiDosen = "-";
//                            } else {
//                                programStudiDosen = data.getDosen().getProdi_id().getNote();
//                            }
//                            if (data.getKoordinator().getProdi_id().getNote() == null) {
//                                programStudiKoordinator = "-";
//                            } else {
//                                programStudiKoordinator = data.getKoordinator().getProdi_id().getNote();
//                            }