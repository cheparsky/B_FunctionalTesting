package GUITests.tests;

import GUITests.pages.AddNewComputerPage;
import GUITests.pages.ComputersListPage;
import GUITests.utils.MyDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class AddNewComputerFieldValidationTest {

    public static WebDriver wd;
    public static int waitTime = 10;
    public static String mainPage = "http://computer-database.herokuapp.com";
    public static String addNewComputerPage = "http://computer-database.herokuapp.com/computers/new";

    @BeforeClass
    public static void setUp() {
        wd = MyDriver.runWDriver("Chrome");
        MyDriver.setWebDriverWait(CRUDTest.waitTime);
        MyDriver.setAllPageObject();
    }

    @Test
    public void addComputerNameValidationTest() {
        wd.get(mainPage);
        ComputersListPage.addNewComputerButton();
        AddNewComputerPage.addNewComputerWithNameOnly("   ");
    }

    @Test
    public void addComputerIntroducedDateValidationTest() {
        wd.get(mainPage);
        ComputersListPage.addNewComputerButton();
        AddNewComputerPage.addNewComputerWithNameAndIntroducedDateOnly("1");
    }
    @Test
    public void addComputerDiscontinuedDateValidationTest() {
        wd.get(mainPage);
        ComputersListPage.addNewComputerButton();
        AddNewComputerPage.addNewComputerWithNameAndDiscontinuedDateOnly("1");
    }

    @AfterClass
    public static void shutDown() {
        wd.quit();
    }
}
