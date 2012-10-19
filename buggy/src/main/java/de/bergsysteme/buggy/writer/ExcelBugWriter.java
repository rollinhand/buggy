package de.bergsysteme.buggy.writer;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import de.bergsysteme.buggy.resolve.Field;

public class ExcelBugWriter extends BugWriter {
	private Workbook workbook;
	private Sheet activeSheet;
	
	public ExcelBugWriter(OutputStream out) {
		super(out);
		workbook = new HSSFWorkbook();
	}

	@Override
	public void write(String[] fieldnames, Object[] values) throws IOException {
		write(null, fieldnames, values);
	}
	
	public void write(String sheetName, String[] fieldnames, Object[] values) throws IOException {
		// Create a new sheet.
		// TODO If sheet empty, construct a symbolic name.
		String safeName = WorkbookUtil.createSafeSheetName(sheetName); 
		activeSheet = workbook.createSheet(safeName);
		// First row (0) contains the translated column names.
		writeHeader(fieldnames);
		// Following rows contain the body or workload.
		writeBody(fieldnames, values);
		// After the model is created in memory write it down.
		workbook.write(out);
	}

	private void writeHeader(String[] fieldnames) {
		Row row = activeSheet.createRow(0);
		for (int index = 0; index < fieldnames.length; ++index) {
			Cell cell = row.createCell(index);
			String name = Field.translate(fieldnames[index]);
			cell.setCellValue(name);
		}
	}
	
	private void writeBody(String[] fieldnames, Object[] values) {
		int rowIndex = 1;
		
		for (Object data : values) {
			Row row = activeSheet.createRow(rowIndex++);
			for (int index = 0; index < fieldnames.length; ++index) {
				Cell cell = row.createCell(index);
				String value = getValue(fieldnames[index], data);
				cell.setCellValue(value);
			}
		}
	}
}
