package com.project.ecommerce.enums;

public enum FromStatus {

        OrderPlaced(ToStatus.Cancelled,ToStatus.OrderConfirmed,ToStatus.OrderRejected),
        Cancelled(ToStatus.RefundInitiated,ToStatus.Closed),
        OrderRejected(ToStatus.RefundInitiated,ToStatus.Closed),
        OrderConfirmed(ToStatus.Cancelled,ToStatus.OrderShipped),
        OrderShipped(ToStatus.Delivered),
        Delivered(ToStatus.ReturnRequested,ToStatus.Closed),
        ReturnRequested(ToStatus.ReturnRejected,ToStatus.ReturnApproved),
        ReturnRejected(ToStatus.Closed),
        ReturnApproved(ToStatus.PickUpInitiated),
        PickUpInitiated(ToStatus.PickUpCompleted),
        PickUpCompleted(ToStatus.RefundInitiated),
        RefundInitiated(ToStatus.RefundCompleted),
        RefundCompleted(ToStatus.Closed);


        private ToStatus toStatus1;
        private ToStatus toStatus2;
        private ToStatus toStatus3;

        FromStatus(ToStatus toStatus1, ToStatus toStatus2, ToStatus toStatus3) {
            this.toStatus1 = toStatus1;
            this.toStatus2 = toStatus2;
            this.toStatus3 = toStatus3;
        }

        FromStatus(ToStatus toStatus1, ToStatus toStatus2) {
            this.toStatus1 = toStatus1;
            this.toStatus2 = toStatus2;
        }

        public ToStatus getToStatus1() {
            return toStatus1;
        }

        FromStatus(ToStatus toStatus3) {
            this.toStatus3 = toStatus3;
        }
}
