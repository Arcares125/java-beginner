package com.learning.java_learning.repository;

import com.learning.java_learning.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
} 