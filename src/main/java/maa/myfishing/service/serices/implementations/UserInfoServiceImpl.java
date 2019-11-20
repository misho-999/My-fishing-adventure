package maa.myfishing.service.serices.implementations;

import maa.myfishing.data.models.Destination;
import maa.myfishing.data.models.UserInfo;
import maa.myfishing.data.reposipories.DestinationRepository;
import maa.myfishing.data.reposipories.UserInfoRepository;
import maa.myfishing.service.models.DestinationServiceModel;
import maa.myfishing.service.models.UserInfoServiceModel;
import maa.myfishing.service.serices.DestinationService;
import maa.myfishing.service.serices.UserInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final DestinationRepository destinationRepository;
    private final DestinationService destinationService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, DestinationRepository destinationRepository
            , DestinationService destinationService, ModelMapper modelMapper) {
        this.userInfoRepository = userInfoRepository;
        this.destinationRepository = destinationRepository;
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserInfoServiceModel saveUserInfo(UserInfoServiceModel userInfoServiceModel) {
        UserInfo userInfo = this.modelMapper.map(userInfoServiceModel, UserInfo.class);

        return this.modelMapper.map(this.userInfoRepository.saveAndFlush(userInfo), UserInfoServiceModel.class);
    }

    @Override
    public void addDestination(String townName, String username) {

        UserInfo userInfo = this.userInfoRepository.findByUserUsername(username);

        DestinationServiceModel destinationServiceModel = this.destinationService.getDestinationByTownName(townName);

        Destination destination = this.modelMapper.map(destinationServiceModel, Destination.class);

        destination.getUserInfos().add(userInfo);
        userInfo.getDestinations().add(destination);

        this.destinationRepository.save(destination);
        this.userInfoRepository.save(userInfo);
    }
}