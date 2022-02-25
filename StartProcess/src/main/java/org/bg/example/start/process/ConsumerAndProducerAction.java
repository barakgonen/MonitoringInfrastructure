package org.bg.example.start.process;

import org.apache.log4j.Logger;

public class ConsumerAndProducerAction implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ProducerAction.class);

    public ConsumerAndProducerAction() {
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            LOGGER.info("Hello! " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
