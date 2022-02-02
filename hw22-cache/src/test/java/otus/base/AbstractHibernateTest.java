package otus.base;

import cachehw.HwListener;
import cachehw.MyCache;
import hibernate.core.repository.DataTemplateHibernate;
import hibernate.core.repository.HibernateUtils;
import hibernate.core.sessionmanager.TransactionManagerHibernate;
import hibernate.crm.dbmigrations.MigrationsExecutorFlyway;
import hibernate.crm.model.Address;
import hibernate.crm.model.Client;
import hibernate.crm.model.Phone;
import hibernate.crm.service.DBServiceClient;
import hibernate.crm.service.DbServiceClientImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static hibernate.demo.DbServiceDemo.HIBERNATE_CFG_FILE;


public abstract class AbstractHibernateTest {
    protected SessionFactory sessionFactory;
    protected TransactionManagerHibernate transactionManager;
    protected DataTemplateHibernate<Client> clientTemplate;
    protected DBServiceClient dbServiceClient;

    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;

    @BeforeAll
    public static void init() {
        CONTAINER = TestContainersConfig.CustomPostgreSQLContainer.getInstance();
        CONTAINER.start();
    }

    @AfterAll
    public static void shutdown() {
        CONTAINER.stop();
    }

    @BeforeEach
    public void setUp() {
        String dbUrl = System.getProperty("app.datasource.demo-db.jdbcUrl");
        String dbUserName = System.getProperty("app.datasource.demo-db.username");
        String dbPassword = System.getProperty("app.datasource.demo-db.password");

        var migrationsExecutor = new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword);
        migrationsExecutor.executeMigrations();

        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUserName);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class, Address.class);

        transactionManager = new TransactionManagerHibernate(sessionFactory);
        clientTemplate = new DataTemplateHibernate<>(Client.class);
        var cache = new MyCache<Long, Optional<Client>>();
        HwListener<Long, Optional<Client>> listener = new HwListener<Long, Optional<Client>>() {
            @Override
            public void notify(Long key, Optional<Client> value, String action) {
                if(value!=null&&value.isPresent()) {
                    System.out.println(String.format("key:%s, value:%s, action: %s", key, value.get(), action));
                }
                else {
                    System.out.println(String.format("key:$s, value:$s, action: $s", key, value, action));
                }
            }
        };
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate,cache);
    }

    protected EntityStatistics getUsageStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(Client.class.getName());
    }
}
