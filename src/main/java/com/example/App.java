package com.example;

import com.example.model.CollateralType;
import com.example.model.Loan;
import com.example.model.LoanStatus;
import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.suffix.SuffixTreeIndex;
import com.googlecode.cqengine.index.unique.UniqueIndex;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.resultset.ResultSet;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    private static final int TEST_DATA_COUNT = 1000000;
    private static final int BENCHMARK_INTERVAL = 1;
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("starting harness");
        Map<Integer, Loan> testData = LoanFactory.getTestData(TEST_DATA_COUNT);

        IndexedCollection<Loan> indexedCollection = new ConcurrentIndexedCollection<Loan>();
        indexedCollection.addIndex(UniqueIndex.onAttribute(Loan.LOAN_REFERENCE_NUMBER));
        indexedCollection.addIndex(NavigableIndex.onAttribute(Loan.COLLATREAL_TYPE));
        indexedCollection.addIndex(NavigableIndex.onAttribute(Loan.STATUS));
        indexedCollection.addIndex(NavigableIndex.onAttribute(Loan.AMOUNT));
        indexedCollection.addIndex(SuffixTreeIndex.onAttribute(Loan.ASSET_ID));
        populateCollection(testData.values(), indexedCollection);

        LOGGER.info("benchmarking hashmap search by key");
        benchMarkHashMapSearchByKey(testData);

        LOGGER.info("benchmarking equals for unique index collection");
        benchMarkEqualsUniqueIndex(indexedCollection);

        LOGGER.info("benchmarking equals on Navigable index for equal with two conditions");
        benchMarkEqualsNavliableIndex(indexedCollection);

        LOGGER.info("benchmarking between clause on Navigable index ");
        benchMarkBetweenNavliableIndex(indexedCollection);

        LOGGER.info("benchmarking string contains clause on suffix index");
        benchMarkStringContainsSuffixIndex(indexedCollection);


        LOGGER.info("end");
    }

    private static void benchMarkStringContainsSuffixIndex(IndexedCollection<Loan> indexedCollection) {
        int count = 0;
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(BENCHMARK_INTERVAL);
        while (System.currentTimeMillis() < endTime) {
            String stringToSearch = RandomStringUtils.randomAlphabetic(2);
            ResultSet<Loan> result = indexedCollection.retrieve( QueryFactory.contains(Loan.ASSET_ID, stringToSearch));
//            LOGGER.info(String.format("for string to search: %s matches found: %d records", stringToSearch,  result.size()));
            count++;
        }
        LOGGER.info(String.format("performed: %d searches in: %d secs ", count, BENCHMARK_INTERVAL));
    }

    private static void benchMarkBetweenNavliableIndex(IndexedCollection<Loan> indexedCollection) {
        int count = 0;
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(BENCHMARK_INTERVAL);
        while (System.currentTimeMillis() < endTime) {
            double minAmount = Math.random() * 1000;
            double maxAmount = minAmount + (Math.random() * 100);
            ResultSet<Loan> result = indexedCollection.retrieve( QueryFactory.between(Loan.AMOUNT, minAmount, maxAmount));
//            LOGGER.info(String.format("for amount between: %f - %f matches found: %d records", minAmount, maxAmount,  result.size()));
            count++;
        }
        LOGGER.info(String.format("performed: %d searches in: %d secs ", count, BENCHMARK_INTERVAL));
    }

    private static void benchMarkEqualsNavliableIndex(IndexedCollection<Loan> indexedCollection) {
        int count = 0;
        Random r = new Random();
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(BENCHMARK_INTERVAL);
        while (System.currentTimeMillis() < endTime) {
            CollateralType collateralType = (r.nextInt(10) % 2) == 0 ? CollateralType.CASH : CollateralType.NONCASH;
            LoanStatus loanStatus = (r.nextInt(10) % 2) == 0 ? LoanStatus.OPEN : LoanStatus.CLOSED;
            ResultSet<Loan> result = indexedCollection.retrieve( QueryFactory.and(QueryFactory.equal(Loan.COLLATREAL_TYPE, collateralType), QueryFactory.equal(Loan.STATUS, loanStatus)));
//            LOGGER.info(String.format("for combination: %s - %s matches found: %d records", collateralType, loanStatus,  result.size()));
            count++;
        }
        LOGGER.info(String.format("performed: %d searches in: %d secs ", count, BENCHMARK_INTERVAL));
    }

    private static void benchMarkHashMapSearchByKey(Map<Integer, Loan> loanMap) {
        int count = 0;
        Random r = new Random();
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(BENCHMARK_INTERVAL);
        while (System.currentTimeMillis() < endTime) {
            int loanReferenceNumber = r.nextInt(TEST_DATA_COUNT);
            Loan result = loanMap.get(loanReferenceNumber);
//            LOGGER.info(String.format("for loanRef: %s match found: %s", loanReferenceNumber, result));
            count++;
        }
        LOGGER.info(String.format("performed: %d searches in: %d secs ", count, BENCHMARK_INTERVAL));
    }


    private static void benchMarkEqualsUniqueIndex(IndexedCollection<Loan> indexedCollection) {
        int count = 0;
        Random r = new Random();
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(BENCHMARK_INTERVAL);
        while (System.currentTimeMillis() < endTime) {
            int loanReferenceNumber = r.nextInt(TEST_DATA_COUNT);
            ResultSet<Loan> result = indexedCollection.retrieve(QueryFactory.equal(Loan.LOAN_REFERENCE_NUMBER,loanReferenceNumber));
//            LOGGER.info(String.format("for loanRef: %s matches found: %d", loanReferenceNumber, result.size()));
            count++;
        }
        LOGGER.info(String.format("performed: %d searches in: %d secs ", count, BENCHMARK_INTERVAL));
    }

    private static void populateCollection(Collection<Loan> testData, IndexedCollection<Loan> indexedCollection){
        long startTime = System.currentTimeMillis();
        indexedCollection.addAll(testData);
        long totalTime = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format("collection populated with: %d recs in: %d ms", indexedCollection.size(), totalTime));
    }

}
