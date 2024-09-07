package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.ProducerDao;
import by.alst.project.jdbc.dto.ProducerDto;
import by.alst.project.jdbc.entity.Producer;

import java.util.List;

public class ProducerService {

    private static final ProducerService INSTANCE = new ProducerService();

    private ProducerService() {
    }

    public static ProducerService getInstance() {
        return INSTANCE;
    }

    private final ProducerDao producerDao = ProducerDao.getInstance();

    public List<ProducerDto> findAll() {
        return producerDao.findAll().stream().map(producer -> new ProducerDto(producer.getId(),
                producer.getProducerName(), producer.getProducerAddress())).toList();
    }

    public ProducerDto findById(Integer producerId) {
        Producer producer = producerDao.findById(producerId).orElse(new Producer());
        return new ProducerDto(producer.getId(), producer.getProducerName(), producer.getProducerAddress());
    }
}
