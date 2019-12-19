package com.woowacourse.caffeine.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

class NotificationInternalService<T> {

    private static final Logger logger = LoggerFactory.getLogger(NotificationInternalService.class);

    private static final long TIMEOUT_IN_MINUTE = 3l;

    private final Map<T, ResponseBodyEmitter> subscriptions = new HashMap<>();
    private final Map<T, Queue<String>> pendingMessages = new HashMap<>();

    public ResponseBodyEmitter subscribe(final T id) {
        ResponseBodyEmitter emitter = createSseEmitter(id);

        try {
            emitter.send("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }

        subscriptions.put(id, emitter);

        flushPendingMessages(id);
        return emitter;
    }

    private ResponseBodyEmitter createSseEmitter(final T id) {
        ResponseBodyEmitter emitter = new SseEmitter(TimeUnit.MINUTES.toMillis(TIMEOUT_IN_MINUTE));
        emitter.onCompletion(onComplete(id));
        emitter.onTimeout(onTimeout(id));
        emitter.onError(onError(id));
        return emitter;
    }

    private void flushPendingMessages(final T id) {
        if (pendingMessages.get(id) != null) {
            pendingMessages.get(id).forEach(m -> send(id, m));
            pendingMessages.get(id).clear();
        }
    }

    private Runnable onComplete(final T id) {
        return () -> {
            logger.info("Client[{}] is on complete", id);
            logger.debug("removed: {}", subscriptions.remove(id));
        };
    }

    private Runnable onTimeout(final T id) {
        return () -> {
            logger.info("Client[{}] is on timeout", id);
            logger.debug("removed: {}", subscriptions.remove(id));
        };
    }

    private Consumer<Throwable> onError(final T id) {
        return t -> {
            logger.error("Client is on error", t);
            subscriptions.remove(id);
        };
    }

    public void send(final T id, final String message) {
        if (isNotSubscribe(id)) {
            putPendingMessage(id, message);
            return;
        }
        sendMessage(id, message);
    }

    private void sendMessage(final T id, final String message) {
        try {
            subscriptions.get(id).send(message);
        } catch (IOException e) {
            logger.error("Error while sending SSE notification", e);
        }
    }

    private boolean isNotSubscribe(final T id) {
        return !subscriptions.containsKey(id);
    }

    private void putPendingMessage(final T id, final String message) {
        pendingMessages.computeIfAbsent(id, k -> new LinkedList<>());
        pendingMessages.get(id).add(message);
    }

    public void sendAll(String message) {
        subscriptions.keySet().stream()
            .forEach(customerId -> send(customerId, message));
    }
}
