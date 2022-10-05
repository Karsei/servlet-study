package kr.pe.karsei.servletstudy.web.frontcontroller.v3;

import kr.pe.karsei.servletstudy.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
