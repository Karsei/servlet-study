package kr.pe.karsei.servletstudy.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 핸들러 매핑 -> 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 함
 * (ex. 스프링 빈 이름으로 핸들러 찾을 수 있는 핸들러 매핑 필요)
 *
 * 핸들러 어댑터 -> 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요
 * (ex. Controller 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행)
 */
@Component("/springmvc/old-controller")
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}