package com.bottle.event;

import com.bottle.APIHandlerServlet;
import com.bottle.event.service.EventService;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CreateEventServlet extends APIHandlerServlet.APIRequestHandler  {
    private static final CreateEventServlet instance = new CreateEventServlet();

    public static CreateEventServlet getInstance() {
        return instance;
    }

    private CreateEventServlet() {
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        EventService eventService = EventService.getEventService();

        JSONObject jsonObject = new JSONObject();
        String result = eventService.registrationEvent(map);
        jsonObject.put("result", result);
        return jsonObject;
    }
}
