package com.app.demoopencartapp.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by janisharali on 27/01/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}