package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.repository.UserRepository;
import com.gpsolutions.todolist.util.ExceptionUtil;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(final int id) {
        final User user = userRepository.findOne(id);
        ExceptionUtil.checkNotNull(id, user, User.class);
        return user;
    }

    @Override
    public User create(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(final int id, final User user) {
        final User original = get(id);
        if (!passwordEncoder.matches(user.getPassword(), original.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(original.getPassword());
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(final int id) {
        userRepository.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return user;
    }
}
