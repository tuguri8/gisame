package me.gisa.api.rss.repository;

import me.gisa.api.rss.repository.entity.Newsfromrss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsfromrssRepository extends JpaRepository<Newsfromrss,Long> {
    List<Newsfromrss> findByRegionNameContaining(String regionName);
}
