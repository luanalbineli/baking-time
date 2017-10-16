package com.udacity.bakingtime;

import com.udacity.bakingtime.injector.components.ApplicationComponent;

public class MockBakingTimeApplication extends BakingTimeApplication {
    @Override
    protected ApplicationComponent buildComponent() {
        return DaggerRecipeListActivityTest_TestComponent.builder()
                .mockApplicationModule(new MockApplicationModule((BakingTimeApplication) mApplicationComponent))
                .build();
    }
}
