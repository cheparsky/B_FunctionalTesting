package GUITests.tests;

import GUITests.pages.AddNewComputerPage;
import GUITests.pages.ComputersListPage;
import GUITests.utils.MyDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class EditComputerFieldValidationTest {

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
    public void editComputerNameValidationTest() {
        wd.get(mainPage);
        ComputersListPage.selectComputerFromList();
        AddNewComputerPage.addNewComputerWithNameOnly("   ");
    }

    @Test
    public void editComputerIntroducedDateValidationTest() {
        wd.get(mainPage);
        ComputersListPage.selectComputerFromList();
        AddNewComputerPage.addNewComputerWithNameAndIntroducedDateOnly("1");
    }
    @Test
    public void editComputerDiscontinuedDateValidationTest() {
        wd.get(mainPage);
        ComputersListPage.selectComputerFromList();
        AddNewComputerPage.addNewComputerWithNameAndDiscontinuedDateOnly("1");
    }

    @AfterClass
    public static void shutDown() {
        wd.quit();
    }
}
