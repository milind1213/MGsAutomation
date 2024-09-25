package com.mgs.Pages.RestPage;

public class FdData {
    private int Principle;
    private int RateOfInterest;
    private int Period;
    private String PeriodType;
    private String Frequency;
    private double MaturityValue;
    private String Expected;
    private String Result;

    public int getRateOfInterest() {
        return RateOfInterest;
    }

    public void setRateOfInterest(int rateOfInterest) {
        RateOfInterest = rateOfInterest;
    }

    public int getPrinciple() {
        return Principle;
    }

    public void setPrinciple(int principle) {
        Principle = principle;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int period) {
        Period = period;
    }

    public String getPeriodType() {
        return PeriodType;
    }

    public void setPeriodType(String periodType) {
        PeriodType = periodType;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public double getMaturityValue() {
        return MaturityValue;
    }

    public void setMaturityValue(double maturityValue) {
        MaturityValue = maturityValue;
    }

    public String getExpected() {
        return Expected;
    }

    public void setExpected(String expected) {
        Expected = expected;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "FdData{" +
                "Principle=" + Principle +
                ", RateOfInterest=" + RateOfInterest +
                ", Period=" + Period +
                ", PeriodType='" + PeriodType + '\'' +
                ", Frequency='" + Frequency + '\'' +
                ", MaturityValue=" + MaturityValue +
                ", Expected='" + Expected + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }

}
