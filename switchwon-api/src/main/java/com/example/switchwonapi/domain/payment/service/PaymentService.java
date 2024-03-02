package com.example.switchwonapi.domain.payment.service;

import com.example.switchwonapi.domain.payment.controller.request.PaymentApprovalRequest;
import com.example.switchwonapi.domain.payment.controller.request.PaymentEstimateRequest;
import com.example.switchwonapi.domain.payment.controller.response.PaymentApprovalResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentBalanceResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentEstimateResponse;
import com.example.switchwonapi.model.merchant.MerchantsEntity;
import com.example.switchwonapi.model.merchant.repository.MerchantsRepository;
import com.example.switchwonapi.model.payment.*;
import com.example.switchwonapi.model.payment.mapper.PaymentEstimateLogsMapper;
import com.example.switchwonapi.model.payment.mapper.PaymentsMapper;
import com.example.switchwonapi.model.payment.repository.*;
import com.example.switchwonapi.model.policy.PolicyFeesEntity;
import com.example.switchwonapi.model.policy.repository.PolicyFeesRepository;
import com.example.switchwonapi.model.user.UsersEntity;
import com.example.switchwonapi.model.user.mapper.UserMapper;
import com.example.switchwonapi.model.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PaymentService {
    private final UsersRepository usersRepository;
    private final MerchantsRepository merchantsRepository;
    private final PaymentsRepository paymentsRepository;
    private final PaymentLogsRepository paymentLogsRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final PaymentEstimateLogsRepository paymentEstimateLogsRepository;
    private final PaymentAccountsRepository paymentAccountsRepository;
    private final PolicyFeesRepository policyFeesRepository;

    private final UserMapper userMapper;
    private final PaymentEstimateLogsMapper paymentEstimateLogsMapper;
    private final PaymentsMapper paymentsMapper;

    public PaymentBalanceResponse getPaymentBalance(Long userId) {
        UsersEntity user = usersRepository.findByIdWithPaymentAccounts(userId)
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        return userMapper.toPaymentBalanceResponse(user);
    }

    public PaymentEstimateResponse savePaymentEstimate(PaymentEstimateRequest request) {
        UsersEntity user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        MerchantsEntity merchant = merchantsRepository.findById(request.getDestination())
                .orElseThrow(() -> new NotFoundException("상점을 찾을 수 없습니다."));

        LocalDateTime now = LocalDateTime.now();
        Optional<PolicyFeesEntity> policyFees = policyFeesRepository.findTopByStartDateIsLessThanEqualAndEndDateIsGreaterThanEqualOrderByStartDateDesc(now, now);

        PaymentEstimateLogsEntity estimateLogs = PaymentEstimateLogsEntity.of(user, merchant, request.getAmount(), request.getCurrency(), policyFees);
        paymentEstimateLogsRepository.save(estimateLogs);
        return paymentEstimateLogsMapper.toPaymentEstimateResponse(estimateLogs);
    }

    public PaymentApprovalResponse savePaymentApproval(PaymentApprovalRequest request) {
        // 사용자 조회
        UsersEntity user = usersRepository.findByIdWithPaymentAccountsAndMethod(request.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        // 마켓 조회
        MerchantsEntity merchant = merchantsRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new NotFoundException("상점을 찾을 수 없습니다."));

        // 사용자에 결제 수단 정보 조회 또는 생성
        PaymentMethodsEntity paymentMethodsEntity = user.findPaymentMethod(
                request.getPaymentMethod()
                , request.getPaymentDetails().getCardNumber()
                , request.getPaymentDetails().getExpiryDate()
                , request.getPaymentDetails().getCvv()
        ).orElseGet(() -> {
            PaymentMethodsEntity paymentMethods = user.addPaymentMethods(
                    request.getPaymentMethod()
                    , request.getPaymentDetails().getCardNumber()
                    , request.getPaymentDetails().getExpiryDate()
                    , request.getPaymentDetails().getCvv()
            );
            paymentMethodsRepository.save(paymentMethods);
            return paymentMethods;
        });

        // 결제 정보 없으면 생성
        PaymentAccountsEntity paymentAccount = user.getPaymentAccount();
        if (ObjectUtils.isEmpty(paymentAccount)) {
            paymentAccount = PaymentAccountsEntity.of(user);
        }

        PaymentsEntity payments = PaymentsEntity.ofPayment(user, paymentAccount, merchant, request.getAmount(), request.getCurrency());
        List<PaymentLogsEntity> paymentLogs = new ArrayList<>();
        try {
            double chargeAmount = request.getAmount() - paymentAccount.getBalance();
            // 충전된 금액 소모
            if (paymentAccount.getBalance() > 0) {
                paymentLogs.add(
                        PaymentLogsEntity.ofPayment(payments, merchant, paymentMethodsEntity, paymentAccount.getBalance(), paymentAccount.getCurrency())
                );
            }
            // 부족 부분 충전
            if (chargeAmount > 0) {
                paymentLogs.add(
                        PaymentLogsEntity.ofCharge(payments, merchant, paymentMethodsEntity, chargeAmount, request.getCurrency())
                );
            }
            // 부족 부분 소모
            if (chargeAmount > 0) {
                paymentLogs.add(
                        PaymentLogsEntity.ofPayment(payments, merchant, paymentMethodsEntity, chargeAmount, request.getCurrency())
                );
            }
            payments.updatePaymentLog(paymentLogs);
            paymentAccountsRepository.save(paymentAccount);
        } catch (Exception e) {
            payments.updateFail();
            paymentLogs.forEach(PaymentLogsEntity::updateFail);
            paymentsRepository.save(payments);
            paymentLogsRepository.saveAll(paymentLogs);
        } finally {
            payments.updateApproval();
            paymentLogs.forEach(PaymentLogsEntity::updateApproval);
            paymentsRepository.save(payments);
            paymentLogsRepository.saveAll(paymentLogs);
        }
        return paymentsMapper.toPaymentApprovalResponse(payments);
    }
}
