package com.demo.demo.entity;

import com.demo.demo.entity.enums.AuditType;
import com.demo.demo.entity.identity.IdEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserLog extends IdEntity {

    private String username;
    private String ipAdrees;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;



    @Enumerated(EnumType.STRING)
    private AuditType auditType;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
