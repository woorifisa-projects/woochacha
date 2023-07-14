package dev.market.spring_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.market.spring_market.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
