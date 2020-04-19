package com.parking.app.model.jpa;

public class TicketDTO {


    private Long amount;

    private Long durationInHours;

    private String startDate;

    private String endDate;

    public TicketDTO() {

    }

    private TicketDTO(TicketDTOBuilder ticketDTOBuilder) {
        this.amount = ticketDTOBuilder.amount;
        this.durationInHours = ticketDTOBuilder.durationInHours;
        this.startDate = ticketDTOBuilder.startDate;
        this.endDate = ticketDTOBuilder.endDate;
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


    public static class TicketDTOBuilder {

        private Long amount;
        private Long durationInHours;
        private String startDate;
        private String endDate;

        public TicketDTOBuilder() {

        }

        public TicketDTOBuilder setAmount(Long amount) {
            this.amount = amount;
            return this;
        }


        public TicketDTOBuilder setDuration(Long duration) {
            this.durationInHours = duration;
            return this;
        }


        public TicketDTOBuilder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public TicketDTOBuilder setEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }


        public TicketDTO build() {
            return new TicketDTO(this);
        }


    }
}
