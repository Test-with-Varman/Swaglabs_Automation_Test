package ExcelUtils;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	public static Object[][] getTestData() throws Throwable {

		String excelPath = System.getProperty("user.dir") + "\\src\\main\\java\\ExcelUtils\\swag.xlsx";
		FileInputStream excel = new FileInputStream(excelPath);

		Workbook wb = WorkbookFactory.create(excel);
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

		wb.close();
		excel.close();

		return data;
	}
}