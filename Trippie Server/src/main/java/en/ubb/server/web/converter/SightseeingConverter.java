package en.ubb.server.web.converter;

import en.ubb.server.core.model.Sightseeing;
import en.ubb.server.web.dto.SightseeingDto;
import org.springframework.stereotype.Component;

@Component
public class SightseeingConverter extends BaseConverter<Sightseeing, SightseeingDto>{
    @Override
    public Sightseeing convertDtoToModel(SightseeingDto dto) {
        Sightseeing sightseeing = new Sightseeing(dto.getName(), dto.getSchedule(), dto.getPrice());
        sightseeing.setId(dto.getId());
        return sightseeing;
    }

    @Override
    public SightseeingDto convertModelToDto(Sightseeing sightseeing) {
        SightseeingDto dto = new SightseeingDto(sightseeing.getName(), sightseeing.getSchedule(), sightseeing.getPrice());
        dto.setId(sightseeing.getId());
        return dto;
    }
}
