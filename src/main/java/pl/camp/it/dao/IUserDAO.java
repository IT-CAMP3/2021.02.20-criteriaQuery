package pl.camp.it.dao;

import pl.camp.it.model.User;

public interface IUserDAO {
    void persist(User user);
    User getUserById(int id);
}
