package com.spitter.domain;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

@Component(value="userListPDF")
public class UserListPDFView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String("员工列表.pdf".getBytes(), "ISO8859-1"));
		List<User> userList = (List<User>) model.get("userList");
		Table table = new Table(2);
		table.setWidth(80);
		table.setBorder(1);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

		BaseFont cnBaseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		Font cnFont = new Font(cnBaseFont, 10, Font.NORMAL, Color.BLUE);

		table.addCell(buildFontCell("姓名", cnFont));
		table.addCell(buildFontCell("电话", cnFont));
		for (User user : userList) {
			table.addCell(user.getName());
			table.addCell(buildFontCell(user.getPhone(), cnFont));
		}
		document.add(table);
	}

	private Cell buildFontCell(String content, Font font) throws RuntimeException {
		try {
			Phrase phrase = new Phrase(content, font);
			return new Cell(phrase);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
