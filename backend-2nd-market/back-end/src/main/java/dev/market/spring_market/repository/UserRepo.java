package dev.market.spring_market.repository;


import dev.market.spring_market.entity.Product;
import dev.market.spring_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public User findByUserEmail(String email);

}
