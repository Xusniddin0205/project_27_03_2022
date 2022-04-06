package com.demo.demo.entity;

import com.demo.demo.entity.enums.RoleName;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String description;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission",joinColumns = {@JoinColumn(name = "role_id")}
    ,inverseJoinColumns ={@JoinColumn(name = "permission_id")} )
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
