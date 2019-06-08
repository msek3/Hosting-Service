package siwz.security;

import siwz.dao.UserDao;
import siwz.model.AppRole;
import siwz.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userDao.findUserByName(username);
        if(user == null)
            throw new UsernameNotFoundException("User: " + username + " not Found");

        Set<AppRole> userRoles = user.getRoles();
        List<GrantedAuthority> grantList = new ArrayList<>();
        if(!userRoles.isEmpty()){
            userRoles.forEach(role -> grantList.add(new SimpleGrantedAuthority(role.getName())));
        }

        return new User(user.getName(), user.getPassword(), grantList);
    }
}
