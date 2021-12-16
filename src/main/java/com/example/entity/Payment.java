package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "PAYMENT")
@SequenceGenerator(name = "SEQ_PAYMENT_NO", sequenceName = "SEQ_PAYMENT_NO", initialValue = 1, allocationSize = 1)
public class Payment {
    @Id
    @Column(name = "PAYMENT_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYMENT")
    private String paymentno = null;

    @Column(name = "PAYMENT METTHOD")
    private String productmethod = null;

    @Column(name = "PAYMENT_DATE")
    private Date paymentdate = null;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO")
    private Order order;
}
