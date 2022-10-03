package kr.pe.karsei.servletstudy.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
    }

private void printStartLine(HttpServletRequest request) {
    System.out.println("--- REQUEST-LINE - start ---");
    // GET
    System.out.println("request.getMethod() = " + request.getMethod());
    // HTTP/1.1
    System.out.println("request.getProtocol() = " + request.getProtocol());
    // http
    System.out.println("request.getScheme() = " + request.getScheme());
    // http://localhost:8080/request-header
    System.out.println("request.getRequestURL() = " + request.getRequestURL());
    // /request-header
    System.out.println("request.getRequestURI() = " + request.getRequestURI());
    // username=hi
    System.out.println("request.getQueryString() = " + request.getQueryString());
    // https 사용 유무
    System.out.println("request.isSecure() = " + request.isSecure());
    System.out.println("--- REQUEST-LINE - end ---");
    System.out.println();
}

    // Header 모든 정보
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");

        // 옛날 방식
        /*
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        */
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));
        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    // Header 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        // localhost
        System.out.println("request.getServerName() = " + request.getServerName());
        // 8080
        System.out.println("request.getServerPort() = " + request.getServerPort());
        System.out.println();
        System.out.println("[Accept-Language 편의 조회]");
        /*
        locale = ko_KR
        locale = ko
        locale = en_US
        locale = en
        locale = ja
        request.getLocale() = ko_KR
         */
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " +
                        locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();
        System.out.println("[cookie 편의 조회]");
        // Idea-6031eaf1: ecd64e47-70e3-4c61-8f49-56de1963d645
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();
        System.out.println("[Content 편의 조회]");

        // text/plain
        System.out.println("request.getContentType() = " + request.getContentType());
        // 8
        System.out.println("request.getContentLength() = " + request.getContentLength());
        // UTF-8
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
// 0:0:0:0:0:0:0:1
System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
// 0:0:0:0:0:0:0:1
System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
// 8239
System.out.println("request.getRemotePort() = " + request.getRemotePort());
System.out.println();
System.out.println("[Local 정보]");
// 0:0:0:0:0:0:0:1
System.out.println("request.getLocalName() = " + request.getLocalName());
// 0:0:0:0:0:0:0:1
System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
// 8080
System.out.println("request.getLocalPort() = " + request.getLocalPort());
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
