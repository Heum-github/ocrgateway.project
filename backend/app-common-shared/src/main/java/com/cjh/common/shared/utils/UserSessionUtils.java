package com.cjh.common.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cjh.common.shared.enums.UserRole;
import com.cjh.common.shared.vo.LoginUserSession;
import com.cjh.common.shared.vo.UserInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public final class UserSessionUtils {

    private static final String USER_SESSION_KEY = "userSession";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void authenticateUser(HttpSession session, UserInfo user) {
        setLoggedInUser(session, createUserSession(user));
    }

    // TODO 리팩토링 - 유사한 사용자 VO 객체가 너무 많다. 공통화처리할것 - 현재 3가지를 사용하고 있음 - LoginUserSession, LoginUser, UserInfo 
    private static LoginUserSession createUserSession(UserInfo user) {

        return Optional.ofNullable(user).map(u -> {
            
            LoginUserSession vo = new LoginUserSession();

            vo.setUserId(u.getUserId());
            vo.setUserName(u.getUserName());
            vo.setLoginDateTime(LocalDateTime.now().format(DATE_FORMATTER));
            vo.setUserBizCode(u.getUserBizCode());
            vo.setUserBizName(u.getUserBizName());
            vo.setUserRole(u.getUserRole());
            vo.setUserRoleName(u.getUserRoleName());
            vo.setDepartmentId(u.getDepartmentId());
            vo.setDepartmentName(u.getDepartmentName());
            vo.setIpAddress(u.getIpAddress());
            vo.setDepartmentIdL1(u.getDepartmentIdL1());
            vo.setDepartmentNameL1(u.getDepartmentNameL1());

            return vo;

        }).orElse(null);
    }

    public static void setLoggedInUser(HttpSession session, LoginUserSession user) {
        if (session != null && user != null) {
            session.setAttribute(USER_SESSION_KEY, user);
        }
    }

    public static void clearSession() {
        Optional.ofNullable(getCurrentSession()).ifPresent(HttpSession::invalidate);
    }

    public static String getUserName() {
        return getAttributeFromSession(LoginUserSession::getUserName);
    }

    public static String getUserName(HttpServletRequest request) {
        return getUserName(request.getSession(false));
    }

    public static String getUserName(HttpSession session) {
        return getAttributeFromSession(session, LoginUserSession::getUserName);
    }

    public static String getUserId() {
        return getAttributeFromSession(LoginUserSession::getUserId);
    }

    public static String getUserId(HttpSession session) {
        return getAttributeFromSession(session, LoginUserSession::getUserId);
    }

    public static String getIpAddress() {
        return getAttributeFromSession(LoginUserSession::getIpAddress);
    }

    public static String getIpAddress(HttpSession session) {
        return getAttributeFromSession(session, LoginUserSession::getIpAddress);
    }

    public static boolean isValid() {
        return getUser() != null;
    }

    public static boolean isValid(HttpSession session) {
        return getUser(session) != null;
    }

    public static LoginUserSession getUser() {
        return getUser(getCurrentSession());
    }

    public static LoginUserSession getUser(HttpSession session) {
        return Optional.ofNullable(session)
                .map(s -> (LoginUserSession) s.getAttribute(USER_SESSION_KEY))
                .orElse(null);
    }

    public static String getLoginDateTime() {
        return getAttributeFromSession(LoginUserSession::getLoginDateTime);
    }

    public static String getLoginDateTime(HttpServletRequest request) {
        return getLoginDateTime(request.getSession(false));
    }

    public static String getLoginDateTime(HttpSession session) {
        return getAttributeFromSession(session, LoginUserSession::getLoginDateTime);
    }

    public static String getUserBizName() {
        return getAttributeFromSession(LoginUserSession::getUserBizName);
    }

    public static String getUserBizName(HttpServletRequest request) {
        return getUserBizName(request.getSession(false));
    }

    public static String getUserBizName(HttpSession session) {
        return getAttributeFromSession(session, LoginUserSession::getUserBizName);
    }

    private static HttpSession getCurrentSession() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getSession)
                .orElse(null);
    }

    private static <T> T getAttributeFromSession(HttpSession session, java.util.function.Function<LoginUserSession, T> mapper) {
        return Optional.ofNullable(getUser(session)).map(mapper).orElse(null);
    }

    private static <T> T getAttributeFromSession(java.util.function.Function<LoginUserSession, T> mapper) {
        return getAttributeFromSession(getCurrentSession(), mapper);
    }

    public static boolean isAdmin() {
        return isAdmin(getUser());
    }
    
    public static boolean isAdmin(LoginUserSession userSession) {
        return Optional.ofNullable(userSession)
                       .map(LoginUserSession::getUserRole)
                       .map(UserSessionUtils::isAdmin)
                       .orElse(false);
    }
    
    public static boolean isAdmin(String userRole) {
        return Optional.ofNullable(userRole)
                       .map(UserRole::isAdmin)
                       .orElse(false);
    }
    
    public static boolean isDeveloper() {
        return isDeveloper(getUser());
    }
    
    public static boolean isDeveloper(LoginUserSession userSession) {
        return Optional.ofNullable(userSession)
                       .map(LoginUserSession::getUserRole)
                       .map(UserSessionUtils::isDeveloper)
                       .orElse(false);
    }
    
    public static boolean isDeveloper(String userRole) {
        return Optional.ofNullable(userRole)
                       .map(UserRole::isDeveloper)
                       .orElse(false);
    }
    
}

