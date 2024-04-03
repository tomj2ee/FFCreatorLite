package org.springbus.ff.util;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springbus.ff.handler.DefaultCallbackHandler;
import org.springbus.ff.handler.ProcessCallbackHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CommandRunner {
    private static final Logger log = LoggerFactory.getLogger(CommandRunner.class);


    /**
     * run process
     *
     * @return
     */
    public static String runProcess(List<String> commands) {
        try {
            return runProcess(commands, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * runProcess
     *
     * @return
     * @throws Exception
     */
    public static String runProcess(List<String> commands, ProcessCallbackHandler handler) {
        ProcessBuilder pb = null;
        Process process = null;

        if (log.isDebugEnabled())
            log.debug("start to run ffmpeg process... cmd : '{}'",commands);
        Stopwatch stopwatch = Stopwatch.createStarted();
        pb = new ProcessBuilder(commands);

        pb.redirectErrorStream(true);

        if (null == handler) {
            handler = new DefaultCallbackHandler();
        }

        String result = null;
        try {
            process = pb.start();
            result = handler.handler(process.getInputStream());
        } catch (Exception e) {
            log.error("errorStream:", e);
        } finally {
            if (null != process) {
                try {
                    process.getInputStream().close();
                    process.getOutputStream().close();
                    process.getErrorStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            int flag = process.waitFor();
            if (flag != 0) {
                throw new IllegalThreadStateException("process exit with error value : " + flag);
            }
        } catch (InterruptedException e) {
            log.error("wait for process finish error:{}", e);
        } finally {
            if (null != process) {
                process.destroy();
                pb = null;
            }

            stopwatch.stop();
        }
        if (log.isInfoEnabled()) {
            log.info("ffmpeg run {} seconds, {} milliseconds",
                    stopwatch.elapsed(TimeUnit.SECONDS),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        return result;
    }
}