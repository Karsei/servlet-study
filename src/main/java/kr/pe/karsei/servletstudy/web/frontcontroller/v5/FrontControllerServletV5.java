package kr.pe.karsei.servletstudy.web.frontcontroller.v5;

import kr.pe.karsei.servletstudy.web.frontcontroller.ModelView;
import kr.pe.karsei.servletstudy.web.frontcontroller.MyView;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberFormControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberListControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberFormControllerV4;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberListControllerV4;
import kr.pe.karsei.servletstudy.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import kr.pe.karsei.servletstudy.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import kr.pe.karsei.servletstudy.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    // 이 두 개의 메소드를 건들지 않고 관리하면 어떨까?
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();// /front-controller/v1/members/new-form
        // 다형성을 이용하여 일관성있게 호출한다.
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter 를 찾을 수 없음. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
