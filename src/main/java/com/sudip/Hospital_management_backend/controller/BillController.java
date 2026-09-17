package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Bill;
import com.sudip.Hospital_management_backend.entity.BillStatus;
import com.sudip.Hospital_management_backend.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping
    public List<Bill> getAllBills() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Bill getBill(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<Bill> getPatientBills(@PathVariable Long patientId) {
        return service.findByPatientId(patientId);
    }

    @PutMapping("/{id}/status")
    public Bill updateStatus(
            @PathVariable Long id,
            @RequestParam BillStatus status
    ) {
        return service.updateStatus(id, status);
    }



    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        return service.create(bill);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        service.delete(id);
    }
}