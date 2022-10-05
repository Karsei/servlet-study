package kr.pe.karsei.servletstudy.web.frontcontroller.v4;

import kr.pe.karsei.servletstudy.web.frontcontroller.ModelView;
import kr.pe.karsei.servletstudy.web.frontcontroller.MyView;
import kr.pe.karsei.servletstudy.web.frontcontroller.v2.ControllerV2;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.ControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberFormControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberListControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberFormControllerV4;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberListControllerV4;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ModelView} 를 사용하지 않고 {@link ControllerV4} 에서 String 으로 view 이름을 반환한다.
 * {@link ControllerV4} 에서는 파라미터로 model 을 추가하여 서블릿에서 전달한다.
 */
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private final Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        String requestURI = request.getRequestURI();// /front-controller/v1/members/new-form
        // 다형성을 이용하여 일관성있게 호출한다.
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
        view.render(model, request, response);
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
