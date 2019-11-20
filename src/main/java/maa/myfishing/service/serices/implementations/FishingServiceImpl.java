package maa.myfishing.service.serices.implementations;

import maa.myfishing.constants.Constants;
import maa.myfishing.data.models.Destination;
import maa.myfishing.data.models.Fishing;
import maa.myfishing.data.reposipories.DestinationRepository;
import maa.myfishing.data.reposipories.FishingRepository;
import maa.myfishing.eroors.DestinationNotFoundException;
import maa.myfishing.eroors.FishingAlreadyExistsException;
import maa.myfishing.service.models.FishingServiceModel;
import maa.myfishing.service.serices.FishingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingServiceImpl implements FishingService {
    private final FishingRepository fishingRepository;
    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FishingServiceImpl(FishingRepository fishingRepository, DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.fishingRepository = fishingRepository;
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FishingServiceModel addFishingToDestination(FishingServiceModel fishingServiceModel, String destinationId) {
        Fishing fishing = this.fishingRepository
                .findByDateAndDescription(fishingServiceModel.getDate(), fishingServiceModel.getDescription())
                .orElse(null);

        if (fishing != null) {
            throw new FishingAlreadyExistsException(Constants.FISHING_ALREADY_EXIST_EXCEPTION);
        }

        fishing = this.modelMapper.map(fishingServiceModel, Fishing.class);

        Destination destination = this.destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationNotFoundException(Constants.DESTINATION_FOT_FOUND_EXCEPTION));

        fishing.setDestination(destination);

        this.fishingRepository.saveAndFlush(fishing);

        this.destinationRepository.save(destination);

        return this.modelMapper.map(fishing, FishingServiceModel.class);
    }
}