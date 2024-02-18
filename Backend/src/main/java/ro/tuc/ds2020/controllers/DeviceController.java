package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDeleteDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/device")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/device/insert")
    public ResponseEntity<Integer> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDTO) {
        Integer deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @PostMapping("/device/delete")
    public ResponseEntity<Integer> deleteDevice(@Valid @RequestBody DeviceDeleteDTO deviceDTO) {
        Integer deviceID = deviceService.delete(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @PostMapping("/device/update")
    public ResponseEntity<Integer> updateDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        Integer deviceID = deviceService.update(deviceDTO);
        if(deviceID == -1){
            return new ResponseEntity<>(deviceID, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @GetMapping(value = "/device/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") Integer deviceId) {
        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
