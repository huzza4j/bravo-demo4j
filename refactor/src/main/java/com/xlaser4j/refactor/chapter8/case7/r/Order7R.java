package com.xlaser4j.refactor.chapter8.case7.r;

/**
 * @package: com.xlaser4j.refactor.chapter8.case7.r
 * @author: Elijah.D
 * @time: 2020/4/3 17:43
 * @description:
 * @modified: Elijah.D
 */
public class Order7R {
    private Customer7R customer;

    public Customer7R getCustomerR() {
        return customer;
    }

    public void setCustomerR(Customer7R customer) {
        if (customer != null) {
            customer.listOrders().remove(this);
        }
        this.customer = customer;
        if (customer != null) {
            customer.listOrders().add(this);
        }
    }
}
