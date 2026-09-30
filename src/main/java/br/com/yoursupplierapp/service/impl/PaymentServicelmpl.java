package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.PaymentRequest;
import br.com.yoursupplierapp.api.model.PaymentResponse;
import br.com.yoursupplierapp.entity.PaymentEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.PaymentMapper;
import br.com.yoursupplierapp.repository.PaymentRepository;
import br.com.yoursupplierapp.service.PaymentService;
import br.com.yoursupplierapp.utils.PaymentConstant;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static br.com.yoursupplierapp.utils.ConstantUtils.DUPLICATED_PAYMENT;

@Service
public class PaymentServicelmpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServicelmpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public void createPayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getNumCard() != null && paymentRepository.findPaymentByNumCard(paymentRequest.getNumCard()).isPresent()) {
            throw new BusinessException("Card number " + paymentRequest.getNumCard() + " already registered in the system!");
        }

        try {
            PaymentEntity paymentEntity = paymentMapper.toEntity(paymentRequest);
            paymentRepository.save(paymentEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(DUPLICATED_PAYMENT);
        }
    }

    @Override
    public List<PaymentResponse> listPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse findPaymentById(Long idPayment) {
        PaymentEntity payment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new BusinessException("Payment with ID: " + idPayment + " not found in the system!"));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public void updatePayment(Long id, PaymentRequest paymentRequest) {
        PaymentEntity existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Payment id number: " + id + " not found in system!"));

        if (StringUtils.hasText(paymentRequest.getNumCard())) {
            existingPayment.setNumCard(paymentRequest.getNumCard());
        }
        if (paymentRequest.getPaymentValue() != null) {
            existingPayment.setPaymentValue(paymentRequest.getPaymentValue());
        }
        if (StringUtils.hasText(paymentRequest.getExpirationDate())) {
            existingPayment.setExpirationDate(paymentRequest.getExpirationDate());
        }
        if (StringUtils.hasText(paymentRequest.getCvv())) {
            existingPayment.setCvv(paymentRequest.getCvv());
        }
        if (paymentRequest.getPaymentConstant() != null) {
            existingPayment.setPaymentConstant(PaymentConstant.valueOf(paymentRequest.getPaymentConstant().getValue()));
        }

        paymentRepository.save(existingPayment);
    }

    @Override
    public void deleteById(Long id) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Payment id number: " + id + " not found in system"));
        paymentRepository.delete(payment);
    }
}
