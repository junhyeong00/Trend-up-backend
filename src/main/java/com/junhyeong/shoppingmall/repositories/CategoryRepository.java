package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
