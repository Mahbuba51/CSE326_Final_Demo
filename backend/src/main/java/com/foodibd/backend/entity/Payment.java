package com.foodibd.backend.entity;

import com.foodibd.backend.entity.enums.PaymentOption;
import com.foodibd.backend.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentOption method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double amount;
    private String transactionRef;
}
