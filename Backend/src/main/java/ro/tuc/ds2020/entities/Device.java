package ro.tuc.ds2020.entities;

import javax.persistence.*;

@Entity
public class Device {
    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "max_consumption", nullable = false)
    private Integer maxConsumption;

    public Device() {
    }

    public Device(String description, String address, Integer maxConsumption) {
        this.description = description;
        this.address = address;
        this.maxConsumption = maxConsumption;
    }

    public Device(Integer id, String description, String address, Integer maxConsumption) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxConsumption = maxConsumption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }
}
