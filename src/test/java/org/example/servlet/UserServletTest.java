//package org.example.servlet;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.service.UserService;
//import org.example.servlet.dto.UserOutGoingDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServletTest {
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private PrintWriter printWriter;
//
//    @InjectMocks
//    private UserServlet userServlet;
//
//    @Test
//    void testGetAllSuccessful() throws IOException {
//        when(request.getPathInfo()).thenReturn("/all");
//        when(response.getWriter()).thenReturn(printWriter);
//        when(userService.findAll()).thenReturn(Collections.singletonList(new UserOutGoingDto()));
//
//        userServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(printWriter).write("[{\"username\":null,\"posts\":null,\"albums\":null}]");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoGetInvalid() throws IOException {
//        when(request.getPathInfo()).thenReturn("/invalid");
//        when(response.getWriter()).thenReturn(printWriter);
//
//        userServlet.doGet(request, response);
//
//        verify(response).setContentType("application/json");
//        verify(response).setCharacterEncoding("UTF-8");
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(printWriter).write("Bad request.");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoGetByIdSuccessful() throws IOException {
//        long userId = 1L;
//        when(request.getPathInfo()).thenReturn("/" + userId);
//        when(response.getWriter()).thenReturn(printWriter);
//        when(userService.findById(userId)).thenReturn(new UserOutGoingDto());
//
//        userServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(printWriter).write("{\"username\":null,\"posts\":null,\"albums\":null}");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoDeleteSuccessful() throws IOException {
//        long userId = 1L;
//        when(request.getPathInfo()).thenReturn("/" + userId);
//        when(response.getWriter()).thenReturn(printWriter);
//
//        userServlet.doDelete(request, response);
//
//        verify(userService).deleteById(userId);
//        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
//        verify(printWriter).write("User with id " + userId + " deleted");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoPostSuccessful() throws IOException {
//        String json = "{\"username\": \"TestUser\"}";
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
//        when(response.getWriter()).thenReturn(printWriter);
//
//        userServlet.doPost(request, response);
//
//        verify(printWriter).write("null");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoPostBadRequest() throws IOException {
//        when(response.getWriter()).thenReturn(printWriter);
//        when(request.getReader()).thenReturn(mock(BufferedReader.class));
//
//        userServlet.doPut(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//    }
//}