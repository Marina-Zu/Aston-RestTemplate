package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AlbumService;
import org.example.service.impl.AlbumServiceImpl;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/album/*"})
public class AlbumServlet extends HttpServlet {
    private final transient AlbumService albumService = AlbumServiceImpl.getInstance();
    private final ObjectMapper objectMapper;

    public AlbumServlet() {
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
                List<AlbumOutGoingDto> albumDtoList = albumService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(albumDtoList);
            } else {
                long albumId = Long.parseLong(pathPart[1]);
                AlbumOutGoingDto albumDto = albumService.findById(albumId);
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(albumDto);
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
            long albumId = Long.parseLong(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            albumService.deleteById(albumId);
            responseAnswer = "Album with id " + albumId + " deleted";
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

        String responseAnswer = "";
        Optional<AlbumIncomingDto> albumResponse;
        try {
            albumResponse = Optional.ofNullable(objectMapper.readValue(json, AlbumIncomingDto.class));
            AlbumIncomingDto album = albumResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(albumService.save(album));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect album Object.";
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
        Optional<AlbumIncomingDto> albumResponse;
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.contains("/addPost/")) {
                String[] pathPart = req.getPathInfo().split("/");
                if (pathPart.length > 3 && "addPost".equals(pathPart[2])) {
                    long albumId = Long.parseLong(pathPart[1]);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    long postId = Long.parseLong(pathPart[3]);
                    albumService.addPost(albumId, postId);
                    responseAnswer = "Post added to album";
                }
            } else {
                albumResponse = Optional.ofNullable(objectMapper.readValue(json, AlbumIncomingDto.class));
                AlbumIncomingDto albumIncomingDto = albumResponse.orElseThrow(IllegalArgumentException::new);
                albumService.update(albumIncomingDto);
                responseAnswer = "Album updated";
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect album Object.";
        }

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
