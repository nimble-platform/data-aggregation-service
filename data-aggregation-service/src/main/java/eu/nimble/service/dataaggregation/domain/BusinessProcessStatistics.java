package eu.nimble.service.dataaggregation.domain;

public class BusinessProcessStatistics {

    private Integer total;
    private TypeCount type;
    private RoleCount role;
    private StateCount state;

    private BusinessProcessStatistics() {
    }

    public BusinessProcessStatistics(Integer total, Integer waiting, Integer approved, Integer denied, Integer buyer,
                                     Integer seller, Integer informationRequest, Integer negotiations, Integer order) {
        this.total = total;
        this.type = new TypeCount(informationRequest, negotiations, order);
        this.role = new RoleCount(buyer, seller);
        this.state = new StateCount(waiting, approved, denied);
    }

    static class TypeCount {
        private Integer informationRequest;
        private Integer negotiations;
        private Integer order;

        private TypeCount() {
        }

        private TypeCount(Integer informationRequest, Integer negotiations, Integer order) {
            this.informationRequest = informationRequest;
            this.negotiations = negotiations;
            this.order = order;
        }

        public Integer getInformationRequest() {
            return informationRequest;
        }

        public Integer getNegotiations() {
            return negotiations;
        }

        public Integer getOrder() {
            return order;
        }
    }

    static class RoleCount {
        private Integer buyer;
        private Integer seller;

        private RoleCount() {
        }

        private RoleCount(Integer buyer, Integer seller) {
            this.buyer = buyer;
            this.seller = seller;
        }

        public Integer getBuyer() {
            return buyer;
        }

        public Integer getSeller() {
            return seller;
        }
    }

    static class StateCount {
        private Integer waiting;
        private Integer approved;
        private Integer denied;

        private StateCount() {
        }

        private StateCount(Integer waiting, Integer approved, Integer denied) {
            this.waiting = waiting;
            this.approved = approved;
            this.denied = denied;
        }

        public Integer getWaiting() {
            return waiting;
        }

        public Integer getApproved() {
            return approved;
        }

        public Integer getDenied() {
            return denied;
        }
    }

    public Integer getTotal() {
        return total;
    }


    public TypeCount getType() {
        return type;
    }

    public RoleCount getRole() {
        return role;
    }

    public StateCount getState() {
        return state;
    }
}
