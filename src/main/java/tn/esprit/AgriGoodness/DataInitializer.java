package tn.esprit.AgriGoodness;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.esprit.AgriGoodness.entities.Role;
import tn.esprit.AgriGoodness.enums.ERole;
import tn.esprit.AgriGoodness.repositories.RoleRepository;

@Component
@AllArgsConstructor
public class DataInitializer {
    RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeRoles() {
        for (ERole role : ERole.values()) {
            if (roleRepository.findByName(role).isEmpty()) {
                    roleRepository.save(new Role(role));
            }
        }
    }

}
