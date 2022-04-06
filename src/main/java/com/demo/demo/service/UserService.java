package com.demo.demo.service;

import com.demo.demo.dto.response.ApiResponseModel;
import com.demo.demo.entity.User;
import com.demo.demo.entity.enums.EntityStatus;
import com.demo.demo.repository.RoleRepository;
import com.demo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Cacheable(cacheNames = "users")
    public List<User> getAll() {
     //   waitSomeTime();
        return userRepository.findAllByState(EntityStatus.ACTIVE);
    }

       public Optional<User> userOpt(Integer id) {
        return userRepository.findById(id);
    }



    public ApiResponseModel checkUser(String userName) {
        waitSomeTime();
        ApiResponseModel result = new ApiResponseModel();
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            result.setSuccess(false);
            result.setObject(user.get());
            result.setMessage("Ushbu user mavjud");
            return result;
        }
        result.setSuccess(true);
        result.setMessage("ushbu user mavjud");
        return result;
    }

    private void waitSomeTime() {
        System.out.println("Long Wait Begin");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Long Wait End");
    }
/*
    public Result Edit(UserRequest userRequest, Long uuid) {
        Result result = new Result();
       Optional<User> userOptional=userRepository.findById(uuid);
       if (userOptional.isPresent()){
          User  user=userOptional.get();
          user.setUserName(userRequest.getUserName());
          user.setFullName(userRequest.getFullName());
          user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
           user.setRoles(adminSercive.getRoleListFromId(userRequest.getList()));
           userRepository.save(user);

       }

        result.setMessage("User saqlandi");
        result.setSuccess(true);
        return result;
    }

    public Result EditSelf(UserEdit edit, User userSelf) {
        Result result = new Result();
        Optional<User> userOptional=userRepository.findById(userSelf.getId());
        if (userOptional.isPresent()){
            User  user=userOptional.get();
            user.setUserName(edit.getUserName());
            user.setFullName(edit.getFullName());
            user.setPassword(passwordEncoder.encode(edit.getPassword()));
            user.setRoles(userSelf.getRoles());
            userRepository.save(user);
            result.setMessage("Sizning o'zagrtirishingiz saqlandi");
            result.setSuccess(true);
            return result;
        }

        result.setMessage("Kechirasiz xatolik sodir bo'ldi");
        result.setSuccess(false);
        return result;
    }

    public Result delete(Long id) {
        userRepository.deleteById(id);
        return new Result(true, "User deleted");
    }


    public Result create(UserRequest userRequest) {
        Result result = new Result();
        User user=new User();
        user.setFullName(userRequest.getFullName());
        user.setUserName(userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(adminSercive.getRoleListFromId(userRequest.getList()));
       try {
           userRepository.save(user);
           result.setMessage("User saqlandi");
           result.setSuccess(true);
       }catch (Exception e)
       {
           result.setSuccess(false);
           result.setMessage(e.getMessage());
       }

        return result;

    }


    public User getUser(Long uuid) {
        Result result = new Result();
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {

            return user.get();
        }
        return user.get();
    }



public Page<User> getUserPage(int page, int size){

    Pageable pageable= PageRequest.of(page,size, Sort.Direction.ASC ,"createdAt");
    return  userRepository.findAll(pageable);
}

 */




}
