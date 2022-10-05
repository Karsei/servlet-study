package kr.pe.karsei.servletstudy.web.frontcontroller.v2;

import kr.pe.karsei.servletstudy.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException; // Servlet 인터페이스와 동일
}
