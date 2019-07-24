package GUITests.pages;

import GUITests.utils.MyDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

public class EditComputersPage {

    @FindBy(xpath = "//input[@class = 'btn primary']")
    public static WebElement saveComputerButton; //edit computer page - save button

    @FindBy(xpath = "//input[@class = 'btn danger']")
    public static WebElement deleteComputerButtom; //computers list - delete computer button

    //Function that updates a computer
    public static void updateComputerInfo() {
        AddNewComputerPage.computerName = "AC" + MyDriver.randomString(5);
        AddNewComputerPage.introducedDate = MyDriver.getCurrentDate();
        AddNewComputerPage.discontinuedDate = MyDriver.getFutureDate(1);
        //we take into account only first 18 companies because we can see them when we click on a list with companies
        //in real application we should take into account all the companies
        AddNewComputerPage.companyId = new Random().nextInt(18) + 1;

        AddNewComputerPage.computerNameInput(AddNewComputerPage.computerName);
        AddNewComputerPage.introducedDateInput(AddNewComputerPage.introducedDate);
        AddNewComputerPage.discontinuedDateInput(AddNewComputerPage.discontinuedDate);
        AddNewComputerPage.companyNameList(AddNewComputerPage.companyId);

        saveComputerButton();
    }

    //Action that clicks on 'Save' button
    public static void saveComputerButton() {
        MyDriver.clickButtonCheckIfElementIsVisible(saveComputerButton,
                ComputersListPage.alertMessageWarning,
                "Edit computer page: Update button is clickable.",
                "Edit computer page: Update button is NOT clickable.",
                "Edit computer page do not contain Update button.");
    }

    //Action that clicks on 'Delete' button
    public static void deleteComputerButton() {
        MyDriver.clickButtonCheckIfElementIsVisible(deleteComputerButtom,
                ComputersListPage.alertMessageWarning,
                "Edit computer page: Add button is clickable.",
                "Edit computer page: Add button is NOT clickable.",
                "Edit computer page do not contain Add button."
        );
    }

    //Constructor that add Page Object Pattern to this class
    public static void setPageObject() {
        PageFactory.initElements(MyDriver.wd, new EditComputersPage());
    }
}
