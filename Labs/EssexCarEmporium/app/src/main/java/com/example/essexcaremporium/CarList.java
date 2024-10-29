package com.example.essexcaremporium;

import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CarList extends ArrayList<Car> {
    @Inject
    CarList() {
        // Default constructor
    }
}
