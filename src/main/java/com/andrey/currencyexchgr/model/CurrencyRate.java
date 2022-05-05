package com.andrey.currencyexchgr.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency_info")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String charCode;

    private double value;
}
