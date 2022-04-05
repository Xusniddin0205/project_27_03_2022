package com.demo.demo.repository;


import com.demo.demo.entity.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog,Long> {


    @Query("SELECT p FROM UserLog p WHERE concat(p.id,'') LIKE %?1%"
            + " and lower(p.username) LIKE %?2%"
            + " and lower(p.ipAdrees) LIKE %?3%"
            + " and concat(p.auditType,'') LIKE %?4%"
                        +" order by p.id DESC ")
    Page<UserLog> searchModel(String id, String username,
                              String ipaddress, String auditype,
                              Pageable pageable);
}
