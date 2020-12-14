import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NOHI
 * @program: itext7
 * @description:
 * @create 2020-12-11 09:39
 **/
public class Html2PDFDemo {

    private static String path = "/Users/nohi/Downloads";

    /**
     * 填充html模板
     *
     * @param templateFile 模板文件名
     * @param args         模板参数
     * @param pdfFile      生成文件路径
     */
    public void template(String templateFile, Map<String, String> args, String pdfFile) {
        FileOutputStream output = null;
        try {
            // 读取模板文件,填充模板参数
            Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_30);
            freemarkerCfg.setTemplateLoader(new ClassTemplateLoader(Html2PDFDemo.class, "/template/"));
            Template template = freemarkerCfg.getTemplate(templateFile);
            StringWriter out = new StringWriter();
            if (args != null && args.size() > 0)
                template.process(args, out);
            String html = out.toString();

            // 设置字体以及字符编码
            ConverterProperties props = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            fontProvider.addFont(sysFont.getFontProgram(), "UniGB-UCS2-H");
            fontProvider.addStandardPdfFonts();
            fontProvider.addFont("template/simsun.ttc");
            fontProvider.addFont("template/STHeitibd.ttf");
            props.setFontProvider(fontProvider);
            props.setCharset("utf-8");

            // 转换为PDF文档
            if (pdfFile.indexOf("/") > 0) {
                File path = new File(pdfFile.substring(0, pdfFile.indexOf("/")));
                if (!path.exists())
                    path.mkdirs();
            }
            output = new FileOutputStream(new File(pdfFile));

            PdfDocument pdf = new PdfDocument(new PdfWriter(output));
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getCatalog().setLang(new PdfString("UTF-8"));

            // 添加事件，该事件拥有添加水印
            WaterMark waterMark = new WaterMark();
//            pdf.addEventHandler(PdfDocumentEvent.INSERT_PAGE, waterMark);


            // 添加页脚页
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new PageXofY(pdf));

//            //Set the document to be tagged
//            pdf.setTagged();
//            pdf.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
//
//            //Set meta tags
//            PdfDocumentInfo pdfMetaData = pdf.getDocumentInfo();
//            pdfMetaData.setAuthor("NOHI");
//            pdfMetaData.addCreationDate();
//            pdfMetaData.getProducer();
//            pdfMetaData.setCreator("NOHI");
//            pdfMetaData.setKeywords("nohi-pdf-demo");
//            pdfMetaData.setSubject("PDF resume");
//            //Title is derived from html
//            //Create event-handlers
//            String footer = "来自：XX网 - www.XXX.com";
//            Footer footerHandler = new Footer(footer, sysFont);
//            PageXofY footPageHandler = new PageXofY(pdf);
//            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footPageHandler);

            Document document = HtmlConverter.convertToDocument(html, pdf, props);
            document.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters() {
                @Override
                public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
                    //return super.isSplitCharacter(text, glyphPos);//覆盖当前
                    return true;//解决word-break: break-all;不兼容的问题，解决纯英文或数字不自动换行的问题
                }
            });
            document.getRenderer().close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Header event handler
    protected class Header implements IEventHandler {
        String header;
        public Header(String header) {
            this.header = header;
        }
        @Override
        public void handleEvent(Event event) {
            //Retrieve document and
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas.setFontSize(18f);
            //Write text at position
            canvas.showTextAligned(header,
                    pageSize.getWidth() / 2,
                    pageSize.getTop() - 30, TextAlignment.CENTER);
        }
    }
    //Header event handler
    protected class Footer implements IEventHandler {
        String footer;
        PdfFont pdfFont;
        public Footer(String footer,PdfFont pdfFont) {
            this.footer = footer;
            this.pdfFont = pdfFont;
        }
        @Override
        public void handleEvent(Event event) {
            //Retrieve document and
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas.setFontSize(8f);
            canvas.setFont(pdfFont);
            //Write text at position
            canvas.showTextAligned(footer,
                    pageSize.getWidth() / 2,
                    pageSize.getBottom() + 30, TextAlignment.CENTER);
        }
    }

    //page X of Y
    protected class PageXofY implements IEventHandler {
        protected PdfFormXObject placeholder;
        protected float side = 20;
        protected float x = 120;
        protected float y = 25;
        protected float space = 4.5f;
        protected float descent = 3;
        public PageXofY(PdfDocument pdf) {
            placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
        }
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);
            int totalPageNum = docEvent.getDocument().getNumberOfPages();
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText();
            canvas.setFontAndSize(getDefaultFont(), 10);
            String text = "一二三    xxxxxxxxxx.js    查询时: 2020-12-14 17:19:29    第" + pageNum + "页/共" + totalPageNum + "页";
            canvas.moveText(x, y);
            canvas.showText(text);
            canvas.endText();
            canvas.stroke();
            canvas.release();
        }
    }

    /**
     * 监听事件 添加水印
     *
     */
    protected static class WaterMark implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfFont font = null;
            try {
//                font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD); // 要显示中文水印的话，需要设置中文字体，这里可以动态判断
                font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfCanvas canvas = new PdfCanvas(page);
            PdfExtGState gs1 = new PdfExtGState();
            gs1.setFillOpacity(0.5f); // 水印透明度
            canvas.setExtGState(gs1);
            new Canvas(canvas, pdfDoc, page.getPageSize()).setFontColor(ColorConstants.LIGHT_GRAY).setFontSize(60)
                    .setFont(font).showTextAligned(new Paragraph("NOHI-添加水印"), 298, 421,
                    pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        }

    }

    /**
     * 获取默认字体
     *
     * @return
     */
    public static PdfFont getDefaultFont() {
        try {
            return PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Test
    public void test(){
        Map<String, String> para = new HashMap<String, String>();
        para.put("var1", "这个值是填充的变量");
        para.put("tab1", "<tr><td>1</td><td>第一个项目</td><td>第一个项目的具体内容</td></tr><tr><td>2</td><td>第二个项目</td><td>第二个项目的具体内容</td></tr>");
        template("demo.html", para, path + "/abc.pdf");
    }

}
