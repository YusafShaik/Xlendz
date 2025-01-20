package in.xlendz;

import in.xlendz.entity.XlendzUserAddress;
import in.xlendz.responses.XlendzUserAddressResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class XlendzConfigurations {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(XlendzUserAddress.class, XlendzUserAddressResponse.class);

        // Add the converter here as well
        modelMapper.addConverter((Converter<List<XlendzUserAddress>, List<XlendzUserAddressResponse>>) context -> {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().stream()
                    .map(address -> modelMapper.map(address, XlendzUserAddressResponse.class))
                    .toList();
        });

        return modelMapper;

    }
}
