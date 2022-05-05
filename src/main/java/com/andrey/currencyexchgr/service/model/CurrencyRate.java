package com.andrey.currencyexchgr.service.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "currency_info")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_rate_sequence")
    private Long id;

    @Column(name = "charcode")
    private String charCode;
    @Column(name = "value")
    private double value;
}
