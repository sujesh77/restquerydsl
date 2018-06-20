package com.itglance.finalquerydsl.controller;

import com.itglance.finalquerydsl.model.Location;
import com.itglance.finalquerydsl.model.QLocation;
import com.itglance.finalquerydsl.repository.LocationRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationController extends BaseController<Location, LocationRepository> {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        super(Location.class, QLocation.location, locationRepository);
        this.locationRepository = locationRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/location")
    @ResponseBody
    public Iterable<Location> searchLocation(@RequestParam(value = "search") String search) {
        return super.search(search);
    }

}
