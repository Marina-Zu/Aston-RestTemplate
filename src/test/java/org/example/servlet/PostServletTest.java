//package org.example.servlet;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.service.PostService;
//import org.example.servlet.dto.PostOutGoingDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.util.Collections;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PostServletTest {
//    @Mock
//    private PostService postService;
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
//    private PostServlet postServlet;
//
//    @Test
//    void testDoGetAllSuccessful() throws IOException {
//        when(request.getPathInfo()).thenReturn("/all");
//        when(response.getWriter()).thenReturn(printWriter);
//        when(postService.findAll()).thenReturn(Collections.singletonList(new PostOutGoingDto()));
//
//        postServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(printWriter).write("[{\"content\":null,\"authorId\":null,\"albumIds\":null}]");
//        verify(printWriter).flush();
//    }
//
//
//    @Test
//    void testDoGetInvalid() throws IOException {
//        when(request.getPathInfo()).thenReturn("/invalid");
//        when(response.getWriter()).thenReturn(printWriter);
//
//        postServlet.doGet(request, response);
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
//        long postId = 1L;
//        when(request.getPathInfo()).thenReturn("/" + postId);
//        when(response.getWriter()).thenReturn(printWriter);
//        when(postService.findById(postId)).thenReturn(new PostOutGoingDto());
//
//        postServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(printWriter).write("{\"content\":null,\"authorId\":null,\"albumIds\":null}");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoDeleteSuccessful() throws IOException {
//        long postId = 1L;
//        when(request.getPathInfo()).thenReturn("/" + postId);
//        when(response.getWriter()).thenReturn(printWriter);
//
//        postServlet.doDelete(request, response);
//
//        verify(postService).deleteById(postId);
//        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
//        verify(printWriter).write("Post with id " + postId + " deleted");
//        verify(printWriter).flush();
//    }
//
//
//    @Test
//    void testDoPostException() throws IOException {
//        String json = "{\"title\": \"Test Post\", \"content\": \"Test Content\", \"authorId\": 1}";
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
//        when(response.getWriter()).thenReturn(printWriter);
//
//        postServlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(printWriter).write("Incorrect post Object.");
//        verify(printWriter).flush();
//    }
//
//    @Test
//    void testDoPutInvalidJson() throws IOException {
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"id\": 1, \"title\": \"New Title\", \"description\": \"New Description\", \"authorId\": \"invalid\"}")));
//        when(response.getWriter()).thenReturn(printWriter);
//
//        postServlet.doPut(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(printWriter).write("Incorrect post Object.");
//        verify(printWriter).flush();
//    }
//}