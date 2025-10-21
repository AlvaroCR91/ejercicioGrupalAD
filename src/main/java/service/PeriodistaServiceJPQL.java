package service;

import com.itextpdf.text.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Periodista;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.text.*;
import java.io.FileOutputStream;
import java.util.List;

public class PeriodistaServiceJPQL {
    private EntityManagerFactory emf;

    public PeriodistaServiceJPQL(){
        emf = Persistence.createEntityManagerFactory("PeriodistaPU");
    }

    public void CrearInformeNingunArticulo(String rutaSalida){
        EntityManager em = emf.createEntityManager();

        try{
            TypedQuery<Periodista> query = em.createQuery("SELECT p FROM Periodista p LEFT JOIN p.articulos a GROUP BY p.nombre, p.dni, p.numTel HAVING COUNT(a) = 0 ORDER BY p.nombre", Periodista.class);
            List<Periodista> periodistas = query.getResultList();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
            document.open();

            Font tituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Paragraph tituloParagraph = new Paragraph(
                    "Informe de periodistas sin Articulos", tituloFont
            );

            tituloParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            tituloParagraph.setSpacingAfter(20);
            document.add(tituloParagraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(90);
            float[] widths = new float[3];
            widths[0] = 3;
            widths[1] = 2;
            widths[2] = 2;
            table.setWidths(widths);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("Nombre", headerFont)));
            table.addCell(new PdfPCell(new Phrase("DNI", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Telefono", headerFont)));

            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            for(Periodista periodista : periodistas){
                table.addCell(new PdfPCell(new Phrase(periodista.getNombre(), bodyFont)));
                table.addCell(new PdfPCell(new Phrase(periodista.getDni(), bodyFont)));
                table.addCell(new PdfPCell(new Phrase(periodista.getNumTel(), bodyFont)));
            }

            document.add(table);
            document.close();

            System.out.println("Informe generado correctamente en: " + rutaSalida);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void CrearInformeDosArticulos(String rutaSalida){
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT p.nombre, p.dni, p.numTel, COUNT(a) FROM Periodista p LEFT JOIN p.articulos a GROUP BY p.nombre, p.dni, p.numTel HAVING COUNT(a) >= 2 ORDER BY COUNT(a) DESC", Object[].class);
            List<Object[]> periodistas = query.getResultList();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
            document.open();

            Font tituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Paragraph tituloParagraph = new Paragraph(
                    "Informe de periodistas sin Articulos", tituloFont
            );

            tituloParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            tituloParagraph.setSpacingAfter(20);
            document.add(tituloParagraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(90);
            float[] widths = new float[4];
            widths[0] = 3;
            widths[1] = 2;
            widths[2] = 2;
            widths[3] = 2;
            table.setWidths(widths);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("Nombre", headerFont)));
            table.addCell(new PdfPCell(new Phrase("DNI", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Telefono", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Nº Articulos", headerFont)));

            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            for(Object[] periodista : periodistas){
                table.addCell(new PdfPCell(new Phrase((String) periodista[0], bodyFont)));
                table.addCell(new PdfPCell(new Phrase((String) periodista[1], bodyFont)));
                table.addCell(new PdfPCell(new Phrase((String) periodista[2], bodyFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf((long) periodista[3]), bodyFont)));
            }

            document.add(table);
            document.close();

            System.out.println("Informe generado correctamente en: " + rutaSalida);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
