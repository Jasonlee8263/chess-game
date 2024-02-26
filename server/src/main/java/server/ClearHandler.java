package server;

import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    ClearService clearService = new ClearService();

    public Object clear(Request req, Response res) {
        clearService.clear();
        res.status(200);
        return "{}";
    }
}
