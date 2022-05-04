package com.andrey.currencyexchgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "currency_info")
public class CurrencyRate extends BaseEntity {

    @Column(name = "currency_code")
    private String charCode;

    @Column(name = "currency_rate")
    private double value;
}
