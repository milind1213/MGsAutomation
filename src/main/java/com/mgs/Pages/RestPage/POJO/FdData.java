package com.mgs.Pages.RestPage.POJO;

public class FdData {
    private int principle;  // Lowercase 'p'
    private int rateOfInterest;  // Lowercase 'r'
    private int period;  // Lowercase 'p'
    private String periodType;  // Lowercase 'p'
    private String frequency;  // Lowercase 'f'
    private double maturityValue;  // Lowercase 'm'
    private String expected;  // Lowercase 'e'
    private String result;  // Lowercase 'r'

    // Getters and setters
    public int getPrinciple() {
        return principle;
    }

    public void setPrinciple(int principle) {
        this.principle = principle;
    }

    public int getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(int rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getMaturityValue() {
        return maturityValue;
    }

    public void setMaturityValue(double maturityValue) {
        this.maturityValue = maturityValue;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
    }
}
