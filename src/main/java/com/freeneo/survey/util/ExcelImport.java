package com.freeneo.survey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.freeneo.survey.domain.Customer;

public class ExcelImport {

	public List excelImport(HttpServletRequest request) {

		// 현재시간 설정
		// Calendar calendar = Calendar.getInstance();

		File tempFile = null;
		// File file = null;
		// PrintStream stream = null;

		/** 등록할 target 목록 */
		ArrayList customers = new ArrayList<Customer>();

		try {
			String rootPath = (request.getSession().getServletContext()
					.getRealPath("/")).replace("\\", "/");
			String savePath = rootPath;// + File.separator;

			// 파일이 저장된 실제 경로 + 파일명 찾기

			// Apache POI 사용 시
			// Apache POI (http://poi.apache.org/) - the Java API for Microsoft
			// Documents
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mFile = multipartRequest.getFile("excel");

			if (mFile != null && mFile.getSize() > 0) {
				String saveFileName = mFile.getOriginalFilename();
				long fileSize = mFile.getSize();

				if (fileSize > 0 && !saveFileName.equals("")) {
					saveFileName = savePath + saveFileName;

					tempFile = new File(saveFileName);
					OutputStream outputStream = new FileOutputStream(tempFile);
					FileCopyUtils.copy(mFile.getInputStream(), outputStream);
					outputStream.close();

					// 엑셀파읽읽기
					// Excel 2007(.xlsx) 이상 파일처리
					if (saveFileName.indexOf(".xlsx") > -1
							|| saveFileName.indexOf(".XLSX") > -1) {
						ArrayList cellGridXssf = new ArrayList();
						FileInputStream myInput = new FileInputStream(
								saveFileName);
						Workbook wb = WorkbookFactory.create(myInput);

						Sheet sheet = wb.getSheetAt(0);
						Row firstRow = sheet.getRow(sheet.getFirstRowNum());
						int cellSize = firstRow.getLastCellNum();
						for (Row row : sheet) {
							ArrayList<XSSFCell> cellRowList = new ArrayList<XSSFCell>();
							for (int cn = 0; cn < cellSize; cn++) {
								// If the cell is missing from the file,
								// generate a blank one
								// (Works by specifying a MissingCellPolicy)
								XSSFCell myCell = (XSSFCell) row.getCell(cn,
										Row.CREATE_NULL_AS_BLANK);
								// Print the cell for debugging
								// System.out.println("CELL: " + cn + " --> " +
								// myCell.toString());
								cellRowList.add(myCell);
							}
							cellGridXssf.add(cellRowList);
						}

						// XSSFSheet mySheet = (XSSFSheet) wb.getSheetAt(0);
						// Iterator<?> rowIter = mySheet.rowIterator();
						//
						// while (rowIter.hasNext()) {
						// XSSFRow myRow = (XSSFRow) rowIter.next();
						// Iterator<?> cellIter = myRow.cellIterator();
						// List<XSSFCell> cellRowList = new
						// ArrayList<XSSFCell>();
						// while (cellIter.hasNext()) { //Cell에 공백이 있을 경우 읽지 못하는
						// 문제 발생
						// XSSFCell myCell = (XSSFCell) cellIter.next();
						// cellRowList.add(myCell);
						// }
						// cellGridXssf.add(cellRowList);
						// }

						// Validation Check
						/*
						 * if (checkXlsx(cellGridXssf)) { throw new
						 * Exception(getMessage()); }
						 */

						// Text로 변환
						/*
						 * file = new File(savePath + "test.txt"); stream = new
						 * PrintStream(file); String stringCellValue = null;
						 */

						// 엑셀파일 기준 1 행부터 데이터 설정
						for (int i = 0; i < cellGridXssf.size(); i++) {
							ArrayList<XSSFCell> cellRowList = (ArrayList<XSSFCell>) cellGridXssf
									.get(i);

							// for (int j = 0; j < cellRowList.size(); j++) {
							// XSSFCell myCell = (XSSFCell) cellRowList.get(j);

							// 첫번째 Cell의 값(AWS_ID)을 목록에 저장한다.
							/*
							 * if (j == 0) { Target awsModel = new Target();
							 * awsModel.setAWS_ID(myCell
							 * .getRichStringCellValue() .getString());
							 * System.out.println("awsModel.getAWS_ID() : " +
							 * awsModel.getAWS_ID()); awsList.add(awsModel); }
							 */

							// 셀의 값을 target에 저장한다.
							Customer customer = new Customer();
							XSSFCell myCell = (XSSFCell) cellRowList.get(0);
							customer.setEtc01(myCell.getRichStringCellValue()
									.getString());
							myCell = (XSSFCell) cellRowList.get(1);
							customer.setHp(myCell.getRichStringCellValue()
									.getString());

							customers.add(customer);

							/*
							 * switch (myCell.getCellType()) { case
							 * XSSFCell.CELL_TYPE_STRING: if
							 * (myCell.getRichStringCellValue
							 * ().getString().equals(null) ||
							 * myCell.getRichStringCellValue()
							 * .getString().equals("null")) { stringCellValue =
							 * "\\N"; } else { stringCellValue = myCell
							 * .getRichStringCellValue() .getString(); } break;
							 * case XSSFCell.CELL_TYPE_NUMERIC: if
							 * (DateUtil.isCellDateFormatted(myCell)) {
							 * System.out.println("CELL_TYPE_NUMERIC++DATA===" +
							 * myCell.getDateCellValue()); SimpleDateFormat
							 * formatter = new SimpleDateFormat( "yyyy-MM-dd");
							 * stringCellValue = formatter
							 * .format(myCell.getDateCellValue()); } else {
							 * stringCellValue = String.valueOf(myCell
							 * .getNumericCellValue()); } break; case
							 * XSSFCell.CELL_TYPE_FORMULA:
							 * System.out.println("CELL_TYPE_FORMULA===" +
							 * myCell.getCellFormula()); stringCellValue =
							 * myCell.getCellFormula(); break; case
							 * XSSFCell.CELL_TYPE_BOOLEAN:
							 * myCell.getBooleanCellValue(); break; case
							 * XSSFCell.CELL_TYPE_ERROR: stringCellValue = "" +
							 * myCell.getErrorCellValue(); break; case
							 * XSSFCell.CELL_TYPE_BLANK: stringCellValue = "";
							 * break; default: break; }// switch
							 */
							// stream.print(stringCellValue + ";");
						}// for cellRowList

						/*
						 * // 사용여부 설정 stream.print("Y" + ";"); // 생성자 ID 설정
						 * stream.print(userId + ";"); // 생성시간 설정
						 * stream.print(calendar.getTime() + ";"); // 수정자 ID 설정
						 * stream.print(userId + ";"); // 수정시간 설정
						 * stream.print(calendar.getTime() + ";");
						 * 
						 * stream.println("");
						 */

					}// for cellGridXssf

					// Excel 2007(.xls) 이하 파일처리
				} else if (saveFileName.indexOf(".xls") > -1
						|| saveFileName.indexOf(".XLS") > -1) {
					ArrayList cellGridHssf = new ArrayList();
					FileInputStream myInput = new FileInputStream(saveFileName);
					POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
					HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

					Sheet sheet = myWorkBook.getSheetAt(0);
					Row firstRow = sheet.getRow(sheet.getFirstRowNum());
					int cellSize = firstRow.getLastCellNum();
					for (Row row : sheet) {
						ArrayList<HSSFCell> cellRowList = new ArrayList<HSSFCell>();
						for (int cn = 0; cn < cellSize; cn++) {
							// If the cell is missing from the file,
							// generate a blank one
							// (Works by specifying a MissingCellPolicy)
							HSSFCell myCell = (HSSFCell) row.getCell(cn,
									Row.CREATE_NULL_AS_BLANK);
							// Print the cell for debugging
							// System.out.println("CELL: " + cn + " --> " +
							// myCell.toString());
							cellRowList.add(myCell);
						}
						cellGridHssf.add(cellRowList);
					}

					// HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					// Iterator<?> rowIter = mySheet.rowIterator();
					//
					// while (rowIter.hasNext()) {
					// HSSFRow myRow = (HSSFRow) rowIter.next();
					// Iterator<?> cellIter = myRow.cellIterator();
					// List<HSSFCell> cellRowList = new
					// ArrayList<HSSFCell>();
					// while (cellIter.hasNext()) {
					// HSSFCell myCell = (HSSFCell) cellIter.next();
					// cellRowList.add(myCell);
					// }
					// cellGridHssf.add(cellRowList);
					// }

					// Validation Check
					/*
					 * if (checkXls(cellGridHssf)) { throw new
					 * Exception(getMessage()); }
					 */

					// Text로 변환
					/*
					 * file = new File(savePath + "test.txt"); stream = new
					 * PrintStream(file); String stringCellValue = null;
					 */

					for (int i = 0; i < cellGridHssf.size(); i++) {
						ArrayList<HSSFCell> cellRowList = (ArrayList<HSSFCell>) cellGridHssf
								.get(i);

						Customer customer = new Customer();
						HSSFCell myCell = (HSSFCell) cellRowList.get(0);
						customer.setEtc01(myCell.getRichStringCellValue()
								.getString());
						myCell = (HSSFCell) cellRowList.get(1);
						customer.setHp(myCell.getRichStringCellValue()
								.getString());
						customers.add(customer);

						/*
						 * for (int j = 0; j < cellRowList.size(); j++) {
						 * HSSFCell myCell = (HSSFCell) cellRowList.get(j);
						 * 
						 * // 첫번째 Cell의 값(AWS_ID)을 목록에 저장한다. if (j == 0) {
						 * Target awsModel = new Target();
						 * awsModel.setAWS_ID(myCell .getRichStringCellValue()
						 * .getString()); awsList.add(awsModel); }
						 * 
						 * switch (myCell.getCellType()) { case
						 * HSSFCell.CELL_TYPE_STRING: if
						 * (myCell.getRichStringCellValue
						 * ().getString().equals(null) ||
						 * myCell.getRichStringCellValue()
						 * .getString().equals("null")) { stringCellValue =
						 * "\\N"; } else { stringCellValue = myCell
						 * .getRichStringCellValue() .getString(); } break; case
						 * HSSFCell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(myCell)) { System.out
						 * .println("CELL_TYPE_NUMERIC++DATA===" +
						 * myCell.getDateCellValue()); SimpleDateFormat
						 * formatter = new SimpleDateFormat( "yyyy-MM-dd");
						 * stringCellValue = formatter.format(myCell
						 * .getDateCellValue()); } else { stringCellValue =
						 * String.valueOf(myCell .getNumericCellValue()); }
						 * break; case HSSFCell.CELL_TYPE_FORMULA:
						 * System.out.println("CELL_TYPE_FORMULA===" +
						 * myCell.getCellFormula()); stringCellValue =
						 * myCell.getCellFormula(); break; case
						 * HSSFCell.CELL_TYPE_BOOLEAN:
						 * myCell.getBooleanCellValue(); break; case
						 * HSSFCell.CELL_TYPE_ERROR: stringCellValue = "" +
						 * myCell.getErrorCellValue(); break; case
						 * HSSFCell.CELL_TYPE_BLANK: stringCellValue = "";
						 * break; default: break; }// switch
						 */

					}// for cellRowList

				}// for cellGridXssf

			}// Excel 2007(.xls) 이하 파일처리 끝

			// AWS ID 중복체크

			// DB에 등록 , Load data로

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (BadSqlGrammarException bsge) {
			bsge.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// stream.close();
			// file.delete();
			tempFile.delete();
		}

		return customers;

	}
}
