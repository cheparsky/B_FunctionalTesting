package GUITests.tests;

import GUITests.pages.AddNewComputerPage;
import GUITests.pages.ComputersListPage;
import GUITests.pages.EditComputersPage;
import GUITests.utils.MyDriver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDTest {

    public static WebDriver wd;
    public static int waitTime = 10;
    public static String mainPage = "http://computer-database.herokuapp.com";
    public static String computerListPage = "http://computer-database.herokuapp.com/computers";

    @BeforeClass
    public static void setUp() {
        wd = MyDriver.runWDriver("Chrome");
        MyDriver.setWebDriverWait(CRUDTest.waitTime);
        MyDriver.setAllPageObject();
        wd.get(mainPage);
        Assert.assertTrue(wd.getCurrentUrl().equals(computerListPage));
    }

    @Test
    public void CRUD_01_addComputerTest() {
        Assert.assertTrue("Current page is not a list of computer.", MyDriver.ifWWWEqual(computerListPage));
        ComputersListPage.addNewComputerButton();
        AddNewComputerPage.addNewComputer();
        Assert.assertTrue(ComputersListPage.alertMessageWarning.getText().contains("Done! Computer "+AddNewComputerPage.computerName+" has been created"));
    }

    @Test
    public void CRUD_02_readAndEditComputerTest() {
        Assert.assertTrue("Current page is not a list of computer.", MyDriver.ifWWWEqual(computerListPage));
        ComputersListPage.findComputerByName(AddNewComputerPage.computerName);
        ComputersListPage.selectComputerFromList();
        EditComputersPage.updateComputerInfo();
        Assert.assertTrue(ComputersListPage.alertMessageWarning.getText().contains("Done! Computer "+AddNewComputerPage.computerName+" has been updated"));
        //in real application we also check if data has really changed by comparing old and new value
    }

    @Test
    public void CRUD_03_deleteComputerTest() {
        Assert.assertTrue("Current page is not a list of computer.", MyDriver.ifWWWEqual(computerListPage));
        ComputersListPage.findComputerByName(AddNewComputerPage.computerName);
        ComputersListPage.selectComputerFromList();
        EditComputersPage.deleteComputerButton();
        Assert.assertTrue(ComputersListPage.alertMessageWarning.getText().contains("Done! Computer has been deleted"));
    }

    @AfterClass
    public static void shutDown() {
        wd.quit();
    }
}
