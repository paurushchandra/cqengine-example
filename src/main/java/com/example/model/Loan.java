package com.example.model;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

import java.util.Calendar;

/**
 * Created by user on 14/4/16.
 */
public class Loan {

    public static final Attribute<Loan,Integer> LOAN_REFERENCE_NUMBER = new SimpleAttribute<Loan, Integer>() {
        @Override
        public Integer getValue(Loan loan, QueryOptions queryOptions) {
            return loan.getLoanRefrenceNumber();
        }
    };

    public static final Attribute<Loan,CollateralType> COLLATREAL_TYPE = new SimpleAttribute<Loan, CollateralType>() {
        @Override
        public CollateralType getValue(Loan loan, QueryOptions queryOptions) {
            return loan.getCollateralType();
        }
    };

    public static final Attribute<Loan,LoanStatus> STATUS = new SimpleAttribute<Loan, LoanStatus>() {
        @Override
        public LoanStatus getValue(Loan loan, QueryOptions queryOptions) {
            return loan.getStatus();
        }
    };

    public static final Attribute<Loan,Double> AMOUNT = new SimpleAttribute<Loan, Double>() {
        @Override
        public Double getValue(Loan loan, QueryOptions queryOptions) {
            return loan.getAmount();
        }
    };

    public static final Attribute<Loan,String> ASSET_ID = new SimpleAttribute<Loan, String>() {
        @Override
        public String getValue(Loan loan, QueryOptions queryOptions) {
            return loan.getAssetId();
        }
    };


    private int loanRefrenceNumber;
    private String assetId;
    private Calendar openDate;
    private Calendar settlementDate;
    private Calendar endDate;
    private int quantity;
    private double price;
    private double amount;
    private CollateralType collateralType;
    private LoanStatus status;

    public int getLoanRefrenceNumber() {
        return loanRefrenceNumber;
    }

    public void setLoanRefrenceNumber(int loanRefrenceNumber) {
        this.loanRefrenceNumber = loanRefrenceNumber;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public Calendar getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Calendar settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CollateralType getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(CollateralType collateralType) {
        this.collateralType = collateralType;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
