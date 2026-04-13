package com.cjh.claim.common.aspect;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cjh.common.shared.enums.UserRole;
import com.cjh.common.shared.utils.IpUtil;
import com.cjh.common.shared.utils.UserSessionUtils;

@Aspect
@Component
public class CommonModelAttributeAspect {

    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(com.cjh.claim.common.aspect.CommonModelAttribute)")
    public void addCommonModelAttributes(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return;
        }

        Object[] args = joinPoint.getArgs();
        Model model = null;

        // 메서드의 파라미터 중 Model 타입을 자동으로 감지
        for (Object arg : args) {
            if (arg instanceof Model) {
                model = (Model) arg;
                break;
            }
        }

        if (model == null) {
            return;
        }

        var user = UserSessionUtils.getUser();

        if (user == null) {
            return;
        }

        model.addAttribute("loginTime", user.getLoginDateTime());

        HttpServletRequest request = attributes.getRequest();
        model.addAttribute("loginIp", IpUtil.getClientIp(request));
        model.addAttribute("loginUserId", user.getUserId());
        model.addAttribute("loginUserName", user.getUserName());
        model.addAttribute("loginUserBizCode", user.getUserBizCode());
        model.addAttribute("loginUserBizName", user.getUserBizName());
        model.addAttribute("loginUserRole", user.getUserRole());
        model.addAttribute("loginUserRoleName", user.getUserRoleName());
        model.addAttribute("loginUserDepartment", user.getDepartmentId());
        model.addAttribute("loginUserDepartmentName", user.getDepartmentName());
        model.addAttribute("loginUserDepartmentL1", user.getDepartmentIdL1());
        model.addAttribute("loginUserDepartmentNameL1", user.getDepartmentNameL1());
        model.addAttribute("isAdmin", UserRole.isAdmin(user.getUserRole()));
        model.addAttribute("isDeveloper", UserRole.isDeveloper(user.getUserRole()));
    }
}
