package kr.pe.karsei.servletstudy.web.frontcontroller.v3.controller;

import kr.pe.karsei.servletstudy.domain.member.Member;
import kr.pe.karsei.servletstudy.domain.member.MemberRepository;
import kr.pe.karsei.servletstudy.web.frontcontroller.ModelView;
import kr.pe.karsei.servletstudy.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {
    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
    }
}
