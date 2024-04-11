package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.AlbumService;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServletTest {
    @Mock
    private AlbumService albumService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private AlbumServlet albumServlet;

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
    void doGetInvalidAlbumId() throws Exception {
        long albumId = -1L;

        when(request.getPathInfo()).thenReturn("/" + albumId);
        when(albumService.findById(albumId)).thenThrow(new RuntimeException("Some error message"));
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Bad request.");
        verify(printWriter).flush();
    }


    @Test
    void doDelete() throws IOException {
        long albumId = 1L;

        when(request.getPathInfo()).thenReturn("/" + albumId);
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doDelete(request, response);

        verify(albumService).deleteById(albumId);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
        verify(printWriter).write("Album with id " + albumId + " deleted");
        verify(printWriter).flush();

    }

    @Test
    public void testDoPost() throws IOException {
        String json = "";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Incorrect album Object.");
        verify(printWriter).flush();
    }

    @Test
    public void testDoPutInValidJson() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"id\": 1, \"title\": \"New Title\", \"description\": \"New Description\", \"authorId\": \"invalid\"}")));
        when(response.getWriter()).thenReturn(printWriter);

        AlbumServlet albumServlet = new AlbumServlet(albumService);

        albumServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Incorrect album Object.");
        verify(printWriter).flush();
    }
}