package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DeviceDetailsDTO {

    private Integer id;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @AgeLimit(limit = 18)
    private Integer maxConsumption;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(Integer id){
        this.id = id;
    }

    public DeviceDetailsDTO( String description, String address, Integer maxConsumption) {
        this.description = description;
        this.address = address;
        this.maxConsumption = maxConsumption;
    }

    public DeviceDetailsDTO(Integer id, String description, String address, Integer maxConsumption) {
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

    public int getAge() {
        return maxConsumption;
    }

    public void setAge(int age) {
        this.maxConsumption = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return maxConsumption == that.maxConsumption &&
                Objects.equals(description, that.description) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, maxConsumption);
    }
}
