package kr.pe.karsei.servletstudy.web.frontcontroller.v1;

import kr.pe.karsei.servletstudy.web.frontcontroller.v1.controller.MemberFormControllerV1;
import kr.pe.karsei.servletstudy.web.frontcontroller.v1.controller.MemberListControllerV1;
import kr.pe.karsei.servletstudy.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 다형성을 이용하기 위해 {@link ControllerV1} 인터페이스를 생성하고,
 * 인터페이스를 구현한 각 Controller 를 실행
 */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private final Map<String, ControllerV1> controllerV1Map = new HashMap<>();

    public FrontControllerServletV1() {
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();// /front-controller/v1/members/new-form
        // 다형성을 이용하여 일관성있게 호출한다.
        ControllerV1 controller = controllerV1Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.process(request, response);
    }
}
