package com.course.kafka.section_7.entity;

/**
 * Section 07: 38. Message Filter
 */
public class CarLocation {

    private String carId;
    private long timestamp;
    private int distance;

    public CarLocation(String carId, long timestamp, int distance) {
        this.carId = carId;
        this.timestamp = timestamp;
        this.distance = distance;
    }

    public CarLocation(){

    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
