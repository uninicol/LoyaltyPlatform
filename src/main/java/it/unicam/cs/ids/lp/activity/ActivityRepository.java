package it.unicam.cs.ids.lp.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository
        extends JpaRepository<Activity, Long> {
    List<Activity> findByCard_Id(long id);

    long deleteByName(String name);
}
