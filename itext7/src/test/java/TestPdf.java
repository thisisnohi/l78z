import com.google.common.collect.Lists;
import com.itext.TextFooterEventHandler;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.junit.Test;
import vo.ColumnVo;
import vo.TableVo;

import java.util.List;

/**
 * @author NOHI
 * @program: itext7
 * @description:
 * @create 2020-12-11 09:39
 **/
public class TestPdf {

    private static String path = "/Users/nohi/Downloads";

    @Test
    public void test1() {
        List<TableVo> tables = Lists.newArrayList();
        TableVo vo = new TableVo();
        vo.setTableComment("表一");
        vo.setTableName("T_USER_INFO");
        List<ColumnVo> columns = Lists.newArrayList();
        ColumnVo columnVo = new ColumnVo();
        columnVo.setColumnComment("主键");
        columnVo.setColumnKey("key");
        columnVo.setColumnName("主键");
        columnVo.setColumnType("VARCHAR2");
        columnVo.setIsNullable("Y");
        columns.add(columnVo);
        vo.setColumns(columns);
        tables.add(vo);

        this.createPdf(tables);
    }

    public String createPdf(List<TableVo> tables) {

        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/DatabaseDesign.pdf"));
            Document doc = new Document(pdfDoc);// 构建文档对象
            TextFooterEventHandler eh = new TextFooterEventHandler(doc);
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, eh);
            // 中文字体
            PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            Paragraph paragraph = new Paragraph();
            paragraph.add("外数慧查一键尽职调查报告").setFont(sysFont).setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
            doc.add(paragraph);
            int num = 0;
            for (TableVo vo : tables) {
                num++;
                doc.add(new Paragraph(""));
                String title = num + "  表名：" + vo.getTableName() + "   表注释：" + vo.getTableComment();
                doc.add(new Paragraph(title).setFont(sysFont).setBold());
                // 构建表格以100%的宽度
                Table table = new Table(5).setWidth(UnitValue.createPercentValue(100));

                table.addCell(new Cell().add(new Paragraph("列名")).setFont(sysFont)
                        .setBackgroundColor(new DeviceRgb(221, 234, 238)));
                table.addCell(new Cell().add(new Paragraph("数据类型")).setFont(sysFont)
                        .setBackgroundColor(new DeviceRgb(221, 234, 238)));
                table.addCell(new Cell().add(new Paragraph("约束")).setFont(sysFont)
                        .setBackgroundColor(new DeviceRgb(221, 234, 238)));
                table.addCell(new Cell().add(new Paragraph("允许空")).setFont(sysFont)
                        .setBackgroundColor(new DeviceRgb(221, 234, 238)));
                table.addCell(new Cell().add(new Paragraph("备注")).setFont(sysFont)
                        .setBackgroundColor(new DeviceRgb(221, 234, 238)));
                for (ColumnVo col : vo.getColumns()) {
                    table.addCell(new Cell().add(new Paragraph(col.getColumnName())).setFont(sysFont));
                    table.addCell(new Cell().add(new Paragraph(col.getColumnType())).setFont(sysFont));
                    table.addCell(new Cell().add(new Paragraph(col.getColumnKey())).setFont(sysFont));
                    table.addCell(new Cell().add(new Paragraph(col.getIsNullable())).setFont(sysFont));
                    table.addCell(new Cell().add(new Paragraph(col.getColumnComment())).setFont(sysFont));
                }
                // 将表格添加入文档并页面居中
                doc.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));
            }
            doc.close();
            return "文件路径-" + System.getProperty("user.dir") + "\\DatabaseDesign.pdf";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导出失败！请检查配置。";
    }
}
