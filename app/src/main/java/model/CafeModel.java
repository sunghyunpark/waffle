package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
 * - 카페 소개
 */

public class CafeModel implements Serializable{

    @SerializedName("cafe_id")
    private String cafeId;
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
    @SerializedName("cafe_full_time_state")
    private String cafeFullTimeState;
    @SerializedName("cafe_wifi_state")
    private String cafeWifiState;
    @SerializedName("cafe_smoke_state")
    private String cafeSmokeState;
    @SerializedName("cafe_parking_state")
    private String cafeParkingState;
    @SerializedName("cafe_intro")
    private String cafeIntro;

    public String getCafeId() {
        return cafeId;
    }

    public void setCafeId(String cafeId) {
        this.cafeId = cafeId;
    }

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

    public String getCafeFullTimeState() {
        return cafeFullTimeState;
    }

    public void setCafeFullTimeState(String cafeFullTimeState) {
        this.cafeFullTimeState = cafeFullTimeState;
    }

    public String getCafeWifiState() {
        return cafeWifiState;
    }

    public void setCafeWifiState(String cafeWifiState) {
        this.cafeWifiState = cafeWifiState;
    }

    public String getCafeSmokeState() {
        return cafeSmokeState;
    }

    public void setCafeSmokeState(String cafeSmokeState) {
        this.cafeSmokeState = cafeSmokeState;
    }

    public String getCafeParkingState() {
        return cafeParkingState;
    }

    public void setCafeParkingState(String cafeParkingState) {
        this.cafeParkingState = cafeParkingState;
    }

    public String getCafeIntro() {
        return cafeIntro;
    }

    public void setCafeIntro(String cafeIntro) {
        this.cafeIntro = cafeIntro;
    }

}
