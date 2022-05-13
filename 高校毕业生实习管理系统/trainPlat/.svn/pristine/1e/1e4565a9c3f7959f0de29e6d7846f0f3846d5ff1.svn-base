package edu.hfu.train.util.daoru;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.hfu.train.util.Constant;

public class ExcelPoi<T> {
	private static Logger log = Logger.getLogger(ExcelPoi.class);

	/**
	 * 导出数据
	 *
	 * @param response
	 * @param list
	 * @param titles
	 * @param filename
	 * @throws IOException
	 */
	public static void exportObject(HttpServletResponse response, List<?> list, String[] titles, String filename)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-disposition", "attachment;filename="
				+ new String((filename + System.currentTimeMillis()).getBytes(), "ISO8859-1") + ".xls");

		try (Workbook workbook = new HSSFWorkbook(); OutputStream ops = response.getOutputStream()) {
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 20);
			font.setColor(HSSFFont.COLOR_RED);
			font.setFontName("黑体");
			font.setBold(false);
			font.setItalic(false);

			Sheet sheet = workbook.createSheet(filename);
			Row titleRow = sheet.createRow(0);
			for (int column = 0; column < titles.length; column++) {
				Cell itemCell = titleRow.createCell(column);
				itemCell.setCellValue(titles[column]);
			}
			if (!ParamCheck.isListEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					Row row = sheet.createRow(i + 1);
					createRow(row, titles, list.get(i));
					// System.out.println("list.get(i)"+list.get(i));
				}
			}
			for (int column = 0; column < titles.length; column++) {
				sheet.autoSizeColumn(column);
			}
			workbook.write(ops);
		} catch (Exception e) {
			log.error(e);
		}
	}

	private static void createRow(Row row, String[] titles, Object object) {
		for (int j = 0; j < titles.length; j++) {
			Field[] publicFields = object.getClass().getFields();
			Field[] declaredFields = object.getClass().getDeclaredFields();
			fieldToCell(object, publicFields, titles, row);
			fieldToCell(object, declaredFields, titles, row);
		}
	}

	/**
	 * 获取对象属性填充到表格一条数据
	 *
	 * @param object
	 * @param fields
	 * @param titles
	 * @param row
	 */
	private static void fieldToCell(Object object, Field[] fields, String[] titles, Row row) {
		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelTitle.class)) {
				Object value = MyObjectUtils.getFieldValueByName(object, field);
				ExcelTitle excelTitle = field.getAnnotation(ExcelTitle.class);
				String title = excelTitle.title();
				int index = MyArrayUtils.getIndex(titles, title);
				if (index != -1 && value != null) {
					Cell cell = row.createCell(index);
					cell.setCellValue("" + value);
				}
			}
		}
	}

	/**
	 * 导入数据
	 *
	 * @param file
	 * @param fileName
	 * @param clz
	 * @throws Exception
	 */
	public List<T> importObjectList(InputStream file, String fileName, Class<T> clz) throws Exception {
		List<T> list = new ArrayList<>();
		Workbook workbook = null;
		if (fileName.endsWith(Constant.EXCEL_EXTENSION)) {
			workbook = new XSSFWorkbook(file);
		} else {
			return null;
		}
		file.close();
		if (workbook != null) {
			// Map<String, Integer> titleMap = new HashMap<>();
			Map<Object, Integer> titleMap = new HashMap<>();
			for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
				Sheet sheet = workbook.getSheetAt(sheetNum);
				Row row = sheet.getRow(0);
				for (int columnNum = 0; columnNum < row.getLastCellNum(); columnNum++) {

					// String value = getCellValue(row.getCell(columnNum));
					Object value = getCellValue(row.getCell(columnNum));
					titleMap.put(value, columnNum);
				}
			}
			for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
				Sheet sheet = workbook.getSheetAt(sheetNum);
				Field[] publicFields = clz.getFields();
				Field[] declaredFields = clz.getDeclaredFields();

				int countRow = sheet.getLastRowNum() + 1;
				for (int rowNum = 1; rowNum < countRow; rowNum++) {
					T record = clz.newInstance();
					Row row = sheet.getRow(rowNum);
					setObject(row, publicFields, record, titleMap);
					setObject(row, declaredFields, record, titleMap);
					list.add(record);
				}
			}
			workbook.close();
		}
		return list;
	}

	/**
	 * 获取一条数据为对象赋值
	 *
	 * @param row
	 * @param fields
	 * @param object
	 * @param titleMap
	 */
	public static void setObject(Row row, Field[] fields, Object object, Map<Object, Integer> titleMap) {
		for (Field field : fields) {
			// 注解ExcelTitle是否在field上
			if (field.isAnnotationPresent(ExcelTitle.class)) {
				ExcelTitle excelTitle = field.getAnnotation(ExcelTitle.class);
				String title = excelTitle.title();
				Integer index = titleMap.get(title);// title对应类型的值在excel上的位置
				if (index != null) {
					Object value = getCellValue(row.getCell(index));
					MyObjectUtils.setFieldValueByName(object, field, value);
				}
			}
		}
	}

	/**
	 * 获取单元格的值
	 *
	 * @param cell
	 * @return
	 */
	// 原本Sting型
	public static Object getCellValue(Cell cell) {
		// String cellValue = "";
		Object cellValue = new Object();
		if (cell == null) {
			return cellValue;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date tempValue = cell.getDateCellValue();
				System.out.println("tempValue:"+tempValue);
				//SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
				//cellValue = simpleFormat.format(tempValue);
				cellValue = tempValue;
			} else {
				// cellValue = String.valueOf(cell.getNumericCellValue());
				long longVal = Math.round(cell.getNumericCellValue());
				Double doubleVal = cell.getNumericCellValue();
				int intval = (int) cell.getNumericCellValue();

				if (Double.parseDouble(longVal + ".0") == doubleVal) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					// cellValue = String.valueOf(cell.getStringCellValue());
					cellValue = Integer.parseInt(String.valueOf(longVal));
				}
				// else if(intval>0&&intval<9){
				// cellValue = Integer.parseInt(String.valueOf(longVal));
				// }
				else {
					cellValue = String.valueOf(cell.getNumericCellValue());
					// cellValue = cell.getNumericCellValue();
					// cellValue = doubleVal;
				}
			}
		}

		else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			boolean isValid = true;

			Date date = null;

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				formatter.parse(cell.toString());
			} catch (ParseException pe) {
				System.out.println(pe.toString());
				isValid = false;
			} catch (Exception e) {
				System.out.println(e.toString());
				isValid = false;
			}

			if (isValid) {
				try {

					date = formatter.parse(cell.toString());
					cellValue = date;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				cellValue = String.valueOf(cell.getStringCellValue());// 获取数据

			}

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else {
			cellValue = "";
		}
		System.out.println("cellValue=" + cellValue);
		return cellValue;
	}

	// *************对应的下载工具类***************
	/**
	 * 【远程】文件下载
	 * 
	 * @param request
	 * @param response
	 * @param fileUrl
	 *            请求下载的远程文件路径/地址
	 */
	public void getDownloadData(HttpServletRequest request, HttpServletResponse response, String fileUrl) {
		HttpURLConnection urlCon = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			URL romoteUrl = new URL(fileUrl);
			urlCon = (HttpURLConnection) romoteUrl.openConnection();
			urlCon.setConnectTimeout(10000);
			urlCon.setReadTimeout(30000);

			is = urlCon.getInputStream();
			response.reset();

			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(
							(System.currentTimeMillis() + fileUrl.substring(fileUrl.lastIndexOf("/"))).getBytes(),
							"iso-8859-1"));
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				bis.close();
				bos.close();
				urlCon.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
