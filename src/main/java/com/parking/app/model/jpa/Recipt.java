package com.parking.app.model.jpa;

public class Recipt {


    private Long amount;

    private Long durationInHours;

    private String startDate;

    private String endDate;

    public Recipt() {

    }

    private Recipt(ReciptBuilder reciptBuilder) {
        this.amount = reciptBuilder.amount;
        this.durationInHours = reciptBuilder.durationInHours;
        this.startDate = reciptBuilder.startDate;
        this.endDate = reciptBuilder.endDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(Long durationInHours) {
        this.durationInHours = durationInHours;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public static class ReciptBuilder {

        private Long amount;
        private Long durationInHours;
        private String startDate;
        private String endDate;

        public ReciptBuilder() {

        }

        public ReciptBuilder setAmount(Long amount) {
            this.amount = amount;
            return this;
        }


        public ReciptBuilder setDuration(Long duration) {
            this.durationInHours = duration;
            return this;
        }


        public ReciptBuilder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReciptBuilder setEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }


        public Recipt build() {
            return new Recipt(this);
        }


    }
}
