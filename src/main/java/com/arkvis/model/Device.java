package com.arkvis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id")
    @NotNull
    private String customerId;

    @Column(name = "system_name")
    @NotNull
    private String systemName;

    @Column(name = "device_type")
    @NotNull
    private String type;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
