package io.lighty.core.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class SocketAnalyzerTest {

    private static final long TIMEOUT = 3;

    @Test
    public void socketAnalyzerAwaitPortSuccess() throws IOException {
        ServerSocket blockingSocket = null;
        try {
            final int availablePort = findAvailablePort();
            blockingSocket = new ServerSocket(availablePort);
            Assert.assertFalse(SocketAnalyzer.awaitPortAvailable(availablePort, TIMEOUT, TimeUnit.SECONDS));
        } catch (InterruptedException | IOException e) {
            Assert.fail("SocketAnalyzer test failed.", e);
        } finally {
            if (blockingSocket != null) {
                blockingSocket.close();
            }
        }
    }

    private int findAvailablePort() throws InterruptedException {
        for(int port = 50000; port < 80000; port++) {
            if (SocketAnalyzer.awaitPortAvailable(port, 1, TimeUnit.SECONDS)) {
                return port;
            }
        }
        throw new IllegalStateException();
    }
}