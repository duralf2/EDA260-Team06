package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import register.model.DataStructure;
import sorter.Sorter;

import java.io.File;
import java.io.IOException;

public class SorterTest {
    DataStructure ds;
    Sorter sorter;
    
    @Before
    public void setUp() {
        ds = new DataStructure();
        sorter = new Sorter(ds);
    }
    
    @After
    public void tearDown() {
        ds = null;
        sorter = null;
    }
    
    @Test
    public void testOrderNoClasses() throws IOException {
        // TODO: Do actual tests, don't only check that it doesn't crash
        File[] files = new File[] {
                new File("testfiles/acceptanstest/Iteration2/acceptanstest18/starttider.txt"),
                new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider1.txt"),
                new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider2.txt")
        };
        
        sorter.sortTime(files, new File("testfiles/acceptanstest/Iteration2/acceptanstest18/namnfilNoClasses.txt"));
    }
}
