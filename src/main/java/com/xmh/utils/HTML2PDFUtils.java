package com.xmh.utils;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.xmh.other.B64ImgReplacedElementFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * .
 *
 * @author 谢明辉
 * @date 2019-7-12 14:29
 */
public enum HTML2PDFUtils {
    /** 单例 */
    INSTANCE;

    /**
     * html字符串转pdf
     *
     * @param htmlString html字符串
     * @param fontDir    字体文件路径
     * @return byte[] pdf二进制
     * @date 2019-7-12
     */
    public byte[] html2pdf(String htmlString, String fontDir) throws IOException, SAXException, ParserConfigurationException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();

        fontResolver.addFont(fontDir, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
        renderer.setDocumentFromString(htmlString);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] bytes = outputStream.toByteArray();
        outputStream.close();

        return bytes;
    }
}
