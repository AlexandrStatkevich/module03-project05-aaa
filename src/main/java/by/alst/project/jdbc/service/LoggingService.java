package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.LoggingDao;
import by.alst.project.jdbc.dto.LoggingDto;
import by.alst.project.jdbc.entity.Logging;

import java.util.List;

public class LoggingService {

    private static final LoggingService INSTANCE = new LoggingService();

    private LoggingService() {
    }

    public static LoggingService getInstance() {
        return INSTANCE;
    }

    private final LoggingDao loggingDao = LoggingDao.getInstance();

    public List<LoggingDto> findAll() {
        return loggingDao.findAll().stream().map(logging -> new LoggingDto(logging.getId(), logging.getUsersId(),
                logging.getUsersAuthenticationTime(), logging.getUsersLogOutTime())).toList();
    }

    public LoggingDto findById(Integer loggingId) {
        Logging logging = loggingDao.findById(loggingId).orElse(new Logging());
        return new LoggingDto(logging.getId(), logging.getUsersId(),
                logging.getUsersAuthenticationTime(), logging.getUsersLogOutTime());
    }
}
