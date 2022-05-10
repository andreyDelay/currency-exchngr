package com.andrey.currencyexchgr.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "currency_rates")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_rates_sequence")
    private Long id;
    private String charCode;
    private double value;
}
