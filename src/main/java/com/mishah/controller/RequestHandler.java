package com.mishah.controller;

import com.mishah.logic.PingManager;
import com.mishah.model.PingAnalyticRequest;
import com.mishah.model.PingAnalyticResponse;
import com.mishah.model.Request;
import com.mishah.model.Response;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rest")
public class RequestHandler {

    private final static UUID SYSTEM_ID = UUID.randomUUID();

    @GetMapping("/")
    public String healthCheck() {
        return "Ping-manager - Welcome to RestWorld!!!";
    }

    @PostMapping("/process")
    public Response handleRequest(@RequestBody Request request) {

        Response response = new Response();

        try {

            String input = request.getUrl();

            if (input == null || input.trim().isEmpty())
                throw new NullPointerException("Error: Input found either NULL or empty.");
            else{

                PingAnalyticRequest pingAnalyticRequest = new PingAnalyticRequest();
                pingAnalyticRequest.setUrl(input);

                PingManager pingManager = new PingManager();
                PingAnalyticResponse pingAnalyticResponse = pingManager.pingURL(pingAnalyticRequest);

                response.setSysId(RequestHandler.SYSTEM_ID.toString());
                response.setMessage("INFO: Request execute successfully.");
                response.setError(false);
                response.setPingAnalyticResponse(pingAnalyticResponse);
            }

        } catch (Exception e){
            response.setError(true);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return  response;
    }
}
