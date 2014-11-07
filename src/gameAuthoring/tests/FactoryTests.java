package gameAuthoring.tests;

import static org.junit.Assert.*;
import gameAuthoring.factory.SingletonDataFactory;
import org.junit.Test;

public class FactoryTests {

    @Test
    public void FactoryDataCreation () {
        SingletonDataFactory factory = new SingletonDataFactory();
        assertNotNull(factory.buildActorDataInstance("Enemy"));
    }


}
