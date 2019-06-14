package com.app.demoopencartapp.di.components;



import com.app.demoopencartapp.di.PerService;
import com.app.demoopencartapp.di.modules.ServiceModule;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

   // void inject(MyFirebaseMessagingServices service);

}