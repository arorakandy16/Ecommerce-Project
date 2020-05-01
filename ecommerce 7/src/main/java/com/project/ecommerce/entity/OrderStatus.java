package com.project.ecommerce.entity;

import com.project.ecommerce.enums.FromStatus;
import com.project.ecommerce.enums.ToStatus;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    public class OrderStatus implements Serializable {

        @Id
        private Long id;

        @Enumerated(EnumType.STRING)
        private FromStatus From_Status;

        @Enumerated(EnumType.STRING)
        private ToStatus toStatus;

        private String Transmission_Notes_Comments;

        public OrderStatus(Long id, FromStatus from_Status,
                           ToStatus toStatus,
                           String transmission_Notes_Comments) {

            this.id = id;
            From_Status = from_Status;
            this.toStatus = toStatus;
            Transmission_Notes_Comments = transmission_Notes_Comments;
        }



        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public FromStatus getFrom_Status() {
            return From_Status;
        }

        public void setFrom_Status(FromStatus from_Status) {
            From_Status = from_Status;
        }

        public ToStatus getToStatus() {
            return toStatus;
        }

        public void setToStatus(ToStatus toStatus) {
            this.toStatus = toStatus;
        }

        public String getTransmission_Notes_Comments() {
            return Transmission_Notes_Comments;
        }

        public void setTransmission_Notes_Comments(String transmission_Notes_Comments) {
            Transmission_Notes_Comments = transmission_Notes_Comments;
        }
    }