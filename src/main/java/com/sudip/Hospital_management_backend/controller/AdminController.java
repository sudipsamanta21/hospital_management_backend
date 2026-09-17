package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Department;
import com.sudip.Hospital_management_backend.entity.Medicine;
import com.sudip.Hospital_management_backend.entity.Room;
import com.sudip.Hospital_management_backend.entity.User;

import com.sudip.Hospital_management_backend.repository.DepartmentRepository;
import com.sudip.Hospital_management_backend.repository.MedicineRepository;
import com.sudip.Hospital_management_backend.repository.RoomRepository;
import com.sudip.Hospital_management_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository users;
    private final DepartmentRepository departments;
    private final RoomRepository rooms;
    private final MedicineRepository medicines;

    @GetMapping("/users")
    public List<User> users(){
        return users.findAll();
    }

    @GetMapping("/departments")
    public List<Department> departments(){
        return departments.findAll();
    }

    @PostMapping("/departments")
    public Department department(@RequestBody Department d){
        return departments.save(d);
    }

    @DeleteMapping("/departments/{id}") public void deleteDepartment(@PathVariable Long id){
        departments.deleteById(id);
    }

    @GetMapping("/rooms")
    public List<Room> rooms(){return rooms.findAll();
    }

    @PostMapping("/rooms") public Room room(@RequestBody Room r){
        return rooms.save(r);
    }

    @GetMapping("/medicines")
    public List<Medicine> medicines(){
        return medicines.findAll();
    }

    @PostMapping("/medicines")
    public Medicine medicine(@RequestBody Medicine m){
        return medicines.save(m);
    }
}
