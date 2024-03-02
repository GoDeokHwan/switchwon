package com.example.switchwonapi.model.user.mapper;

import com.example.switchwonapi.domain.payment.controller.response.PaymentBalanceResponse;
import com.example.switchwonapi.model.user.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE
        , unmappedSourcePolicy = ReportingPolicy.IGNORE
        , typeConversionPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "paymentAccount.balance", target = "balance")
    @Mapping(source = "paymentAccount.currency", target = "currency")
    PaymentBalanceResponse toPaymentBalanceResponse(UsersEntity entity);
}
