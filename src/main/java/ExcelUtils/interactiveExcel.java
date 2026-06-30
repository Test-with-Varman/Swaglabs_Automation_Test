package ExcelUtils;

import java.io.FileInputStream;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class interactiveExcel {

    public static String[] getInteractiveUserData() throws Throwable {
        
        String excelPath = System.getProperty("user.dir") + "\\src\\main\\java\\ExcelUtils\\swag.xlsx";
        FileInputStream excel = new FileInputStream(excelPath);

        Workbook wb = WorkbookFactory.create(excel);
        Sheet sheet = wb.getSheet("usercreds");
        DataFormatter formatter = new DataFormatter();

        int lastrownum1 = sheet.getLastRowNum();
        
        String universalPassword = formatter.formatCellValue(sheet.getRow(1).getCell(1));

        System.out.println("========== USER SELECTION MENU ==========");
        for (int i = 1; i <= lastrownum1; i++) {
            String tempUser = formatter.formatCellValue(sheet.getRow(i).getCell(0));
            System.out.println(i + " = " + tempUser);
        }
        System.out.println("=========================================");

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of the user you want to test: ");
        int choice = scan.nextInt();

        if(choice < 1 || choice > lastrownum1) {
            System.out.println("Invalid choice! Defaulting to User 1.");
            choice = 1;
        }

        String selectedUsername = formatter.formatCellValue(sheet.getRow(choice).getCell(0));
        
        System.out.println(">>> Executing Test for: " + selectedUsername);

        wb.close();
        excel.close();
        
        return new String[] {selectedUsername, universalPassword};
    }
}