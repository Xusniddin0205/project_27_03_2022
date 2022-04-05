package com.demo.demo.security.audit;

import com.demo.demo.entity.User;
import com.demo.demo.entity.UserLog;
import com.demo.demo.entity.enums.AuditType;
import com.demo.demo.repository.UserLogRepository;
import com.demo.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogRepository userLogRepository;


    private static final Logger LOG = LoggerFactory.getLogger(LoginListener.class);
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        UserDetails ud = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
        UserLog userLog=new UserLog();
        userLog.setUsername(ud.getUsername());
        userLog.setIpAdrees(request.getRemoteAddr());
        userLog.setAuditType(AuditType.KIRISH);
        userLog.setUser((User) userService.checkUser(ud.getUsername()).getObject());
        userLogRepository.save(userLog);



        LOG.info(" Kirish User " + ud.getUsername() + " logged in successfully");
    }
}
