package com.example.tarottales.enumdata;

public class EnumData {

    public enum ElementName {

        FIRE("Lửa"),
        WATER("Nước"),
        EARTH("Đất"),
        AIR("Khí");

        private final String elementName;

        ElementName(String elementName) {
            this.elementName = elementName;
        }

        public String getElementName() {
            return this.elementName;
        }
    }

    public enum PlanetName {
        MERCURY("Thủy Tinh"),
        VENUS("Kim Tinh"),
        EARTH("Địa Cầu"),
        MARS("Hỏa Tinh"),
        JUPITER("Mộc Tinh"),
        SATURN("Thổ Tinh"),
        SUN("Mặt Trời"),
        MOON("Mặt Trăng");

        private final String description;

        PlanetName(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ZodiacName {
        ARIES("Bạch Dương"),
        TAURUS("Kim Ngưu"),
        GEMINI("Song Tử"),
        CANCER("Cự Giải"),
        LEO("Sư Tử"),
        VIRGO("Xử Nữ"),
        LIBRA("Thiên Bình"),
        SCORPIO("Thiên Yết"),
        SAGITTARIUS("Nhân Mã"),
        CAPRICORN("Ma Kết"),
        AQUARIUS("Bảo Bình"),
        PISCES("Song Ngư");

        private final String description;

        ZodiacName(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
