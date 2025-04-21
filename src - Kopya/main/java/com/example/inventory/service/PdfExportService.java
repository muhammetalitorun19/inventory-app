package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfExportService {

    public ByteArrayInputStream export(List<Product> products) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(14);
            Paragraph title = new Paragraph("Ürün Listesi", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // boşluk

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 4, 3, 3});

            addTableHeader(table);
            addRows(table, products);

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("PDF oluşturulamadı", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Ürün Adı", "Açıklama", "Fiyat", "Stok")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Product> products) {
        for (Product p : products) {
            table.addCell(p.getName());
            table.addCell(p.getDescription());
            table.addCell(String.valueOf(p.getPrice()));
            table.addCell(String.valueOf(p.getStock()));
        }
    }
}

