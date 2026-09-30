package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.PaymentApi;
import br.com.yoursupplierapp.api.model.PaymentRequest;
import br.com.yoursupplierapp.api.model.PaymentResponse;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<String> createPayment(PaymentRequest paymentRequest) {
        try {
            paymentService.createPayment(paymentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("payment created with success");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Error creating payment: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<PaymentResponse>> listPayments() {
        return ResponseEntity.ok(paymentService.listPayments());
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentById(Long id) {
        try {
            return ResponseEntity.ok(paymentService.findPaymentById(id));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<String> updatePaymentById(Long id, PaymentRequest paymentRequest) {
        try {
            paymentService.updatePayment(id, paymentRequest);
            return ResponseEntity.ok("payment updated successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating payment: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deletePaymentById(Long id) {
        try {
            paymentService.deleteById(id);
            return ResponseEntity.ok("payment removed successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting payment: " + e.getMessage());
        }
    }
}
