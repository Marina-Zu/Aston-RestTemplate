package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AlbumService;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AlbumServletTest {
    @Mock
    private AlbumService albumService;
//    @Mock
//    private ObjectMapper objectMapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

   @InjectMocks
    private AlbumServlet albumServlet;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
    @Test
    void doGet() throws Exception {
        long albumId = 1L;
        AlbumOutGoingDto albumDto = new AlbumOutGoingDto("Test Album", "Test Description", 1L, null);

        when(request.getPathInfo()).thenReturn("/" + albumId);
        when(albumService.findById(anyLong())).thenReturn(albumDto);
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(printWriter).flush();
    }

    @Test
    void doGetF() throws Exception {
        long albumId = -1L;

        when(request.getPathInfo()).thenReturn("/" + albumId);
        when(albumService.findById(albumId)).thenThrow(new RuntimeException("Some error message")); // Бросаем исключение, чтобы проверить обработку ошибки
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Bad request.");
        verify(printWriter).flush();
    }


    @Test
    void doDelete() {
    }

    @Test
    void doPost() {
    }

    @Test
    void doPut() {
    }
}