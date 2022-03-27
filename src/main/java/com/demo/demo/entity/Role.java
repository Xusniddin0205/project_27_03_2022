package com.demo.demo.entity;

import com.demo.demo.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String description;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Permission> permissions;

    public  Role(String description, RoleName roleName,Permission permission ) {
        this.description=description;
        this.roleName=roleName;
        this.permissions.add(permission);
    }
    public  Role(String description, RoleName roleName, Set<Permission> permission ) {
        this.description=description;
        this.roleName=roleName;
        this.permissions=permission;
    }
}
