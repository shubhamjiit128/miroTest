/**
 * 
 */
package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

public class ExcelUtils {
	public static String ExcelPath;
	static File file;
	static FileInputStream ExcelFile;

	// Main Directory of the project
	public static final String currentDir = System.getProperty("user.dir");

	public static String excelfileName = null;
	
	// Location of Test data excel file
	public static String testDataExcelPath = null;

	// Excel WorkBook
	private static Workbook excelWBook;

	// Excel Sheet
	private static Sheet excelWSheet;

	// Excel cell
	private static Cell cell;

	// Excel row
	private static Row row;

	// Row Number
	public static int rowNumber;

	// Column Number
	public static int columnNumber;

	// Setter and Getters of row and columns
	public static void setRowNumber(int pRowNumber) {
		rowNumber = pRowNumber;
	}

	public static int getRowNumber() {
		return rowNumber;
	}

	public static void setColumnNumber(int pColumnNumber) {
		columnNumber = pColumnNumber;
	}

	public static int getColumnNumber() {
		return columnNumber;
	}


	/**
	 * @author srajput
	 * @description This method is to read excel data based on passed header
	 *              value.
	 * @param RowNum
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getTestData(String sheetName, String columnName)  {
		ArrayList<String> testdata = new ArrayList<String>();
		try {
			if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
				testDataExcelPath = currentDir + "//input//" + Constants.excelfileName;
			} else if (Platform.getCurrent().toString().contains("WIN")) {
				testDataExcelPath = currentDir + "\\input\\" +Constants.excelfileName;
			}
			FileInputStream fis = new FileInputStream(testDataExcelPath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row = sheet.getRow(0);
 			int col_num = -1;
			int count = 1;
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(columnName))
					col_num = i;
			}
			XSSFCell cell = row.getCell(col_num);
			row = sheet.getRow(count);
			cell = row.getCell(col_num);

			while (cell.getStringCellValue() != "") {
				String value = cell.getStringCellValue();
				testdata.add(value);
				count++;
				row = sheet.getRow(count);
				cell = row.getCell(col_num);
			}
			return testdata;
		} catch (IllegalArgumentException e1) {
			throw (e1);
		}
		catch (NullPointerException e) {
			return testdata;
		}
		catch (IOException e) {
			return testdata;
		}
		catch (Exception e) {
			throw (e);
		}
	}
	

}
