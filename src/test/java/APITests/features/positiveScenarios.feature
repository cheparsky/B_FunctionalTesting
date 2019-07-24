@test
Feature: Positive scenarios

  Background:
    * call read('classpath:APITests/commonFunctions.feature')
    * url 'http://computer-database.herokuapp.com'
    * def name = '00AC'+randomString(5)


  Scenario Outline: <ID> - Adding new computer - <DESCRIPTION> - expected status 200

  Here we validate the ability of application to accept correct values while we are adding a new computer.
  Also here we validate the ability of application to delete the added computer a step before.
  We validate only the response status, a front-end validation on the 'Add new computer' page we validate using selenium.

          #here we add new computer, the info of which we are going to edit
    * def companyName = <NAME>

    * path 'computers'
    * configure headers = sessionHeaders
    * def payload = 'company='+<COMPANY>+'&discontinued='+<DISCONTINUED>+'&introduced='+<INTRODUCED>+'&name='+companyName
    When request payload
    And method post
    Then status 200

          #here we are looking for the id of added computer
    * def computerId = Utils.getComputerId(response, companyName)

        #here we delete added computer in order to not to clutter up the base
    * path 'computers/'+computerId+'/delete'
    * configure headers = sessionHeaders
    When request ''
    And method post
    Then status 200



    Examples: discontinued
      | ID                         | DESCRIPTION                          | COMPANY | DISCONTINUED | INTRODUCED    | NAME |
      | api-test-positive-001-001  | 'discontinued with the future date'  | 8       | futureDate(1) | currentDate() | name |
      | api-test-positive-0021-002 | 'discontinued with the current date' | 11      | currentDate() | currentDate() | name |


    Examples: company
      | ID                        | DESCRIPTION    | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-positive-002-001 | 'company = 1'  | 1       | futureDate(100) | currentDate() | name |
      | api-test-positive-002-002 | 'company = 43' | 43      | futureDate(100) | currentDate() | name |


    Examples: introduced
      | ID                        | DESCRIPTION                        | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-positive-003-001 | 'introduced with the current date' | 2       | futureDate(100) | currentDate() | name |
      | api-test-positive-003-002 | 'introduced with the future date'  | 4       | futureDate(100) | futureDate(1) | name |


    Examples: name
        # these test cases can work wrong because I am searching for the added computers only on first page,
        # however in real application we would add searching for the added computer on all pages
      | ID                        | DESCRIPTION                     | COMPANY | DISCONTINUED   | INTRODUCED    | NAME                 |
      | api-test-positive-004-001 | 'name contains only characters' | 3       | futureDate(100) | currentDate() | 'AA'+randomString(5) |
      | api-test-positive-004-002 | 'name contains only integers'   | 3       | futureDate(100) | currentDate() | '00'+randomInt(5)    |


  Scenario Outline: <ID> - Editing an existing computer - <DESCRIPTION> - expected status 200

  Here we validate the ability of application to read a computer info and to accept correct values while we are editing a computer.
  We validate only the response status, front-end validation on the 'Edit computer' page we validate using selenium.

  Currently we update all fields, for correct work of this test, we should add functions which will get the field values from page, that we don`t edit,
  and then we will send only one updated value.

          #here we open our application and get a list of id of the computers
    * path 'computers'
    * configure headers = sessionHeaders
    When request
    And method get
    Then status 200

        #here we get the id of the first computer (almost always it will be the same id  during one test)
    * def computerId = Utils.getSpecifiedComputerId(response, 1)

        #here we read the info of the first computer
    * path 'computers/'+computerId
    * configure headers = sessionHeaders
    When request
    And method get
    Then status 200

      #here we edit the info for selected computer
    * path 'computers/'+computerId
    * configure headers = sessionHeaders
    * def payload = 'company='+<COMPANY>+'&discontinued='+<DISCONTINUED>+'&introduced='+<INTRODUCED>+'&name='+<NAME>
    When request payload
    And method post
    Then status 200



    Examples: discontinued
      | ID                        | DESCRIPTION                   | COMPANY | DISCONTINUED | INTRODUCED    | NAME |
      | api-test-positive-005-001 | 'discontinued = '2345-67-89'' | 8       | '2345-67-89' | currentDate() | name |


    Examples: company
      | ID                        | DESCRIPTION    | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-positive-006-001 | 'company = 34' | 34      | futureDate(100) | currentDate() | name |


    Examples: introduced
      | ID                        | DESCRIPTION                        | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-positive-007-001 | 'introduced with the current date' | 2       | futureDate(100) | currentDate() | name |


    Examples: name
      | ID                        | DESCRIPTION                     | COMPANY | DISCONTINUED   | INTRODUCED    | NAME                 |
      | api-test-positive-008-001 | 'name contains only characters' | 3       | futureDate(100) | currentDate() | 'AA'+randomString(5) |


  Scenario Outline: <ID> - Page number test - <DESCRIPTION> - expected status 200

  Here we validate the ability of application to go to the existing page with the computers.

    * path 'computers'
    * param p = <PAGE_NUMBER>
    * configure headers = sessionHeaders
    When request
    And method get
    Then status 200

    Examples:
      | ID                        | DESCRIPTION   | PAGE_NUMBER |
      | api-test-negative-009-001 | 'second page' | '1'         |
        #curently we have 678 computers and due to this, the last page with computers will be page 67
        #in real application we first have to check how many computers added and only after this chose the last page number and check it
      | api-test-negative-009-002 | 'page 67' | '67'         |