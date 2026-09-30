package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.PaymentRequest;
import br.com.yoursupplierapp.api.model.PaymentResponse;
import br.com.yoursupplierapp.api.model.PaymentType;
import br.com.yoursupplierapp.entity.PaymentEntity;
import br.com.yoursupplierapp.utils.PaymentConstant;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentEntity toEntity(PaymentRequest request) {
        if (request == null) {
            return null;
        }
        PaymentEntity entity = new PaymentEntity();
        if (request.getPaymentConstant() != null) {
            entity.setPaymentConstant(PaymentConstant.valueOf(request.getPaymentConstant().getValue()));
        }
        entity.setNumCard(request.getNumCard());
        entity.setCvv(request.getCvv());
        entity.setExpirationDate(request.getExpirationDate());
        entity.setPaymentValue(request.getPaymentValue());
        return entity;
    }

    public PaymentResponse toResponse(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }
        PaymentResponse response = new PaymentResponse();
        response.setIdPayment(entity.getIdPayment());
        if (entity.getPaymentConstant() != null) {
            response.setPaymentConstant(PaymentType.fromValue(entity.getPaymentConstant().name()));
        }
        response.setNumCard(entity.getNumCard());
        response.setCvv(entity.getCvv());
        response.setExpirationDate(entity.getExpirationDate());
        response.setPaymentValue(entity.getPaymentValue());
        if (entity.getOrder() != null) {
            response.setOrderId(entity.getOrder().getIdOrder());
        }
        return response;
    }
}
