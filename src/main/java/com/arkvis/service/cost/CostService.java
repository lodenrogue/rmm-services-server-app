package com.arkvis.service.cost;

import com.arkvis.model.Cost;

public interface CostService {

    Cost calculateCostForCustomer(String customerId);
}
