package app.netlify.nmhillusion.eciapp.service_impl;

import app.netlify.nmhillusion.eciapp.service.MainService;
import app.netlify.nmhillusion.eciapp.service.WantedPeopleService;
import app.netlify.nmhillusion.n2mix.helper.log.LogHelper;
import app.netlify.nmhillusion.neon_di.annotation.Inject;
import app.netlify.nmhillusion.neon_di.annotation.Neon;

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
