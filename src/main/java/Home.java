import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Home {
    static int buhariVotes = 0;
    static int jonathanVotes = 0;
    static int kachikwuVotes = 0;


    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/templates");
        get("/home", Home::welcome, new ThymeleafTemplateEngine());

        post("/vote", (req, res) -> {
            //ObjectMapper provides functionality for reading and writing JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Vote votePojo = objectMapper.readValue(req.body(), Vote.class);
            vote(votePojo);
            return "Vote successful";
        });

    }

    private static ModelAndView welcome(Request req, Response res) {
        Map<String, Object> params = new HashMap<>();
        params.put("Jonathan", jonathanVotes);
        params.put("Buhari", buhariVotes);
        params.put("Kachikwu", kachikwuVotes);

        return new ModelAndView(params, "welcome");
    }

    private static void vote(Vote vote) {
        if (vote.getName().equalsIgnoreCase("Buhari")) {
            buhariVotes++;
        } else if (vote.getName().equalsIgnoreCase("Jonathan")) {
            jonathanVotes++;
        } else if (vote.getName().equalsIgnoreCase("Kachikwu")) {
            kachikwuVotes++;
        }

    }
}
