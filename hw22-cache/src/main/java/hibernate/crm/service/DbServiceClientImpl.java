package hibernate.crm.service;

import cachehw.HwCache;
import cachehw.HwListener;
import cachehw.MyCache;
import hibernate.core.repository.DataTemplate;
import hibernate.core.sessionmanager.TransactionManager;
import hibernate.crm.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<Long,Optional<Client>> cache;


    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<Long,Optional<Client>> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        var clientClone = transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
        cache.put(clientClone.getId(),Optional.of(clientClone));
        return clientClone;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var returned = cache.get(id);
        if(returned==null) {
            returned = transactionManager.doInReadOnlyTransaction(session -> {
                var query = session.createQuery("select c from Client c join fetch c.phones join fetch c.address where c.id=" + id, Client.class);
                Client client = query.getSingleResult();
                Optional<Client> result = Optional.of(client);
                cache.put(id, result);
                log.info("client: {}", client);
                return result;
            });
        }
        else {
            log.info("client returned from cache");
        }
        return returned;
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var query = session.createQuery("select distinct c from Client c join fetch c.phones  join fetch c.address", Client.class);
            List<Client> clients = query.getResultList();
            log.info("clientList:{}", clients);
            return clients;
        });
    }
}
