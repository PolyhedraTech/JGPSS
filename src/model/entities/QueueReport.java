/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.ArrayDeque;
import java.util.Deque;
import lombok.Getter;
import model.Model;
import model.TimeRecord;

/**
 * Register the queue statistics
 *
 * @author eZe
 */
public class QueueReport {

    @Getter
    private int maxCount;
    @Getter
    private int totalEntries;
    @Getter
    private int zeroEntries;
    @Getter
    private int currentCount;
    @Getter
    private int retry;
    private final Deque<TimeRecord> timeRecords;
    private final Model model;

    public QueueReport(Model model) {
        retry = 0;
        maxCount = 0;
        totalEntries = 0;
        zeroEntries = 0;
        timeRecords = new ArrayDeque<>();
        this.model = model;
    }

    /**
     * Returns the percentage of entries that are zero entries
     *
     * @return
     */
    public double getPercentZeros() {

        return (this.zeroEntries / this.totalEntries) * 100;
    }

    /**
     * the average waiting time of the transactions in the queue
     *
     * @return
     */
    public Float getAvgTime() {
        return averageTime(false);
    }

    /**
     * the average waiting time of the transactions in the queue
     *
     * @param zero if true returns the average waiting time without zero entries
     * @return
     */
    public Float getAvgTime(boolean zero) {
        return averageTime(zero);
    }

    public Float getAvgContent() {

        return timeRecords.stream()//
                .map(tr -> tr.totalTime())//
                .reduce(0f, (x, y) -> x + y) / model.getRelativeClock();

    }

    /**
     * Increments the current count by entries units
     *
     * @param entries
     */
    public void incCurrentCount(int entries) {
        currentCount += entries;
        totalEntries += entries;

        if (currentCount > maxCount) {
            maxCount = currentCount;
        }

        regStartTime();
    }

    /**
     * Decrements the current count by entries units
     *
     * @param entries
     */
    public void decCurrentCount(int entries) {
        currentCount -= entries;
        regEndTime();
    }

    /**
     * Increments the entries with 0 waiting time
     */
    public void incZeroEntries() {
        this.zeroEntries++;
    }

    /**
     * Increments the maximum length of the queue by increment units
     *
     * @param increment
     */
    public void incMaxCount(int increment) {
        maxCount += increment;
    }   

    /**
     * Cleans the statistics
     */
    public void clean() {
        retry = 0;
        maxCount = 0;
        totalEntries = 0;
        zeroEntries = 0;
        timeRecords.clear();
    }

    private void regStartTime() {
        float time = model.getRelativeClock();
        timeRecords.push(new TimeRecord(time, time));
    }

    private void regEndTime() {
        timeRecords.peekFirst().setEndTime(model.getRelativeClock());
    }

    private float averageTime(boolean withoutZeroEntries) {

        if (timeRecords.isEmpty()) {
            return 0f;
        }

        if (withoutZeroEntries) {

            return timeRecords.stream()//
                    .map(rt -> rt.totalTime())//
                    .filter(tt -> tt == 0f)//
                    .reduce(0f, (x, y) -> x + y) / totalEntries;
        }

        return timeRecords.stream()//
                .map(rt -> rt.totalTime())//
                .reduce(0f, (x, y) -> x + y) / totalEntries;
    }

}
