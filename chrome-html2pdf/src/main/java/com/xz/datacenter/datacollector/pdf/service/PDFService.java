package com.xz.datacenter.datacollector.pdf.service;

import com.xz.datacenter.datacollector.pdf.vo.PDFSessionDetail;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/4
 */
public interface PDFService {

    void generatePDF(PDFSessionDetail pdfSessionDetail, String value);

}
