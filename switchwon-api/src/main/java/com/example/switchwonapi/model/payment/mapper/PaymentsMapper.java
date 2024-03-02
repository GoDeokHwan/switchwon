package com.example.switchwonapi.model.payment.mapper;

import com.example.switchwonapi.domain.payment.controller.response.PaymentApprovalResponse;
import com.example.switchwonapi.model.payment.PaymentsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE
        , unmappedSourcePolicy = ReportingPolicy.IGNORE
        , typeConversionPolicy = ReportingPolicy.IGNORE)
public interface PaymentsMapper {
    @Mapping(source = "id", target = "paymentId")
    PaymentApprovalResponse toPaymentApprovalResponse(PaymentsEntity entity);
}
