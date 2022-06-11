package ua.services;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public class PublisherServiceImpl implements PublisherService {
    PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
    @Override
    public Map<String, String> signUp(UserSignUpDto userDto) {
        return null;
    }

    @Override
    public Publisher get(String email) {
        return null;
    }

    @Override
    public boolean updateRole(int id) {
        return false;
    }

    @Override
    public List<Publisher> getAll() {
        return publisherMySqlDao.getAll();
    }

    public List<Publisher> getByTopic(String topic) {
        return publisherMySqlDao.getByTopic(topic);
    }


}
