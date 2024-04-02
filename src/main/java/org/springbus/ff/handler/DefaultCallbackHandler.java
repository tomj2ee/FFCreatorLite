package org.springbus.ff.handler;


import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Slf4j
public class DefaultCallbackHandler implements ProcessCallbackHandler {

    @Override
    public String handler(InputStream inputStream) throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> buffer = Lists.newArrayList();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.add(line);
        }
        return String.join("\n", buffer);
    }
}