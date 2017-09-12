package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SungHyun on 2017-09-07.
 * Cafe Model
 * - 카페 이름
 * - 카페 프로필
 * - 카페 오픈 상태
 * - 카페 주중 오픈 시간
 * - 카페 주중 마감 시간
 * - 카페 주말 오픈 시간
 * - 카페 주말 마감 시간
 * - 카페 거리
 * - 카페 주소
 * - 카페 전화번호
 * - 카페 특징(24시간 유무, wifi, 흡연실, 주차장)
 */

public class CafeModel {
    @SerializedName("cafe_name")
    private String cafeName;
    @SerializedName("cafe_thumbnail")
    private String cafeThumbnail;
    @SerializedName("cafe_weekdays_open_time")
    private String cafeWeekDaysOpenTime;
    @SerializedName("cafe_weekdays_close_time")
    private String cafeWeekDaysCloseTime;
    @SerializedName("cafe_weekend_open_time")
    private String cafeWeekendOpenTime;
    @SerializedName("cafe_weekend_close_time")
    private String cafeWeekendCloseTime;
    @SerializedName("cafe_latitude")
    private String cafeLatitude;
    @SerializedName("cafe_longitude")
    private String cafeLongitude;
    @SerializedName("cafe_address")
    private String cafeAddress;
    @SerializedName("cafe_phone")
    private String cafePhoneNum;
    /*
    private boolean FullTimeState;
    private boolean WifiState;
    private boolean SmokeState;
    private boolean ParkingState;
*/
    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getCafeThumbnail() {
        return cafeThumbnail;
    }

    public void setCafeThumbnail(String cafeThumbnail) {
        this.cafeThumbnail = cafeThumbnail;
    }

    public String getCafeWeekDaysOpenTime() {
        return cafeWeekDaysOpenTime;
    }

    public void setCafeWeekDaysOpenTime(String cafeWeekDaysOpenTime) {
        this.cafeWeekDaysOpenTime = cafeWeekDaysOpenTime;
    }

    public String getCafeWeekDaysCloseTime() {
        return cafeWeekDaysCloseTime;
    }

    public void setCafeWeekDaysCloseTime(String cafeWeekDaysCloseTime) {
        this.cafeWeekDaysCloseTime = cafeWeekDaysCloseTime;
    }

    public String getCafeWeekendOpenTime() {
        return cafeWeekendOpenTime;
    }

    public void setCafeWeekendOpenTime(String cafeWeekendOpenTime) {
        this.cafeWeekendOpenTime = cafeWeekendOpenTime;
    }

    public String getCafeWeekendCloseTime() {
        return cafeWeekendCloseTime;
    }

    public void setCafeWeekendCloseTime(String cafeWeekendCloseTime) {
        this.cafeWeekendCloseTime = cafeWeekendCloseTime;
    }

    public String getCafeLatitude() {
        return cafeLatitude;
    }

    public void setCafeLatitude(String cafeLatitude) {
        this.cafeLatitude = cafeLatitude;
    }

    public String getCafeLongitude() {
        return cafeLongitude;
    }

    public void setCafeLongitude(String cafeLongitude) {
        this.cafeLongitude = cafeLongitude;
    }


    public String getCafeAddress() {
        return cafeAddress;
    }

    public void setCafeAddress(String cafeAddress) {
        this.cafeAddress = cafeAddress;
    }

    public String getCafePhoneNum() {
        return cafePhoneNum;
    }

    public void setCafePhoneNum(String cafePhoneNum) {
        this.cafePhoneNum = cafePhoneNum;
    }
/*
    public boolean isFullTimeState() {
        return FullTimeState;
    }

    public void setFullTimeState(boolean fullTimeState) {
        FullTimeState = fullTimeState;
    }

    public boolean isWifiState() {
        return WifiState;
    }

    public void setWifiState(boolean wifiState) {
        WifiState = wifiState;
    }

    public boolean isSmokeState() {
        return SmokeState;
    }

    public void setSmokeState(boolean smokeState) {
        SmokeState = smokeState;
    }

    public boolean isParkingState() {
        return ParkingState;
    }

    public void setParkingState(boolean parkingState) {
        ParkingState = parkingState;
    }
    */
}
