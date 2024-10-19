package com.mgs.Pages.RestPage.POJO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class CustomerProfile {
    private String customerName;
    private String riskCapacity;
    private String investmentStrategy;
    private int totalInvestment;
    private int maturityAmount;
    private ArrayList<DepositDetail> depositDetails;

    @Getter
    @Setter
    public static class DepositDetail {
        private long principle;
        private long rateOfInterest;
        private long period;
        private String interestType;
        private String periodType;
        private double maturityValue;
        private BankDetails bankDetails;
        private Audits audits;
        private History history;
        private AdditionalInfo additionalInfo;

        @Getter
        @Setter
        public static class BankDetails {
            private String ifsc;
            private String branch;
            private String name;
            private int accountNumber;
        }

        @Getter
        @Setter
        public static class Audits {
            private Review review;
            private Approval approval;

            @Getter
            @Setter
            public static class Review {
                private String auditor;
                private String remarks;
                private String date;
            }

            @Getter
            @Setter
            public static class Approval {
                private String auditor;
                private String remarks;
                private String date;
            }
        }

        @Getter
        @Setter
        public static class History {
            private ArrayList<String> transactionIDs;
            private Date lastUpdated;
            private ArrayList<Status> statuses;

            @Getter
            @Setter
            public static class Status {
                private String status;
                private String updatedBy;
                private String date;
            }
        }

        @Getter
        @Setter
        public static class AdditionalInfo {
            @JsonProperty("isVerified")  // This allows JSON to map to "verified"
            private boolean verified;
            private String comments;
        }
    }
}
