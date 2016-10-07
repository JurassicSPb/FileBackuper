package ru.levelp.model.utils;

/**
 * Created by Мария on 07.10.2016.
 */
import com.google.gson.Gson;
import ru.levelp.model.api.RequestContainer;
import ru.levelp.model.api.ResponseContainer;

public class JsonUtil {
    private static final JsonUtil instance = new JsonUtil();
    private Gson gson = new Gson();

    private JsonUtil() {

    }

    public static JsonUtil getInstance() {
        return instance;
    }

    public ResponseContainer jsonToResponse(String json) {
        return gson.fromJson(json, ResponseContainer.class);
    }

    public String requestToJson(RequestContainer object) {
        return gson.toJson(object);
    }

}