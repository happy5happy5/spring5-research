package com.demo1010.researcherv2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
class ApplicationRunnerUtility implements org.springframework.boot.ApplicationRunner {

    private final WebServerApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(ApplicationRunnerUtility.class);

    @Autowired
    public ApplicationRunnerUtility(WebServerApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        WebServer webServer = context.getWebServer();
        int port = webServer.getPort();
        String hostName = getHostName();

        logger.info("http://{}:{}/research/list", hostName, port);
        logger.info("http://127.0.0.1:{}/research/list", port);
    }

    private String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("error while getting host name", e);
            return "localhost";
        }
    }
}
