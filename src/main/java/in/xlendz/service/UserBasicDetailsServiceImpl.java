package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.XlendzUserAddress;
import in.xlendz.entity.XlendzUserBasicDetails;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.UserBasicDetailsRepo;
import in.xlendz.repo.XlendzUserAddressRepo;
import in.xlendz.requests.XlendzUserDetailRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserBasicDetailsServiceImpl implements UserBasicDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBasicDetailsServiceImpl.class);

    private final ModelMapper modelMapper;
    private final UserBasicDetailsRepo xlendzUserBasicDetailsRepo;
    private final XlendzUserAddressRepo xlendzUserAddressRepo;

    public UserBasicDetailsServiceImpl(ModelMapper modelMapper, UserBasicDetailsRepo xlendzUserBasicDetailsRepo, XlendzUserAddressRepo xlendzUserAddressRepo) {
        this.modelMapper = modelMapper;
        this.xlendzUserBasicDetailsRepo = xlendzUserBasicDetailsRepo;
        this.xlendzUserAddressRepo = xlendzUserAddressRepo;
    }

    @Override
    public MethodResponse saveUserBasicDetails(XlendzUserDetailRequest xlendzUserDetailRequest) {
        try{
            LOGGER.info("Saving User Basic Details of Id {}",xlendzUserDetailRequest.getXlendzUserId());
            XlendzUserBasicDetails userBasicDetails=modelMapper.map(xlendzUserDetailRequest,XlendzUserBasicDetails.class);
            xlendzUserBasicDetailsRepo.save(userBasicDetails);
            LOGGER.info("User Basic Details saved successfully");
            List<XlendzUserAddress> userAddress= addressDtoToEntity(xlendzUserDetailRequest);
            for (XlendzUserAddress address : userAddress) {
                address.setUserBasicDetailsId(userBasicDetails);
            }
            xlendzUserAddressRepo.saveAll(userAddress);
            LOGGER.info("User Address saved successfully");
            return  MethodResponse.SUCCESS;
        }catch (DataUpdationException e){
            throw new DataUpdationException("Something went wrong please try again !!",e);
        }
    }

    private List<XlendzUserAddress> addressDtoToEntity(XlendzUserDetailRequest xlendzUserDetailRequest) {
        return xlendzUserDetailRequest.getAddress().stream().
                map(address -> modelMapper.map(address, XlendzUserAddress.class)).toList();
    }
}
