package com.example.demo;

import java.util.List;


import java.util.ArrayList;
import java.util.Optional;

import com.example.data.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.Users;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers()
    {
        List<Users>userRecords = new ArrayList<>();
        usersRepository.findAll().forEach(userRecords::add);
        return userRecords;
    }

    public void addUser(Users user)
    {
        System.out.println(user);
        usersRepository.save(user);
    }

    public Optional<Users> getUserByEmail(String email)
    {
        return usersRepository.findById(email);
    }


    public void deleteUser(String email)
    {
        usersRepository.deleteById(email);
    }

}
