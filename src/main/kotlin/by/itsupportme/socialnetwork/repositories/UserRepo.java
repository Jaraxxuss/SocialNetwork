package by.itsupportme.socialnetwork.repositories;

import by.itsupportme.socialnetwork.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User getAllById(Long id);
}
