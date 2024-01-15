package com.teamc.moodtracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/sse")
public class SSEController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @GetMapping("/subscribe")
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @GetMapping("/send")
    public void sendNotification(String message){
        for(SseEmitter emitter : emitters){
            try {
                emitter.send(SseEmitter.event()
                        .data(message)
                        .id(String.valueOf(idCounter.incrementAndGet()))
                        .name("notification")
                        .reconnectTime(3000));

            }catch (IOException e){
                emitters.remove(emitter);
            }
        }
    }

}
