package com.demo.demo.entity;


import com.demo.demo.entity.identity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends IdEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String surName;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @ManyToMany( fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;




    /*Security*/
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialNonExpired = true;
    private Boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        System.out.println(getGrantedAuthorities(getRoleAndPermissionNames()).toString());
        return getGrantedAuthorities(getRoleAndPermissionNames());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private List<String> getRoleAndPermissionNames() {
        List<String> privillages = new ArrayList<>();
        for (Role role : roles) {
            privillages.add(role.getRoleName().name());
            if (role.getPermissions().size() != 0) {
                for (Permission permission : role.getPermissions()) {
                    privillages.add(permission.getName().name());
                }
            }
        }
        return  privillages;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
