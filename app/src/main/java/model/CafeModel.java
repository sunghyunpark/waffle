package model;

/**
 * Created by SungHyun on 2017-09-07.
 * Cafe Model
 * - 카페 이름
 * - 카페 프로필
 * - 카페 오픈 상태
 * - 카페 오픈 시간
 * - 카페 마감 시간
 * - 카페 거리
 * - 카페 주소
 * - 카페 전화번호
 * - 카페 특징(24시간 유무, wifi, 애완동물, 흡연실, 주차장)
 */

public class CafeModel {
    private String cafeName;
    private String cafeThumbnail;
    private boolean cafeState;
    private String cafeOpenTime;
    private String cafeCloseTime;
    private String cafeDistance;
    private String cafeAddress;
    private String cafePhoneNum;
    private boolean FullTimeState;
    private boolean WifiState;
    private boolean PetState;
    private boolean SmokeState;
    private boolean ParkingState;

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

    public boolean isCafeState() {
        return cafeState;
    }

    public void setCafeState(boolean cafeState) {
        this.cafeState = cafeState;
    }

    public String getCafeOpenTime() {
        return cafeOpenTime;
    }

    public void setCafeOpenTime(String cafeOpenTime) {
        this.cafeOpenTime = cafeOpenTime;
    }

    public String getCafeCloseTime() {
        return cafeCloseTime;
    }

    public void setCafeCloseTime(String cafeCloseTime) {
        this.cafeCloseTime = cafeCloseTime;
    }

    public String getCafeDistance() {
        return cafeDistance;
    }

    public void setCafeDistance(String cafeDistance) {
        this.cafeDistance = cafeDistance;
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

    public boolean isPetState() {
        return PetState;
    }

    public void setPetState(boolean petState) {
        PetState = petState;
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
}
