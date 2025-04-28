package com.ssafy.enjoytrip.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.enjoytrip.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
public class AdminInterceptor implements HandlerInterceptor {
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       HttpSession session = request.getSession(false);
       if (session == null || session.getAttribute("user") == null) {
           response.sendRedirect(request.getContextPath() + "/index");
           return false;
       }
       UserDTO loginUser = (UserDTO) session.getAttribute("user");
       if (!"ADMIN".equals(loginUser.getClass())) {
           response.sendRedirect("/");
           return false;
       }
       return true;
       }
}