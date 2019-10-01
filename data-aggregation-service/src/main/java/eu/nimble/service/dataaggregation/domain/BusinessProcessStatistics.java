package eu.nimble.service.dataaggregation.domain;

public class BusinessProcessStatistics {

    private Integer total;
    private StateCount state;
    private TypeCount type;
    private RoleCount role;

    private BusinessProcessStatistics() {
    }

    public BusinessProcessStatistics(Integer total, Integer waiting, Integer approved, Integer denied,
                                     Integer sellertotal,Integer sellerwaiting, Integer sellerapproved, Integer sellerdenied,
                                     Integer buyertotal,Integer buyerwaiting, Integer buyerapproved, Integer buyerdenied,
                                     Integer informationRequest, Integer negotiations, Integer order) {
        this.total = total;
        this.type = new TypeCount(informationRequest, negotiations, order);
        this.role = new RoleCount(new RoleType(buyertotal,buyerwaiting,buyerapproved,buyerdenied),
                new RoleType(sellertotal,sellerwaiting,sellerapproved,sellerdenied));
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
        private RoleType buyer;
        private RoleType seller;

        private RoleCount() {
        }

        private RoleCount(RoleType buyer, RoleType seller) {
            this.buyer = buyer;
            this.seller = seller;
        }

        public RoleType getBuyer() {
            return buyer;
        }

        public RoleType getSeller() {
            return seller;
        }
    }

    static class RoleType {
        private Integer tot;
        private Integer waiting;
        private Integer approved;
        private Integer denied;

        private RoleType() {
        }

        private RoleType(Integer tot,Integer waiting, Integer approved, Integer denied) {
            this.waiting = waiting;
            this.approved = approved;
            this.denied = denied;
            this.tot = tot;
        }

        public Integer getTot() {
            return tot;
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
