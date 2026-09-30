package br.com.yoursupplierapp.service;

import br.com.yoursupplierapp.api.model.PaymentRequest;
import br.com.yoursupplierapp.api.model.PaymentResponse;

import java.util.List;

public interface PaymentService {

    void createPayment(PaymentRequest paymentRequest);

    List<PaymentResponse> listPayments();

    PaymentResponse findPaymentById(Long idPayment);

    void updatePayment(Long id, PaymentRequest paymentRequest);

    void deleteById(Long id);
}
