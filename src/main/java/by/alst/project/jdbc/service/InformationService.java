package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.InformationDao;
import by.alst.project.jdbc.dto.CreateInformationDto;
import by.alst.project.jdbc.dto.InformationDto;
import by.alst.project.jdbc.entity.Information;
import by.alst.project.jdbc.exception.ValidationException;
import by.alst.project.jdbc.mapper.CreateInformationMapper;
import by.alst.project.jdbc.validator.CreateInformationValidator;

import java.util.List;

public class InformationService {

    private static final InformationService INSTANCE = new InformationService();

    private InformationService() {
    }

    public static InformationService getInstance() {
        return INSTANCE;
    }

    private final InformationDao informationDao = InformationDao.getInstance();
    private final CreateInformationValidator createInformationValidator = CreateInformationValidator.getInstance();
    private final CreateInformationMapper createInformationMapper = CreateInformationMapper.getInstance();

    public List<InformationDto> findAll() {
        return informationDao.findAll().stream().map(information -> new InformationDto(information.getUsersId(),
                information.getFirstName(), information.getSecondName(), information.getLastName(),
                information.getAddress(), information.getPhone())).toList();
    }

    public InformationDto findById(Integer usersId) {
        Information information = informationDao.findById(usersId).orElse(new Information());
        return new InformationDto(information.getUsersId(), information.getFirstName(), information.getSecondName(),
                information.getLastName(), information.getAddress(), information.getPhone());
    }

    public boolean create(CreateInformationDto createInformationDto) {
        var validationResult = createInformationValidator.isValid(createInformationDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var information = createInformationMapper.mapFrom(createInformationDto);
        information = informationDao.save(information);
        return information.getUsersId().equals(createInformationDto.getUsersId());
    }
}
