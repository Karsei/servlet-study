package kr.pe.karsei.servletstudy.web.frontcontroller.v3;

import kr.pe.karsei.servletstudy.web.frontcontroller.ModelView;
import kr.pe.karsei.servletstudy.web.frontcontroller.MyView;
import kr.pe.karsei.servletstudy.web.frontcontroller.v2.ControllerV2;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberFormControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberListControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ModelView} 를 생성하여 {@link ControllerV2} 에 있는 {@link HttpServletRequest}, {@link HttpServletResponse} 의존성을 삭제한다.
 * 위 작업은 각 Controller 에 있었던 setAttribute 를 삭제하여 대신 Servlet 에서 처리하기 위함이며
 * 또한, {@link MyView} 대신 {@link ModelView} 을 반환하여 위의 setAttribute 부분과 중복되는 view 문자열들을 정리하기 위함이다.
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private final Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();// /front-controller/v1/members/new-form
        // 다형성을 이용하여 일관성있게 호출한다.
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    // 요청으로 들어온 모든 파라미터를 가져온다.
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
