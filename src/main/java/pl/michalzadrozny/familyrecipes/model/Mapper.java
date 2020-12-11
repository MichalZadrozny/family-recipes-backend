package pl.michalzadrozny.familyrecipes.model;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;


public class Mapper {
    public static ModelMapper signUpToAppUserMapper() {
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