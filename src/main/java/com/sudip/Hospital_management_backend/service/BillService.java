package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.entity.Bill;
import com.sudip.Hospital_management_backend.entity.BillStatus;
import com.sudip.Hospital_management_backend.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository bills;

    public List<Bill> findAll() {
        return bills.findAll();
    }

    public Bill findById(Long id) {
        return bills.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found with id: " + id
                        )
                );
    }

    public List<Bill> findByPatientId(Long patientId) {
        return bills.findByPatientId(patientId);
    }

    public Bill updateStatus(Long id, BillStatus status) {

        Bill bill = bills.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found with id: " + id
                        )
                );

        bill.setStatus(status);

        return bills.save(bill);
    }


    public Bill create(Bill bill) {

        if (bill.getInvoiceNumber() == null ||
                bill.getInvoiceNumber().isBlank()) {

            bill.setInvoiceNumber(
                    "INV-" + System.currentTimeMillis()
            );
        }

        if (bill.getBillDate() == null) {
            bill.setBillDate(LocalDate.now());
        }

        if (bill.getStatus() == null) {
            bill.setStatus(BillStatus.PENDING);
        }

        return bills.save(bill);
    }

    public void delete(Long id) {

        if (!bills.existsById(id)) {
            throw new RuntimeException(
                    "Bill not found with id: " + id
            );
        }

        bills.deleteById(id);
    }
}