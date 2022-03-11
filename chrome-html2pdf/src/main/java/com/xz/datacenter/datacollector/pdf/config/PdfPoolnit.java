package com.xz.datacenter.datacollector.pdf.config;

import com.xz.datacenter.datacollector.pdf.util.HTML2PDF;
import com.xz.datacenter.datacollector.pdf.vo.PDFSessionDetail;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/21
 */
@Slf4j
@Component
public class PdfPoolnit implements ApplicationRunner {

    @Value("${pdf.pool.launchSize}")
    private int launchSize;

    @Value("${pdf.pool.contextSize}")
    private int contextSize;

    @Value("${pdf.pool.sessionSize}")
    private int sessionSize;

    @Value("${pdf.tempPath}")
    private String tempPath;


    @PreDestroy
    public void destory() {
        log.warn("销毁程序-销毁连接池");
        if(!CollectionUtils.isEmpty(HTML2PDF.sessionPool)){
            for(PDFSessionDetail pdfSessionDetail:HTML2PDF.sessionPool){
                try {
                    if(pdfSessionDetail.getSession()!=null)
                    pdfSessionDetail.getSession().close();
                } catch (Exception e) {
                    log.warn("销毁连接失败");
                }
                try {
                    if(pdfSessionDetail.getSessionFactory()!=null)
                    pdfSessionDetail.getSessionFactory().close();
                } catch (Exception e) {
                    log.warn("销毁连接失败");
                }
                try {
                    if(pdfSessionDetail.getLauncher()!=null)
                    pdfSessionDetail.getLauncher().kill();
                } catch (Exception e) {
                    log.warn("销毁连接失败");
                }
            }

            log.warn("销毁程序-完成销毁："+HTML2PDF.sessionPool.size());
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Test.test();
        log.warn("###初始化pdf连接池###");
        for(int i=1;i<=launchSize;i++){
            Launcher launcher=HTML2PDF.createLauncher(tempPath+"/"+i);
            SessionFactory factory=HTML2PDF.createFactory(launcher);
            for(int j=1;j<=contextSize;j++){
                String context=HTML2PDF.createBrowserContext(factory);
                for(int k=1;k<=sessionSize;k++){
                    Session session=HTML2PDF.createSession(factory,context);

                    PDFSessionDetail pdfSessionDetail=new PDFSessionDetail();
                    pdfSessionDetail.setHold(false);
                    pdfSessionDetail.setActive(true);
                    pdfSessionDetail.setLauncher(launcher);
                    pdfSessionDetail.setSessionFactory(factory);
                    pdfSessionDetail.setBrowserContext(context);
                    pdfSessionDetail.setSession(session);
                    pdfSessionDetail.setLastHoldDate(new Date());
                    session.navigate("http://www.baidu.com");
//                    session.waitDocumentReady();
//                    session.printToPDF(new File("d:/test.pdf").toPath());
                    HTML2PDF.sessionPool.add(pdfSessionDetail);

                }
            }
        }

        log.warn("###完成初始化pdf连接池###"+HTML2PDF.sessionPool.size());

    }
}
