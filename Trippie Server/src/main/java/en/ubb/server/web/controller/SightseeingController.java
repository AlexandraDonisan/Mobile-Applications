package en.ubb.server.web.controller;

import en.ubb.server.core.model.Sightseeing;
import en.ubb.server.core.service.SightseeingService;
import en.ubb.server.web.converter.SightseeingConverter;
import en.ubb.server.web.dto.SightseeingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "")
public class SightseeingController {

    @Autowired
    private SightseeingService sightseeingService;

    @Autowired
    private SightseeingConverter sightseeingConverter;

    @RequestMapping(value = "/sightseeings", method = RequestMethod.GET)
    List<SightseeingDto> getAllSightseeings(){
        List<Sightseeing> all = sightseeingService.getAllSightseeings();
        List<SightseeingDto> result = new ArrayList<>();
        for(Sightseeing s: all)
                result.add(sightseeingConverter.convertModelToDto(s));
        return result;
    }

    @RequestMapping(value = "/sightseeings/{id}", method = RequestMethod.GET)
    @ResponseBody
    SightseeingDto getSightseeing(@PathVariable Integer id){
        Sightseeing sightseeing = sightseeingService.getSightSeeing(id);
        return sightseeingConverter.convertModelToDto(sightseeing);
    }

    @RequestMapping(value = "/sightseeings", method = RequestMethod.POST)
    SightseeingDto saveUnicorn(@RequestBody SightseeingDto dto){
        return sightseeingConverter.convertModelToDto(
                sightseeingService.saveSightseeing(
                        sightseeingConverter.convertDtoToModel(dto)
                ));
    }

    @RequestMapping(value = "/sightseeings/{id}", method = RequestMethod.DELETE)
    SightseeingDto deleteSightseeing(@PathVariable Integer id){
        return sightseeingConverter.convertModelToDto(
                sightseeingService.deleteSightseeing(id));
    }

    @RequestMapping(value = "/sightseeings/{id}", method = RequestMethod.PUT)
    SightseeingDto updateSightseeing(@PathVariable final Integer id,
                                     @RequestBody final SightseeingDto sightseeingDto ){
        Sightseeing sightseeing = sightseeingService.updateSightseeing(id, sightseeingDto.getName(), sightseeingDto.getSchedule(), sightseeingDto.getPrice());
        return sightseeingConverter.convertModelToDto(sightseeing);
    }
}
