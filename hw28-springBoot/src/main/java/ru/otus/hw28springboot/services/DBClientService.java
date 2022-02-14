package ru.otus.hw28springboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw28springboot.domain.entity.Address;
import ru.otus.hw28springboot.domain.entity.Client;
import ru.otus.hw28springboot.domain.entity.Phone;
import ru.otus.hw28springboot.domain.repository.AddressRepository;
import ru.otus.hw28springboot.domain.repository.ClientRepository;
import ru.otus.hw28springboot.domain.repository.PhoneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBClientService {
    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Optional<Client> save(Map<String, Object> param) {
        var client = deserializeClient(param);
        return Optional.of(client);
    }

    private Client deserializeClient(Map<String, Object> param) {
        var client = clientRepository.save(new Client(null, param.get("name").toString()));
        var address = addressRepository.save(new Address(null, param.get("address").toString(), client.getId()));
        var phones = (ArrayList<String>) param.get("phones");
        List<Phone> phoneList = new ArrayList<Phone>();
        for (var phone :
                phones) {
            phoneList.add(phoneRepository.save(new Phone(null, phone, client.getId())));
        }
        client.setAddress(address);
        client.setPhones(phoneList);
        return client;
    }

}
