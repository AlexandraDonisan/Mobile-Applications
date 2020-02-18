package en.ubb.server.core.service;

import en.ubb.server.core.model.Sightseeing;

import java.util.List;

public interface SightseeingService {
    List<Sightseeing> getAllSightseeings();

    Sightseeing getSightSeeing(Integer id);

    Sightseeing saveSightseeing(Sightseeing sightseeing);

    Sightseeing deleteSightseeing(Integer id);

    Sightseeing updateSightseeing(Integer id, String name, String schedule, int price);
}
