package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.services.*;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final Logger log = LoggerFactory.getLogger(WebServerWithFilterBasedSecurityDemo.class);



    public static void main(String[] args) throws Exception {
        var dbServiceClient = DBServiceClientProvider.getDbServiceClient();
//        dbServiceClient.saveClient(new Client(null, "Vasya", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
//                new Phone(null, "14-666-333"))));
//        dbServiceClient.saveClient(new Client(null, "Ilya", new Address(null, "IlyaStreet"), List.of(new Phone(null, "8-800-555-35-35"),
//                new Phone(null, "8-999-999-99-99"))));
        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, userDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
