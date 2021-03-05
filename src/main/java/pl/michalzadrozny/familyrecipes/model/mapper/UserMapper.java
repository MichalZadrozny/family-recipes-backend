package pl.michalzadrozny.familyrecipes.model.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;

public class UserMapper {

    private UserMapper() {
    }

    public static ModelMapper signUpDtoToAppUserMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<SignUpDTO, AppUser>() {
            @Override
            protected void configure() {
                map().setUsername(source.getUsername());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
            }
        });
        return modelMapper;
    }
}