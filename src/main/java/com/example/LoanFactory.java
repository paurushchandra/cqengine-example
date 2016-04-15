package com.example;

import com.example.model.CollateralType;
import com.example.model.Loan;
import com.example.model.LoanStatus;
import org.apache.commons.lang.RandomStringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 14/4/16.
 */
public class LoanFactory {

    private static final AtomicInteger loanReferenceNumber = new AtomicInteger(0);
    private static final Random random = new Random();

    private static Loan getLoan(){
        Loan loan = new Loan();
        loan.setLoanRefrenceNumber(loanReferenceNumber.incrementAndGet());
        loan.setAssetId(RandomStringUtils.randomAlphabetic(4));
        loan.setAmount(Math.random() * 1000);
        loan.setOpenDate(Calendar.getInstance());
        loan.setEndDate(Calendar.getInstance());
        loan.setSettlementDate(Calendar.getInstance());
        loan.setPrice(Math.random());
        loan.setQuantity((int)Math.random() * 100);
        return loan;
    }

    public static Loan newOpenCashLoan(){
        Loan loan = getLoan();
        loan.setStatus(LoanStatus.OPEN);
        loan.setCollateralType(CollateralType.CASH);
        return  loan;
    }

    public static Loan newOpenNonCashLoan(){
        Loan loan = getLoan();
        loan.setStatus(LoanStatus.OPEN);
        loan.setCollateralType(CollateralType.NONCASH);
        return  loan;
    }

    public static Loan newClosedCashLoan(){
        Loan loan = getLoan();
        loan.setStatus(LoanStatus.CLOSED);
        loan.setCollateralType(CollateralType.CASH);
        return  loan;
    }

    public static Loan newClosedNonCashLoan(){
        Loan loan = getLoan();
        loan.setStatus(LoanStatus.CLOSED);
        loan.setCollateralType(CollateralType.NONCASH);
        return  loan;
    }

    public static Loan newRandomCombination(){
        int i = random.nextInt(5);
        switch (i){
            case 1: return newOpenCashLoan();
            case 2: return newClosedCashLoan();
            case 3: return newOpenNonCashLoan();
            case 4: return newClosedNonCashLoan();
            default: return newOpenNonCashLoan();
        }
    }

    public static Map<Integer, Loan> getTestData(int count){
        Map<Integer, Loan> loans = new HashMap<Integer, Loan>(count);
        for (int i = 0; i < count; i++) {
            Loan randomLoan = newRandomCombination();
            loans.put(randomLoan.getLoanRefrenceNumber(), newRandomCombination());
        }
        return loans;
    }


}
