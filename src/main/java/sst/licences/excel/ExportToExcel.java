package sst.licences.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sst.licences.container.LicencesContainer;
import sst.licences.model.Membre;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
public abstract class ExportToExcel implements ExcelExporter {

    public void export() throws IOException {
        List<Membre> membres = membres();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Membres");

        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        addHeader(row, style);

        for (Membre membre : membres) {
            rownum++;
            row = sheet.createRow(rownum);
            int i = 0;

            // Nom
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getNom());
            // Prénom
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getPrenom());
            // Rue
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getRue());
            // Numéro
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getNum());
            // Code Postal
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getCodePostal());
            // Localité
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getLocalite());
            // Téléphone
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getTelephone());
            // GSM
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getGsm());
            // Email
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getEmail().getAdresse());
            // Date de Naissance
            cell = row.createCell(i++, CellType.STRING);
            LocalDate dateDeNaissance = membre.getDateDeNaissance();
            cell.setCellValue(dateDeNaissance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            // Pays
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(membre.getCodePays());
            // Langue
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(membre.getLangue());

            updateMembre(membre);
        }

        toFile(fileBaseName(), workbook);
    }

    private void toFile(String baseName, XSSFWorkbook workbook) throws IOException {
        File file = new File(filename(baseName));

        try (FileOutputStream outFile = new FileOutputStream(file)) {
            workbook.write(outFile);
        }
        log.info("Created file: " + file.getAbsolutePath());

        if (saveMembres()) {
            LicencesContainer.me().save();
        }
    }

    private String filename(String baseName) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd.HH.mm.ss"));
        return baseName.replace("{date}", formattedDate);
    }

    private void addHeader(Row row, XSSFCellStyle style) {
        Cell cell;
        int i = 0;
        // Nom
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Nom");
        cell.setCellStyle(style);
        // Prénom
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Prénom");
        cell.setCellStyle(style);
        // Rue
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Rue");
        cell.setCellStyle(style);
        // Numéro
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("nummer");
        cell.setCellStyle(style);
        // Code Postal
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Code postal");
        cell.setCellStyle(style);
        // Localité
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Commune");
        cell.setCellStyle(style);
        // Téléphone
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Téléphone");
        cell.setCellStyle(style);
        // Portable
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Portable");
        cell.setCellStyle(style);
        // Email
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Email");
        cell.setCellStyle(style);
        // Date de naissance
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Date de naissance");
        cell.setCellStyle(style);
        // Code pays
        cell = row.createCell(i++, CellType.STRING);
        cell.setCellValue("Code pays");
        cell.setCellStyle(style);
        // Langue
        cell = row.createCell(i, CellType.STRING);
        cell.setCellValue("Langue");
        cell.setCellStyle(style);
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
