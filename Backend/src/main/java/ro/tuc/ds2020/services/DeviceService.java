package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDeleteDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(Integer id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    public Integer insert(DeviceDetailsDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public Integer delete(DeviceDeleteDTO deviceDTO){
        Integer id = deviceDTO.getId();
        deviceRepository.deleteById(id);
        LOGGER.debug("Device with id {} was deleted from db", id);
        return id;
    }

    public Integer update(DeviceDTO deviceDTO){
        Device device = DeviceBuilder.toEntity(deviceDTO);
        if(device.getId() == null){
            return -1;
        }
        Device deviceBeforeUpdate = deviceRepository.findById(device.getId()).orElse(null);
        if(deviceBeforeUpdate == null) {
            LOGGER.debug("Device with id {} was not updated", device.getId());
            return -1;
        }
        if(device.getDescription().equals("")){
            device.setDescription(deviceBeforeUpdate.getDescription());
        }
        if(device.getAddress().equals("")){
            device.setAddress(deviceBeforeUpdate.getAddress());
        }
        if(device.getMaxConsumption() == 0){
            device.setMaxConsumption(deviceBeforeUpdate.getMaxConsumption());
        }
        deviceRepository.save(device);
        LOGGER.debug("Device with id {} was updated in db", device.getId());
        return device.getId();
    }
}
