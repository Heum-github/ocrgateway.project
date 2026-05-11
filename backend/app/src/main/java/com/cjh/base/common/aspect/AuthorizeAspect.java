package com.cjh.base.common.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cjh.common.shared.exception.UnAuthorizedPageException;
import com.cjh.common.shared.utils.UserSessionUtils;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizeAspect {

    private final HttpSession _httpSession;

    public AuthorizeAspect(HttpSession httpSession) {
        this._httpSession = httpSession;
    }

   @Around("within(@org.springframework.stereotype.Controller *) && @annotation(authorize)")
    public Object hasAuthorizePage(ProceedingJoinPoint joinPoint, Authorize authorize) throws Throwable {
        
        // 세션 유효성 검사
        if (!UserSessionUtils.isValid(_httpSession)) {
            throw new UnAuthorizedPageException("로그인이 필요합니다.");
        }

        return joinPoint.proceed();
    }
}
