package ru.otus.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import net.bytebuddy.description.method.MethodDescription;
import ru.otus.hibernate.crm.model.Address;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;
import ru.otus.hibernate.crm.service.DBServiceClient;
import ru.otus.model.User;
import ru.otus.dao.UserDao;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var clients = dbServiceClient.findAll();

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String json = gson.toJson(clients);
        out.print(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var request = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        var requestJson = gson.fromJson(request, JsonObject.class);
        var client = createClientFromRequest(requestJson);
        dbServiceClient.saveClient(client);
    }

    private Client createClientFromRequest(JsonObject json){
        var client = new Client();
        var phones = json.get("phones").getAsJsonArray();
        List<Phone> phonesList = new ArrayList<>();
        for(int i =0;i<phones.size();i++){
            phonesList.add(new Phone(null,phones.get(i).getAsString()));
        }
        var address = new Address(null, json.get("address").getAsString());
        var name = json.get("name").getAsString();
        client.setPhones(phonesList);
        client.setAddress(address);
        client.setName(name);
        return client;
    }
    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
