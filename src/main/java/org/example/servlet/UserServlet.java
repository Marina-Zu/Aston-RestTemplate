package org.example.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.impl.UserDtoMapperImpl;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "UserServlet", value = "/user/*")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private UserDtoMapper userDtoMapper;

    @Override
    // http://localhost:8081/user?id=1
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdString = req.getParameter("id");
        if (userIdString == null || userIdString.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ID parameter is required");
        }
        long userId;
        try {
            userId = Long.parseLong(userIdString);

        }

    }


}
