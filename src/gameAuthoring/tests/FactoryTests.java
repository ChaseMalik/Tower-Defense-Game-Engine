package gameAuthoring.tests;

import static org.junit.Assert.*;
import gameAuthoring.factory.SingletonDataFactory;
import org.junit.Test;

public class FactoryTests {

    @Test
    public void FactoryDataCreation () {
        assertNotNull(SingletonDataFactory.getInstance().buildActorDataInstance("Enemy"));
    }


}
