package org.example.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.exeption.NotFoundException;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@WebServlet(urlPatterns = {"/user/*"})
////@WebServlet(name = "UserServlet", value = "/user/*")
//public class UserServlet extends HttpServlet {
//
//    private final UserService userService = UserServiceImpl.getInstance();
//    private UserDtoMapper userDtoMapper;
//    private ObjectMapper objectMapper;
//
//    @Override
//    // http://localhost:8081/user/{userId}
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String requestURL = req.getRequestURI();
//        String[] pathSegment = requestURL.split("/");
//        String userIdString = pathSegment[pathSegment.length - 1];
//        String responseAnswer = "";
//        try {
//            if (userIdString.equals("all")) {
//                List<UserOutGoingDto> usersDto = userService.findAll();
//                resp.setStatus(HttpServletResponse.SC_OK);
//                responseAnswer = objectMapper.writeValueAsString(usersDto);
//            } else {
//                Optional<UserOutGoingDto> userOptional = userService.findById(Long.parseLong(userIdString));
//
//                if (userOptional.isPresent()) {
//                    UserOutGoingDto userDto = userOptional.get();
//                    resp.setContentType("application/json");
//                    resp.getWriter().write(objectMapper.writeValueAsString(userDto));
//                } else {
//                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    resp.setContentType("text/plain");
//                    resp.getWriter().write("User not found for ID: " + userIdString);
//                }
//            }
//            PrintWriter printWriter = resp.getWriter();
//            printWriter.write(responseAnswer);
//            printWriter.flush();
//
//        } catch (NumberFormatException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.setContentType("text/plain");
//            resp.getWriter().write("Invalid user ID format: " + userIdString);
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.setContentType("text/plain");
//            resp.getWriter().write("Internal server error occurred");
//        }
//    }
//}

@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {
    private final transient UserService userService = UserServiceImpl.getInstance();
    private final ObjectMapper objectMapper;

    public UserServlet() {
        this.objectMapper = new ObjectMapper();
    }

    private static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);

        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                List<UserOutGoingDto> userDtoList = userService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(userDtoList);
            } else {
                Long userId = Long.parseLong(pathPart[1]);
                UserOutGoingDto userDto = userService.findById(userId);
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(userDto);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            long userId = Long.parseLong(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            userService.deleteById(userId);
            responseAnswer = "User with id " + userId + " deleted";
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = null;
        Optional<UserIncomingDto> userResponse;
        try {
            userResponse = Optional.ofNullable(objectMapper.readValue(json, UserIncomingDto.class));
            UserIncomingDto user = userResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(userService.save(user));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect user Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = "";
        UserIncomingDto userResponse;
        try {
            userResponse = objectMapper.readValue(json, UserIncomingDto.class);
            userService.update(userResponse);
            responseAnswer = "User update";
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect user Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
