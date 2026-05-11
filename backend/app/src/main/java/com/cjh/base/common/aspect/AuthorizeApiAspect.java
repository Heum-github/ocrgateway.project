package com.cjh.base.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.cjh.common.shared.exception.UnAuthorizedApiException;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * API 요청(`@RestController`)에 대한 세션 검증을 담당하는 Aspect.
 */
@Aspect
@Component
public class AuthorizeApiAspect {

    private final HttpSession _httpSession;

    public AuthorizeApiAspect(HttpSession httpSession) {
        this._httpSession = httpSession;
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(authorizeApi)")
    public void hasAuthorizeApi(final JoinPoint joinPoint, final AuthorizeApi authorizeApi) {
        hasAuthorizeSession();
    }

    public void hasAuthorizeSession() {
        
        // TODO 계정이 비활성화 되어있거나, 유효기간이 만료되었는지 체크하는 로직 추가
        var isValidSession = true;

        if (!isValidSession) {
            throw new UnAuthorizedApiException("API 세션이 만료되었습니다.");
        }
    }
}
