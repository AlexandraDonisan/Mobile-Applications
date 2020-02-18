package en.ubb.server.core.service;

import en.ubb.server.core.model.Sightseeing;
import en.ubb.server.core.repository.SightseeingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SightseeingServiceImpl implements SightseeingService {
    @Autowired
    private SightseeingRepository sightseeingRepository;

    @Override
    public List<Sightseeing> getAllSightseeings() {
        return sightseeingRepository.findAll();
    }

    @Override
    public Sightseeing getSightSeeing(Integer id) {
        Optional<Sightseeing> sightseeing = sightseeingRepository.findById(id);
        return sightseeing.get();
    }

    @Override
    @Transactional
    public Sightseeing saveSightseeing(Sightseeing sightseeing) {
        return sightseeingRepository.save(sightseeing);
    }

    @Override
    @Transactional
    public Sightseeing deleteSightseeing(Integer id) {
        Optional<Sightseeing> sightseeing = sightseeingRepository.findById(id);
        Sightseeing sight = sightseeing.get();
        sightseeingRepository.deleteById(id);
        return sight;
    }

    @Override
    @Transactional
    public Sightseeing updateSightseeing(Integer id, String name, String schedule, int price) {
        Optional<Sightseeing> optionalSightseeing = sightseeingRepository.findById(id);
        assert optionalSightseeing.isPresent();
        Sightseeing sight = optionalSightseeing.get();
        sight.setName(name);
        sight.setSchedule(schedule);
        sight.setPrice(price);
        return sight;
    }
}
