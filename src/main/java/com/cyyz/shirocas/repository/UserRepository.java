package com.cyyz.shirocas.repository;

import com.cyyz.shirocas.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2016/1/25.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
}
