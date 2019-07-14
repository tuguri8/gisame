package me.gisa.api.daum.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaumNewsRepository extends JpaRepository<DaumNews, Long> {
}
