package com.demo.demo.entity;

import com.demo.demo.entity.enums.PermissionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private  String description;

    @Enumerated(EnumType.STRING)
    private PermissionName name;


    public Permission(String description, PermissionName name) {
        this.description=description;
        this.name=name;
    }
}
