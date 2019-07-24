package GUITests.utils;

import GUITests.pages.AddNewComputerPage;
import GUITests.pages.ComputersListPage;
import GUITests.pages.EditComputersPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDriver {

    //add all the necessary variables
    public static WebDriver wd;
    private static int timeToWait;
    public static List<String> errList = new ArrayList<String>();
    public static Date currentDate;
    public static String date;
    private static boolean isHeadless = Boolean.parseBoolean(System.getProperty("isHeadless", "false"));

    // We define the function that is responsible for connecting the appropriate driver for our tests
    public static WebDriver runWDriver(String browserName) {

        if (browserName.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup(); // if we use webdriver manager
            //System.setProperty("webdriver.geckodriver.driver", "src/test/resources/chromedriver.exe"); //if we use binary file
            wd = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup(); // if we use webdriver manager
            //System.setProperty("webdriver.chromedriver.driver", "src/test/resources/chromedriver.exe"); //if we use binary file
            if (isHeadless) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors"); //here we initiate headless options and below add to our driver
                wd = new ChromeDriver(options);
            } else {
                wd = new ChromeDriver();
            }
            wd.manage().window().maximize();
        }
        return wd;
    }

    // We define functions that are responsible for setting the waiting time for an element
    public static void setWebDriverWait(int timeSec) {
        timeToWait = timeSec;
    }

    // We define functions that is setting Page Object Pattern for our pages
    public static void setAllPageObject(){
        ComputersListPage.setPageObject();
        AddNewComputerPage.setPageObject();
        EditComputersPage.setPageObject();
    }

    // We define functions which shows if element is present on page
    public static boolean isElementVisible(WebElement e) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                //here we wait untill page will be fully loaded
                new WebDriverWait(wd, 20)
                        .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                                .equals("complete"));
                //here we waiting until element will be visible (diplayed) - the element is shown in the viewport of the browser, and only has
                // * height and width greater than 0px, and that its visibility is not "hidden"
                // * and its display property is not "none".
                new WebDriverWait(wd, timeToWait).until(ExpectedConditions.visibilityOf(e));
                boolean result = false;
                result = e.isDisplayed();
                System.out.println("if element " + e + " is visible: " + result);
                return result;
            } catch (TimeoutException exx) {
                //in real application when we get error we also should take a screenshot
                throw new RuntimeException("Element is not visible. RuntimeException occured on " + e);
            } catch (StaleElementReferenceException es) {
                attempts++;
            }
        }
        if (attempts > 2)
            throw new RuntimeException("Element is not visible. StaleElementReferenceException occured on " + e);
        return false;
    }

    // We define functions which checks if button is present and if is clickable
    public static void clickButton(WebElement element,  //webelement
                                   String successMsg, //Message when button is clickable
                                   String errMsg1,   //Message when button is non-clickable
                                   String errMsg2) {     //Message when is no button

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                System.out.println(successMsg);
            } catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // We define a function that checks if the button is, whether it is clickable and whether it redirects to the appropriate page
    public static void clickButtonCheckIfPageLoaded(WebElement element, //Webelement
                                                    String pageTo,  // The page to which driver must go after pressing the button
                                                    String successMsg, // The message when going to the correct page
                                                    String errMsg1, // Message if you do not go to the correct page, i.e. the button is not clickable
                                                    String errMsg2) { // Message if there is no button on the page

        int urlLenght = pageTo.length();
        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (wd.getCurrentUrl().substring(0, urlLenght).equals(pageTo)) {
                    System.out.println(successMsg + " You are on the page: " + wd.getCurrentUrl());
                } else {
                    throw new RuntimeException(errMsg1 + " You are still on the page: " + wd.getCurrentUrl());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e + errMsg1 + " You are still on the page: " + wd.getCurrentUrl(), e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Define a function that checks if the button is, is it clickable and whether the target element on the page is shown after clicking
    public static void clickButtonCheckIfElementIsVisible(WebElement element, //Webelement
                                                          WebElement elementCheck,  // Web element with which we check whether the button works
                                                          String successMsg, // Message if elementCheck is on the page
                                                          String errMsg1,// Message if the elementCheck is not on the page, i.e. maybe the button is not clickable
                                                          String errMsg2) { // Message if there is no button on the page

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (MyDriver.isElementVisible(elementCheck)) {
                    System.out.println(successMsg);
                } else {
                    throw new RuntimeException(errMsg1 + " You are still on the page: " + wd.getCurrentUrl());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(errMsg1 + " You are still on the page: " + wd.getCurrentUrl(), e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Define a function that checks if the button is, is it clickable and whether the target element on the page is not shown after clicking
    public static void clickButtonCheckIfElementIsNotVisible(WebElement element, //Webelement
                                                             WebElement elementCheck,  // Web element with which we check whether the button works
                                                             String successMsg, // Message if elementCheck is on the page
                                                             String errMsg1,// Message if the elementCheck is not on the page, i.e. maybe the button is not clickable
                                                             String errMsg2) { // Message if there is no button on the page

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (!MyDriver.isElementVisible(elementCheck)) {
                    System.out.println(successMsg);
                } else {
                    throw new RuntimeException(errMsg1);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    /// We define a function that checks whether there is input, whether you can enter data in the input and enter data
    public static void inputData(WebElement element, //Webelement
                                 String data, // Text entered to input
                                 String errMsg1,  // Message in case of non-editable input
                                 String errMsg2){	// Message if there is no input

        if (MyDriver.isElementVisible(element)) {
            try {
                element.clear();
                element.sendKeys(data);
                System.out.println("Data: " + data + ", has been entered");
            }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // We define a function that checks if there is an input, if you can delete data in the paste and delete data
    public static void clearData(WebElement element, //Webelement
                                 String errMsg1,  // Message in case of non-editable input
                                 String errMsg2){	// Message if there is no input

        if (MyDriver.isElementVisible(element)) {
            try {
                element.clear();
            }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Define a function that checks if the given and current pages are the same, if the same is OK
    public static boolean ifWWWEqual(String page) {
        int pageLength = page.length();
        try {
            if (wd.getCurrentUrl().substring(0, pageLength).equals(page)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Current page '" + wd.getCurrentUrl() + "' is different from the page you want '" + page + "'.");
            return false;
        }
    }

    // Define a function that checks if the given and current pages are not the same, if the same is OK
    public static boolean ifWWWNotEqual(String page) {
        int pageLength = page.length();
        try {
            if (wd.getCurrentUrl().substring(0, pageLength).equals(page)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Current page '" + wd.getCurrentUrl() + "' różni się od żądanej strony '" + page + "' (OK), but there is an error checking the URL.");
            return true;
        }
    }

    //Function that highlights element
    public static void highLighterMethod(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) wd;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        //Thread.sleep(500);
    }

    // Function that creates random string
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String randomString(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int characters = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(characters));
        }
        return builder.toString();
    }

    // Function that creates random date
    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }

    public static String getSpecifiedComputerId(String response, int specifiedComputer){
        String computerId = null;
        String[] computers = response.split("<td><a href=\"/computers/");
        return computers[specifiedComputer].substring(0,3);
    }

    public static String getComputerId(String response, String computerName){
        String computerId = null;
        String[] computers = response.split("<td><a href=\"/computers/");
        for (String computer: computers) {
            computer = computer.substring(0,14);
            if (computer.contains(computerName))
                computerId = computer.substring(0,3);
        }
        return computerId;
    }

    public static String getCurrentDate(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
    }

    public static String getFutureDate(int numberOfDays){
        LocalDate futureDate = LocalDate.now().plusDays(numberOfDays);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(futureDate);
    }
}
