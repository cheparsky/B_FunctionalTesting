package GUITests.pages;

import GUITests.utils.MyDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class AddNewComputerPage {

    public static String computerName = "";
    static String introducedDate = "";
    static String discontinuedDate = "";
    static int companyId = 0;

    @FindBy(id = "name")
    public static WebElement computerNameInput; //add new computers page - first name input

    @FindBy(id = "introduced")
    public static WebElement introducedDateInput; //add new computers page - last name input

    @FindBy(id = "discontinued")
    public static WebElement discontinuedDateInput; //add new computers page - start date input

    @FindBy(id = "company")
    public static WebElement companyNameInput; //add new computers page - compnay name input

    @FindBy(xpath = "//input[@class = 'btn primary']")
    public static WebElement createNewComputerButton; //add new computers page - add button

    @FindBy(xpath = "//*[@id='main']/form/fieldset/div[1][@class = 'clearfix error']")
    public static WebElement computerNameErrror; //add new computers page - add button

    @FindBy(xpath = "//*[@id='main']/form/fieldset/div[2][@class = 'clearfix error']")
    public static WebElement introducedDateErrror; //add new computers page - add button

    @FindBy(xpath = "//*[@id='main']/form/fieldset/div[3][@class = 'clearfix error']")
    public static WebElement discontinuedDateErrror; //add new computers page - add button

    //Function which creates a new computer
    public static void addNewComputer() {
        computerName = "AC" + MyDriver.randomString(5);
        introducedDate = MyDriver.getCurrentDate();
        discontinuedDate = MyDriver.getFutureDate(1);
        //we take into account only first 18 companies, because we can see them when we click on a list with companies
        //in real application we should take into account all the companies
        companyId = new Random().nextInt(18) + 1;
        ;

        computerNameInput(computerName);
        introducedDateInput(introducedDate);
        discontinuedDateInput(discontinuedDate);
        companyNameList(companyId);

        createNewComputerButton();
    }

    //Function which creates a new computer only with name
    public static void addNewComputerWithNameOnly(String computerName) {
        computerNameInput(computerName);

        createNewComputerErrorButton(AddNewComputerPage.computerNameErrror);
    }

    //Function which creates a new computer only with name and introducedDate
    public static void addNewComputerWithNameAndIntroducedDateOnly(String introducedDate) {
        computerName = "AC" + MyDriver.randomString(5);

        computerNameInput(computerName);
        introducedDateInput(introducedDate);

        createNewComputerErrorButton(introducedDateErrror);
    }

    //Function which creates a new computer only with name and introducedDate
    public static void addNewComputerWithNameAndDiscontinuedDateOnly(String discontinuedDate) {
        computerName = "AC" + MyDriver.randomString(5);

        computerNameInput(computerName);
        discontinuedDateInput(discontinuedDate);

        createNewComputerErrorButton(discontinuedDateErrror);
    }

    private static void createNewComputerErrorButton(WebElement element) {
        MyDriver.clickButtonCheckIfElementIsVisible(createNewComputerButton,
                element,
                "Add new computers page: Create button is clickable.",
                "Add new computers page: Create button is NOT clickable.",
                "Add new computers page do not contain Create button.");

    }

    //Action that enters name to the appropriate input
    public static void computerNameInput(String computerName) {
        MyDriver.inputData(computerNameInput,
                computerName,
                "Add new computers page: name input is not editable.",
                "Add new computers page do not contain name input.");
    }

    //Action that enters introduced date to the appropriate input
    public static void introducedDateInput(String introducedDate) {
        MyDriver.inputData(introducedDateInput,
                introducedDate,
                "Add new computers page: introduced date input is not editable.",
                "Add new computers page do not contain introduced date input.");
    }

    //Action that enters discontinued date to the appropriate input
    public static void discontinuedDateInput(String discontinuedDate) {
        MyDriver.inputData(discontinuedDateInput,
                discontinuedDate,
                "Add new computers page: discontinued date input is not editable.",
                "Add new computers page do not contain discontinued date input.");
    }

    //Action that selects company in the appropriate input
    public static void companyNameList(int companyId) {
        companyNameInput();
        selectCompanyNameInput(companyId);
    }

    //Action that clicks on a list with companies
    public static void companyNameInput() {
        MyDriver.clickButton(companyNameInput,
                "Add new computers page: list of companies is clickable.",
                "Add new computers page: list of companies is NOT clickable.",
                "Add new computers page do not contain list of companies.");
    }

    public static void selectCompanyNameInput(int companyId) {
        companyId++;
        List<WebElement> el = MyDriver.wd.findElements(By.xpath("//*[@id='company']/option"));
        MyDriver.clickButton(el.get(companyId),
                "Add new computers page: company " + companyId + " is clickable.",
                "Add new computers page: company " + companyId + " is NOT clickable.",
                "Add new computers page do not contain company " + companyId + ".");
    }

    //Action that clicks on a 'create' button
    public static void createNewComputerButton() {
        MyDriver.clickButtonCheckIfElementIsVisible(createNewComputerButton,
                ComputersListPage.alertMessageWarning,
                "Add new computers page: Create button is clickable.",
                "Add new computers page: Create button is NOT clickable.",
                "Add new computers page do not contain Create button.");
    }

    //Constructor that adds Page Object Pattern to this class
    public static void setPageObject() {
        PageFactory.initElements(MyDriver.wd, new AddNewComputerPage());
    }
}
