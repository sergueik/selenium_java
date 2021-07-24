package ru.habrahabr.pdf.servlets;

import com.lowagie.text.DocumentException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Date: 31.03.2014
 * Time: 9:33
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 */
public class PdfServlet extends HttpServlet {
    private static final String PAGE_TO_PARSE = "http://localhost:8080/page.jsp";
    private static final String CHARSET = "UTF-8";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/pdf");

            byte[] pdfDoc = performPdfDocument(PAGE_TO_PARSE);

            resp.setContentLength(pdfDoc.length);
            resp.getOutputStream().write(pdfDoc);
        } catch (Exception ex) {
            resp.setContentType("text/html");

            PrintWriter out = resp.getWriter();
            out.write("<strong>Something wrong</strong><br /><br />");
            ex.printStackTrace(out);
        }
    }

    /**
     * Метод, подготавливащий PDF документ.
     * @param path путь до страницы
     * @return PDF документ
     */
    private byte[] performPdfDocument(String path) throws IOException, DocumentException {
        // Получаем HTML код страницы
        String html = getHtml(path);

        // Буффер, в котором будет лежать отформатированный HTML код
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Форматирование HTML кода
        /* эта процедура не обязательна, но я настоятельно рекомендую использовать этот блок */
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setCharset(CHARSET);
        TagNode node = cleaner.clean(html);
        new PrettyXmlSerializer(props).writeToStream(node, out);

        // Создаем PDF из подготовленного HTML кода
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(new String(out.toByteArray(), CHARSET));
        renderer.layout();
        /* заметьте, на этом этапе Вы можете записать PDF документ, скажем, в файл
         * но раз мы пишем сервлет, который будет возвращать PDF документ,
         * нам нужен массив байт, который мы отдадим пользователю */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);

        // Завершаем работу
        renderer.finishPDF();
        out.flush();
        out.close();

        byte[] result = outputStream.toByteArray();
        outputStream.close();

        return result;
    }

    private String getHtml(String path) throws IOException {
        URLConnection urlConnection = new URL(path).openConnection();

        ((HttpURLConnection) urlConnection).setInstanceFollowRedirects(true);
        HttpURLConnection.setFollowRedirects(true);

        boolean redirect = false;

        // Проверка на редирект
        int status = ((HttpURLConnection) urlConnection).getResponseCode();
        if (HttpURLConnection.HTTP_OK != status &&
                (HttpURLConnection.HTTP_MOVED_TEMP == status ||
                        HttpURLConnection.HTTP_MOVED_PERM == status ||
                        HttpURLConnection.HTTP_SEE_OTHER == status)) {

            redirect = true;
        }

        if (redirect) {
            // Обработка заголовка Location
            String newUrl = urlConnection.getHeaderField("Location");

            // Новое соединение до страницы редиректа
            urlConnection = new URL(newUrl).openConnection();
        }

        urlConnection.setConnectTimeout(30000);
        urlConnection.setReadTimeout(30000);


        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), CHARSET));

        StringBuilder sb = new StringBuilder();
        String line;
        while (null != (line = in.readLine())) {
            sb.append(line).append("\n");
        }

        in.close();

        return sb.toString().trim();
    }

    @Override
    public String getServletInfo() {
        return "The servlet that generate and returns pdf file";
    }
}
