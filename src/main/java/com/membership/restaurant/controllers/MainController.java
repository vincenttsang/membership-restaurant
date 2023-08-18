package com.membership.restaurant.controllers;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@Log4j2
public class MainController {
    @GetMapping(value = {"/image/16-9.png", "/image/16x9", "/image/bg"}, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getAzumaSerenHD() {
        try {
            InputStream in = getClass().getResourceAsStream("/static/bg.png");
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/image/sticker", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getAzumaSerenSticker() {
        try {
            InputStream in = getClass().getResourceAsStream("/static/take_it_easy.png");
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = {"/", "index", "index.html", "index.htm"}, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex() {
        try {
            InputStream in = getClass().getResourceAsStream("/static/main.html");
            return IOUtils.toString(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
