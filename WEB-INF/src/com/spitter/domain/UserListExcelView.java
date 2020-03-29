package com.spitter.domain;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * @author Tyler Yin
 */
@Component(value = "userListExcel")
public class UserListExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "inline; filename=" + new String("员工列表.xls".getBytes(), "ISO8859-1"));
        List<User> userList = (List<User>) model.get("userList");
        HSSFSheet sheet = workbook.createSheet("users");
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("姓名");
        header.createCell(1).setCellValue("电话");

        int rowNum = 1;
        for (User user : userList) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getPhone());
        }
    }
}
