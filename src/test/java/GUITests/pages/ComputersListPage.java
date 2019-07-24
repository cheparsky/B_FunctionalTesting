package GUITests.pages;

import GUITests.utils.MyDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComputersListPage {

    @FindBy(id = "add")
    public static WebElement addNewComputerButton; //computers list - create new computer button

    @FindBy(className = "alert-message")
    public static WebElement alertMessageWarning; //computers list - alert message warning

    @FindBy(id = "searchbox")
    public static WebElement searchInput; //computers list - search input

    @FindBy(id = "searchsubmit")
    public static WebElement searchButton; //computers list - search submit button

    @FindBy(xpath = "//h1[contains(text(),'One computer found')]")
    public static WebElement searchResultInfo; //computers list - search info result

    @FindBy(xpath = "//*[@id='main']/table/tbody/tr[1]/td[1]/a")
    public static WebElement foundElementButton; //computers list - search info result

    //Action that clicks on 'Create' button
    public static void addNewComputerButton() {
        MyDriver.clickButtonCheckIfElementIsVisible(ComputersListPage.addNewComputerButton,
                AddNewComputerPage.createNewComputerButton,
                "Computers list page: Create button is clickable.",
                "Computers list page: Create button is NOT clickable.",
                "Computers list page do not contain Create button."
        );
    }

    public static void findComputerByName(String computerName) {
        if (!computerName.isEmpty()) {
            searchInput(computerName);
            searchButton();
        }
    }

    private static void searchInput(String computerName) {
        MyDriver.inputData(searchInput,
                computerName,
                "Computers list page: search input is not editable.",
                "Computers list page do not contain search input.");
    }

    private static void searchButton() {
        MyDriver.clickButtonCheckIfElementIsVisible(searchButton,
                searchResultInfo,
                "Computers list page: Search button is clickable.",
                "Computers list page: Search button is NOT clickable.",
                "Computers list page do not contain Search button.");
    }

    public static void selectComputerFromList() {
        MyDriver.clickButtonCheckIfElementIsVisible(foundElementButton,
                EditComputersPage.saveComputerButton,
                "Search result page: computer is clickable.",
                "Search result page: computer is NOT clickable.",
                "Search result page do not contain computer.");
    }

    //Constructor that add Page Object Pattern to this class
    public static void setPageObject() {
        PageFactory.initElements(MyDriver.wd, new ComputersListPage());
    }


}
