<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
%>
<%-- ! Ссылки на ресурсы должны быть абсолютными ! --%>

<html>
<head>
    <title>Пример</title>
    <style>
        @font-face {
            font-family: "HabraFont";
            src: url(http://localhost:8080/resources/fonts/tahoma.ttf);
            -fs-pdf-font-embed: embed;
            -fs-pdf-font-encoding: Identity-H;
        }

        @page {
            margin: 0px;
            padding: 0px;
            size: A4 portrait;
        }

        @media print {
            .new_page {
                page-break-after: always;
            }
        }

        body {
            background-image: url(http://localhost:8080/resources/images/background.png);
        }

        body *{
            padding: 0;
            margin: 0;
        }

        * {
            font-family: HabraFont;
        }

        #block {
            width: 90%;
            margin: auto;
            background-color: white;
            border: dashed #dbdbdb 1px;
        }

        #logo {
            margin-top: 5px;
            width: 100%;
            text-align: center;
            border-bottom: dashed #dbdbdb 1px;
        }

        #content {
            padding-left: 10px;
        }

    </style>
</head>
<body>
<div id="block">
    <div id="logo"><img src="http://localhost:8080/resources/images/habra-logo.png"></div>
    <div id="content">
        Привет, хабр! Текущее время: <%=sdf.format(new Date())%>
        <div class="new_page">&nbsp;</div>
        Новая страница!
    </div>
</div>

</body>
</html>