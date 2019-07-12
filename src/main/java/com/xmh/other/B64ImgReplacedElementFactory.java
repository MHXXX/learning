package com.xmh.other;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;


/**
 * .
 *
 * @author 谢明辉
 * @date 2019-7-12 13:00
 */

public class B64ImgReplacedElementFactory implements ReplacedElementFactory {

    /*
         *
         * <p>Title: createReplacedElement</p>
         * <p>Description: </p>
         * <p>sql: </p>
         *
         * @author  2016年11月1日 下午7:05:55
         *
         * @param c	上下文
         * @param box 盒子
         * @param uac 回调
         * @param cssWidth css宽
         * @param cssHeight css高
         * @return
         * @see org.xhtmlrenderer.extend.ReplacedElementFactory#createReplacedElement(org.xhtmlrenderer.layout.LayoutContext, org.xhtmlrenderer.render.BlockBox, org.xhtmlrenderer.extend.UserAgentCallback, int, int)
         */
    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth,
                                                int cssHeight) {
        Element e = box.getElement();
        if (e == null) {
            return null;
        }
        String nodeName = e.getNodeName();
        // 找到img标签
        if (nodeName.equals("img")) {
            String attribute = e.getAttribute("src");
            FSImage fsImage = null;
            try { // 生成itext图像
                fsImage = buildImage(attribute, uac);
            } catch (BadElementException e1) {
                fsImage = null;
            } catch (IOException e1) {
                fsImage = null;
            } catch (Base64DecodingException e1) {
                e1.printStackTrace();
            }
            if (fsImage != null) { // 对图像进行缩放
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                }
                return new ITextImageElement(fsImage);
            }
        }
        return null;
    }

    /**
     * TODO(将base64编码解码并生成itext图像)
     *
     * @param srcAttr 属性
     * @param uac     回调
     * @return
     * @throws IOException
     * @throws BadElementException
     * @author 2016年11月1日 下午7:08:57
     */
    protected FSImage buildImage(String srcAttr, UserAgentCallback uac) throws IOException, BadElementException, Base64DecodingException {
        FSImage fsImage;
        if (srcAttr.startsWith("data:image/")) {
            String b64encoded = srcAttr.substring(srcAttr.indexOf("base64,") + "base64,".length(), srcAttr.length()); // 解码
            byte[] decodedBytes = Base64.decode(b64encoded);
            fsImage = new ITextFSImage(Image.getInstance(decodedBytes));
        } else {
            fsImage = uac.getImageResource(srcAttr).getImage();
        }
        return fsImage;
    }

    @Override
    public void remove(Element arg0) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void reset() {
        // TODO 自动生成的方法存根

    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener arg0) {
        // TODO 自动生成的方法存根

    }

}
