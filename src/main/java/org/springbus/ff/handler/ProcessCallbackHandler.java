package org.springbus.ff.handler;

import java.io.InputStream;


public interface ProcessCallbackHandler {
    String handler(InputStream inputStream) throws Exception;
}