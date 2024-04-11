package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AlbumService;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServletTest {
    @Mock
    private AlbumService albumService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private AlbumServlet albumServlet;

    @Test
    void testDoGetSuccessful() throws Exception {
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
    void testDoGetAllSuccessful() throws Exception {
        when(request.getPathInfo()).thenReturn("/all");
        when(response.getWriter()).thenReturn(printWriter);
        when(albumService.findAll()).thenReturn(Collections.singletonList(new AlbumOutGoingDto()));

        albumServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(printWriter).write("[{\"title\":null,\"description\":null,\"authorId\":0,\"postIds\":null}]");
        verify(printWriter).flush();

    }

    @Test
    void testDoGetInvalidAlbumId() throws Exception {
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
    void testDoDeleteSuccessful() throws IOException {
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
    void testDoPostInvalidAlbumId() throws IOException {
        String json = "";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Incorrect album Object.");
        verify(printWriter).flush();
    }

    @Test
    void testDoPutInvalidJson() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"id\": 1, \"title\": \"New Title\", \"description\": \"New Description\", \"authorId\": \"invalid\"}")));
        when(response.getWriter()).thenReturn(printWriter);

        albumServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(printWriter).write("Incorrect album Object.");
        verify(printWriter).flush();
    }
}