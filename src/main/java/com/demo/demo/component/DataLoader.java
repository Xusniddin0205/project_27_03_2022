package com.demo.demo.component;

import com.demo.demo.entity.Permission;
import com.demo.demo.entity.Role;
import com.demo.demo.entity.User;
import com.demo.demo.entity.enums.PermissionName;
import com.demo.demo.entity.enums.RoleName;
import com.demo.demo.repository.PermissionRepository;
import com.demo.demo.repository.RoleRepository;
import com.demo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;
    @Override
    public void run(String... args) throws Exception {

        /*for (int i=0;i<=100000;i++){
            User userAdmin=new User();
            userAdmin.setLastName("Xushbaqtov-"+i);
            userAdmin.setName("Shamsiddin"+i);
            userAdmin.setUserName("shamsiddin"+i);
            userAdmin.setSurName("Rahim o'g'li");
            userAdmin.setRoles(new HashSet<>(Arrays.asList(roleRepository.getById(1))));
            userAdmin.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(userAdmin);
        }*/

       if (initMode.equals("always")) {

           Permission permissionS1 = permissionRepository
                   .save(new Permission("User yaratish", PermissionName.USER_CREATE));
           Permission permissionS2 = permissionRepository
                   .save(new Permission("Userni o'zgartirish", PermissionName.USER_EDIT));

           Permission permissionS3 = permissionRepository
                   .save(new Permission("Userni o'chireish", PermissionName.USER_DELETE));

           Permission permissionS4 = permissionRepository
                   .save(new Permission("Userni Ko'rish", PermissionName.USER_VIEW));

           Set<Permission> permissions = new HashSet<>();
           permissions.add(permissionS1);
           permissions.add(permissionS2);
           permissions.add(permissionS3);
           permissions.add(permissionS4);
           Role roleAdmin = roleRepository.save(
                   new Role("Administrator", RoleName.ROLE_ADMIN, permissions));

           User userAdmin=new User();
           userAdmin.setLastName("Xushbaqtov");
           userAdmin.setName("Shamsiddin");
           userAdmin.setUserName("shamsiddin");
           userAdmin.setSurName("Rahim o'g'li");
           userAdmin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
           userAdmin.setPassword(passwordEncoder.encode("12345"));
          userRepository.save(userAdmin);



           Set<Permission> permissionsM = new HashSet<>();
           permissionsM.add(permissionS1);
           permissionsM.add(permissionS4);


           Role roleModerator = roleRepository
                   .save(new Role("Moderator", RoleName.ROLE_MODERATOR, permissionsM));

           User userModer=new User();
           userModer.setLastName("Xushbaqtov");
           userModer.setName("Xusniddin");
           userModer.setUserName("xusniddin");
           userModer.setSurName("Rahim o'g'li");
           userModer.setRoles(new HashSet<>(Arrays.asList(roleModerator)));
           userModer.setPassword(passwordEncoder.encode("12345"));
           userRepository.save(userModer);



       }
    }
}
