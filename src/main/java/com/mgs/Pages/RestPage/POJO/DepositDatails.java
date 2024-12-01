package com.mgs.Pages.RestPage.POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositDatails {
    private int principle;  // Lowercase 'p'
    private int rateOfInterest;  // Lowercase 'r'
    private int period;  // Lowercase 'p'
    private String periodType;  // Lowercase 'p'
    private String frequency;  // Lowercase 'f'
    private double maturityValue;  // Lowercase 'm'
    private String expected;  // Lowercase 'e'
    private String result;  // Lowercase 'r'
/*
    @Override
    public String toString() {
        return "FdData{" +
                "principle=" + principle +
                ", rateOfInterest=" + rateOfInterest +
                ", period=" + period +
                ", periodType='" + periodType + '\'' +
                ", frequency='" + frequency + '\'' +
                ", maturityValue=" + maturityValue +
                ", expected='" + expected + '\'' +
                ", result='" + result + '\'' +
                '}';
    }*/
}
