package ExcelUtils;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	public static Object[][] getTestData() throws Throwable {

		// Try to load from classpath first (recommended location: src/test/resources/swag.xlsx)
		Workbook wb = null;
		java.io.InputStream excelStream = ExcelUtils.class.getClassLoader().getResourceAsStream("swag.xlsx");
		java.io.FileInputStream fileStream = null;

		if (excelStream != null) {
			wb = WorkbookFactory.create(excelStream);
		} else {
			// Fallback to original path for backward compatibility
			String excelPath = System.getProperty("user.dir") + "\\src\\main\\java\\ExcelUtils\\swag.xlsx";
			fileStream = new java.io.FileInputStream(excelPath);
			wb = WorkbookFactory.create(fileStream);
		}
		Sheet sheet = wb.getSheet("usercreds");

		int lastrownum1 = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();

		Object[][] data = new Object[lastrownum1][colCount];

		DataFormatter formatter = new DataFormatter();
		String universalPassword = formatter.formatCellValue(sheet.getRow(1).getCell(1));

		for (int i = 1; i <= lastrownum1; i++) {

			String username = formatter.formatCellValue(sheet.getRow(i).getCell(0));

			data[i - 1][0] = username;

			data[i - 1][1] = universalPassword;
		}

		if (wb != null) {
			wb.close();
		}
		if (fileStream != null) {
			fileStream.close();
		}

		return data;
	}
}