package com.finaldesign.backend.utils.filter;

import cn.hutool.core.util.StrUtil;
import com.finaldesign.backend.mapper.AdminMapper;
import com.finaldesign.backend.mapper.StudentMapper;
import com.finaldesign.backend.mapper.TeacherMapper;
import com.finaldesign.backend.pojo.Admin;
import com.finaldesign.backend.pojo.Student;
import com.finaldesign.backend.pojo.Teacher;
import com.finaldesign.backend.service.impl.utils.AdminDetailsImpl;
import com.finaldesign.backend.service.impl.utils.StudentDetailsImpl;
import com.finaldesign.backend.service.impl.utils.TeacherDetailsImpl;
import com.finaldesign.backend.utils.JwtUtil;

import javax.validation.constraints.NotNull;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StudentMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        String userid, role;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
            role = claims.get("role", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserDetails userDetails = null;
        if ("STUDENT".equals(role)) {
            Student student = userMapper.selectById(Integer.parseInt(userid));
            userDetails = new StudentDetailsImpl(student);
        } else if ("TEACHER".equals(role)) {
            Teacher teacher = teacherMapper.selectById(Integer.parseInt(userid));
            userDetails = new TeacherDetailsImpl(teacher);
        } else if("ADMIN".equals(role)) {
            Admin admin = adminMapper.selectById(Integer.parseInt(userid));
            userDetails = new AdminDetailsImpl(admin);
        }


        if (userDetails == null) {
            throw new RuntimeException("用户名未登录");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}

