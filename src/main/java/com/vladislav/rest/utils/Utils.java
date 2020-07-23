package com.vladislav.rest.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@UtilityClass
public class Utils {

    public String readClassPathResourceToString(String path) {
        try {
            final ClassPathResource classPathResource = new ClassPathResource(path);
            return new String(classPathResource.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
