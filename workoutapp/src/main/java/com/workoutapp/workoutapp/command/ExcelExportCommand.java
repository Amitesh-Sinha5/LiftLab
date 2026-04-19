package com.workoutapp.workoutapp.command;

import com.workoutapp.workoutapp.model.BodyMeasurement;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

/**
 * Command Pattern (Behavioural) — Concrete Command.
 *
 * Encapsulates the entire body-measurement Excel export operation.
 * ProgressController (the Invoker) calls execute() without knowing
 * anything about POI, content-type headers, or column layout.
 */
public class ExcelExportCommand implements ExportCommand {

    private static final String[] HEADERS = {
        "Date", "Weight (kg)", "Height (m)",
        "Chest (cm)", "Waist (cm)", "Biceps (cm)", "BMI"
    };

    private final List<BodyMeasurement> measurements;

    public ExcelExportCommand(List<BodyMeasurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public void execute(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=body-measurements.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Body Measurements");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            int rowNum = 1;
            for (BodyMeasurement m : measurements) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(m.getDate().toString());
                row.createCell(1).setCellValue(m.getWeight());
                row.createCell(2).setCellValue(m.getHeight());
                row.createCell(3).setCellValue(m.getChest());
                row.createCell(4).setCellValue(m.getWaist());
                row.createCell(5).setCellValue(m.getBiceps());
                Double bmi = m.getBmi();
                if (bmi != null) {
                    row.createCell(6).setCellValue(bmi);
                } else {
                    row.createCell(6).setCellValue("");
                }
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }
}
