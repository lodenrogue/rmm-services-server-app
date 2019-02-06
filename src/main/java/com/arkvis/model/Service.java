package com.arkvis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @Column(name = "service_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "windows_cost")
    private BigDecimal windowsCost;

    @Column(name = "mac_cost")
    private BigDecimal macCost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getWindowsCost() {
        return windowsCost;
    }

    public void setWindowsCost(BigDecimal windowsCost) {
        this.windowsCost = windowsCost;
    }

    public BigDecimal getMacCost() {
        return macCost;
    }

    public void setMacCost(BigDecimal macCost) {
        this.macCost = macCost;
    }
}
