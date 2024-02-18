package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private Integer id;
    private String description;
    private String address;
    private Integer maxConsumption;

    public DeviceDTO() {
    }

    public DeviceDTO(Integer id, String description, String address, Integer maxConsumption) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return maxConsumption == deviceDTO.maxConsumption &&
                Objects.equals(description, deviceDTO.description)&&
                Objects.equals(address, deviceDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, maxConsumption);
    }
}
