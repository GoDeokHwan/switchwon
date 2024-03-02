package com.example.switchwonapi.model.payment.mapper;

import com.example.switchwonapi.domain.payment.controller.response.PaymentEstimateResponse;
import com.example.switchwonapi.model.payment.PaymentEstimateLogsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE
        , unmappedSourcePolicy = ReportingPolicy.IGNORE
        , typeConversionPolicy = ReportingPolicy.IGNORE)
public interface PaymentEstimateLogsMapper {
    PaymentEstimateResponse toPaymentEstimateResponse(PaymentEstimateLogsEntity entity);
}
