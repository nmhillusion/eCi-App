package tech.nmhillusion.eciapp.service_impl;

import tech.nmhillusion.eciapp.service.MainService;
import tech.nmhillusion.eciapp.service.WantedPeopleService;
import tech.nmhillusion.neon_di.annotation.Inject;
import tech.nmhillusion.neon_di.annotation.Neon;

/**
 * date: 2023-02-25
 * <p>
 * created-by: nmhillusion
 */

@Neon
public class MainServiceImpl implements MainService {
    @Inject
    private WantedPeopleService wantedPeopleService;

    @Override
    public String helloMessage() {
        return "Greeting message from MainService";
    }
}
