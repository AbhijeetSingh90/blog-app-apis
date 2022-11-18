package blogappapis.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import blogappapis.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
