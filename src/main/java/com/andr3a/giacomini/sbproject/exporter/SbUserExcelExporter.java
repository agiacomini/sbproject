package com.andr3a.giacomini.sbproject.exporter;

import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SbUserExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<SbUser> listSbUser;

    public SbUserExcelExporter(List<SbUser> listSbUser){
        this.listSbUser = listSbUser;
        workbook = new XSSFWorkbook();
    }

    // Create HEADER of the sheet
    private void writeHeaderLine(){
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);
        row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());

        CellStyle headerCellStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(16);
//        headerFont.setFontName("Calibri");
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create Cell Header
        createCell(row, 0, "User ID", headerCellStyle);
        createCell(row, 1, "First Name", headerCellStyle);
        createCell(row, 2, "Last Name", headerCellStyle);
        createCell(row, 3, "Enabled", headerCellStyle);
        createCell(row, 4, "Last Login Date", headerCellStyle);
        createCell(row, 5, "Email", headerCellStyle);
        createCell(row, 6, "Group Name", headerCellStyle);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle cellStyle){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if(value instanceof LocalDateTime){
            cell.setCellValue(((LocalDateTime) value).format( DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") ));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    // Write the content of the table
    private void writeDataLine(){
        int rowCount = 1;

        CellStyle dataCellStyle = workbook.createCellStyle();
        XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataFont.setFontName("Calibri");
        dataCellStyle.setFont(dataFont);

        for(SbUser sbUser : listSbUser){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, sbUser.getId().toString(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getFirstName(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getLastName(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getEnabled(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getLastLoginDate(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getEmail(), dataCellStyle);
            createCell(row, columnCount++, sbUser.getSbGroup().getGroupName(), dataCellStyle);
        }
    }

    // Write the file
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}