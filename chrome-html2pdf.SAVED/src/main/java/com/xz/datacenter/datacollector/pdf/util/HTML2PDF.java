package com.xz.datacenter.datacollector.pdf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xz.datacenter.datacollector.pdf.vo.PDFSessionDetail;
import com.xz.datacenter.datacollector.pdf.vo.Result;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.Options;
import io.webfolder.cdp.command.IO;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.io.ReadResult;
import io.webfolder.cdp.type.page.PrintToPDFResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import static io.webfolder.cdp.type.constant.PdfTransferMode.ReturnAsStream;
import static java.util.Base64.getDecoder;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/19
 */
@Slf4j
public class HTML2PDF {
    public static int c=0;

    public static Vector<PDFSessionDetail> sessionPool=new Vector();

    public synchronized static PDFSessionDetail useSession(){
        PDFSessionDetail returnSession=null;
        if(!CollectionUtils.isEmpty(sessionPool))
        for(PDFSessionDetail pdfSessionDetail:sessionPool){
            if(!pdfSessionDetail.isHold()&&pdfSessionDetail.isActive()){
                pdfSessionDetail.setHold(true);
                pdfSessionDetail.setLastHoldDate(new Date());
                pdfSessionDetail.setHoldTimes(pdfSessionDetail.getHoldTimes()+1);
                returnSession=pdfSessionDetail;
                break;
            }
        }
        return returnSession;
    }

    public static void releaseSession(PDFSessionDetail pdfSessionDetail){
        if(pdfSessionDetail!=null){
            if(pdfSessionDetail.getHoldTimes()>100){
                log.warn("切换session");
                Session session=pdfSessionDetail.getSession();
                session.close();
                session=null;
                pdfSessionDetail.setSession(null);

                Session newSession=createSession(pdfSessionDetail.getSessionFactory(),pdfSessionDetail.getBrowserContext());
                pdfSessionDetail.setSession(newSession);
                pdfSessionDetail.setHoldTimes(0);
            }
            pdfSessionDetail.setHold(false);
        }
    }

    public static Session createSession(SessionFactory factory ,String context){
        return factory.create(context);
    }

    public static String createBrowserContext(SessionFactory factory){
        return factory.createBrowserContext();
    }

    public static Launcher createLauncher(String tempPath){
        File tempFile =new File(tempPath);
        if(tempFile.exists()){
            deleteDirectoryContent(tempFile);
        };

        Options options = Options.builder()
                .headless(true)
                .userDataDir(tempFile.toPath())
                .build();

        Launcher launcher = new Launcher(options);
        return launcher;

    }

    public static SessionFactory createFactory(Launcher launcher){
        return launcher.launch();
    }


    public static Result html2Pdf(Session session, String url, String path)  {
        FileOutputStream out=null;
        IO io=null;
        String stream=null;
        try {
            log.warn("开始访问");
            session.navigate(url);
            session.waitDocumentReady();
            String html=session.getContent();
            Document doc= Jsoup.parse(html);
            String str=doc.body().text();

            JSONObject errObject=null;
            try {
                if(str.contains("statusCode"))
                errObject= JSON.parseObject(str);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }



            if(errObject!=null&&errObject.containsKey("statusCode")){
                return Result.failure(errObject.getString("statusCode"),errObject.getString("error"));
            }else {
                log.warn("完成文档渲染-开始打印PDF");
                PrintToPDFResult result = session.getCommand().getPage().printToPDF(null, null,
                        true, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, ReturnAsStream);

                io = session.getCommand().getIO();
                stream = result.getStream();

                out = new FileOutputStream(path);

                boolean eof = false;
                while (!eof) {
                    ReadResult streamResult = io.read(stream);
                    eof = streamResult.getEof();
                    if (streamResult.getBase64Encoded()) {
                        if (streamResult.getData() != null &&
                                !streamResult.getData().isEmpty()) {
                            byte[] content = getDecoder().decode(streamResult.getData());
                            out.write(content);
                        }
                    } else {
                        return Result.failure("-9","编码错误:Inavlid content encoding: it must be base64");
                    }
                }
                result = null;
                return Result.sucess("0",null);
            }
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
            return Result.failure("-10","未知错误："+e.getMessage());
        } finally {
            try {
                out.flush();
                out.close();
                io.close(stream);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }


    public static void deleteDirectoryContent(File file){
        File[] list = file.listFiles();
        for (File f:list){
            if (f.isDirectory()){
                deleteDirectoryContent(new File(f.getPath()));
            }else{
                f.delete();
            }
        }
        file.delete();
    };

}
