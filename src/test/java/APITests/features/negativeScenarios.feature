@test

Feature: Negative scenarios

  Background:
    * call read('classpath:APITests/commonFunctions.feature')
    * url 'http://computer-database.herokuapp.com'
    * def name = '00AC'+randomString(5)


  Scenario Outline: <ID> - Adding new computer - <DESCRIPTION> - expected status 400

  Here we validate the ability of application to accept incorrect values while we are adding a new computer.
  We validate only the response status, a front-end validation errors on the 'Add new computer' page we validate using selenium.

    * path 'computers'
    * configure headers = sessionHeaders
    * def payload = 'company='+<COMPANY>+'&discontinued='+<DISCONTINUED>+'&introduced='+<INTRODUCED>+'&name='+<NAME>
    When request payload
    And method post
    Then status 400
    #next, here we can check if web element "<div class="clearfix error">" appears near tested input


    Examples: discontinued
      | ID                        | DESCRIPTION                      | COMPANY | DISCONTINUED | INTRODUCED    | NAME |
      | api-test-negative-001-001 | 'discontinued = 1'               | 1       | 1             | currentDate() | name |
        # here we have error because currently 'discontinued date' in the past is accepted, however the 'discontinued date' in the past can`t be accepted
      | api-test-negative-001-002 | 'discontinued in the past'       | 30      | pastDate(100) | currentDate() | name |
      | api-test-negative-001-003 | 'discontinued = 0000-00-00'      | 31      | '0000-00-00'  | currentDate() | name |
        # here we have error because currently 'discontinued date' with minus is accepted, however the 'discontinued date' with minus can`t be accepted
      | api-test-negative-001-004 | 'discontinued  date with minus'  | 32      | '-2019-07-23' | currentDate() | name |
      | api-test-negative-001-005 | 'discontinued  = true (boolean)' | 32      | true          | currentDate() | name |

    Examples: company
      | ID                        | DESCRIPTION                   | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-negative-002-001 | 'company = true (boolean)'    | true    | futureDate(100) | currentDate() | name |
      | api-test-negative-002-002 | 'company = * (not int)'       | '*'     | futureDate(100) | currentDate() | name |
        #here we have error because currently, company can has value out of the range, however it is can`t be accepted
      | api-test-positive-002-003 | 'company = 0 (out of range)'  | 0       | futureDate(100) | currentDate() | name |
      | api-test-positive-002-004 | 'company = 44 (out of range)' | 44      | futureDate(100) | currentDate() | name |


    Examples: introduced
      | ID                        | DESCRIPTION                   | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-negative-003-001 | 'introduced = 1'              | 2       | futureDate(100) | 1             | name |
      | api-test-negative-003-002 | 'introduced = 1'              | 4       | futureDate(100) | '0000-00-00'  | name |
        # here we have error because currently 'introduced date' in the past is accepted but the 'introduced date' in the past can`t be accepted
      | api-test-negative-003-003 | 'introduced in the past'      | 16      | futureDate(100) | pastDate(100) | name |
        # here we have error because currently 'introduced date' with minus is accepted but the 'introduced date' with minus can`t be accepted
      | api-test-negative-003-004 | 'introduced date with minus'  | 14      | futureDate(100) | '-2019-07-23' | name |
      | api-test-negative-003-005 | 'introduced = true (boolean)' | 14      | futureDate(100) | true          | name |

    Examples: name
      | ID                        | DESCRIPTION                  | COMPANY | DISCONTINUED   | INTRODUCED    | NAME                     |
      | api-test-negative-004-001 | 'name contains only space'   | 13      | futureDate(100) | currentDate() | '  '                     |
      | api-test-negative-004-002 | 'name = '=abc''              | 13      | futureDate(100) | currentDate() | '=abc'                   |
      #here we have error because currently the name which contains only symbols is accepted but the name which contains only symbols can`t be accepted
      | api-test-negative-004-003 | 'name contains only symbols' | 23      | futureDate(100) | currentDate() | randomSpecialSymbols(15) |


  Scenario Outline: <ID> - Deleting computer - <DESCRIPTION> - expected status <STATUS>

  Here we validate the ability of application to delete a computer that doesn`t exist.
  We validate only the response status, a front-end validation errors in deleting a computer process we validate using selenium.

    * path 'computers/'+<COMPUTER_NUMBER>+'/delete'
    * configure headers = sessionHeaders
    When request ''
    And method post
    Then status <STATUS>

    Examples:
      | ID                        | DESCRIPTION                                | COMPUTER_NUMBER                     | STATUS |
      | api-test-negative-005-001 | id of computer is too long                 | 60000000000000000000000000000000000 | 400    |
        #here we have error because currently we can delete id of the computer that doesn`t exist however this situation can`t be accepted
      | api-test-negative-005-002 | id of computer doesn`t exist (positive id) | 6000000000                          | 404    |
        #here we have error because currently we can delete id of the computer that doesn`t exist however this situation can`t be accepted
      | api-test-negative-005-003 | id of computer doesn`t exist (negative id) | -1                                  | 404    |


  Scenario Outline: <ID> - Reading info about a computer - <DESCRIPTION> - expected status <STATUS>

  Here we validate the ability of application to read a computer info that doesn`t exist.
  We validate only the response status, a front-end validation errors in reading a computer info process we validate using selenium.

    * path 'computers/'+<COMPUTER_NUMBER>
    * configure headers = sessionHeaders
    When request
    And method get
    Then status <STATUS>

    Examples:
      | ID                        | DESCRIPTION                                | COMPUTER_NUMBER                     | STATUS |
      | api-test-negative-006-001 | id of computer is too long                 | 60000000000000000000000000000000000 | 400    |
      | api-test-negative-006-002 | id of computer doesn`t exist (positive id) | 6000000000                          | 404    |
      | api-test-negative-006-003 | id of computer doesn`t exist (negative id) | -1                                  | 404    |


  Scenario Outline: <ID> - Editing computer - <DESCRIPTION> - expected status 400

  Here we validate the ability of application to accept incorrect values while we are editing a computer.
  We validate only the response status, a front-end validation errors on the 'Edit computer' page we validate using selenium.

      #here we add a new computer the info of which we are going to edit
    * path 'computers'
    * configure headers = sessionHeaders
    * def payload = '&name='+<NAME>
    When request payload
    And method post
    And status 200

      #here we are looking for the id of added computer
    * def computerId = Utils.getComputerId(response, <NAME>)

      #here we edit info for added computer
    * path 'computers/'+computerId
    * configure headers = sessionHeaders
    * def payload = 'company='+<COMPANY>+'&discontinued='+<DISCONTINUED>+'&introduced='+<INTRODUCED>+'&name='+<NAME>
    When request payload
    And method post
    Then status 400

      #here we delete added and edited computer in order to not to clutter up the base
    * path 'computers/'+computerId+'/delete'
    * configure headers = sessionHeaders
    When request ''
    And method post
    Then status 200

    # in real application we should also check if after editing, if the info has really changed

    Examples: discontinued
      | ID                        | DESCRIPTION                     | COMPANY | DISCONTINUED | INTRODUCED    | NAME |
      | api-test-negative-007-001 | 'discontinued = 1'              | 11      | 1             | currentDate() | name |
        # here we have error because currently 'discontinued date' in the past is accepted however the 'discontinued date' in the past can`t be accepted
      | api-test-negative-007-002 | 'discontinued in the past'      | 12      | pastDate(100) | currentDate() | name |
      | api-test-negative-007-003 | 'discontinued = 0000-00-00'     | 13      | '0000-00-00'  | currentDate() | name |
        # here we have error because currently 'discontinued date' with minus is accepted however the 'discontinued date' with minus can`t be accepted
      | api-test-negative-007-004 | 'discontinued  date with minus' | 14      | '-2019-07-23' | currentDate() | name |

    Examples: company
      | ID                        | DESCRIPTION   | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-negative-008-001 | 'company = 0' | true    | futureDate(100) | currentDate() | name |
      | api-test-negative-008-002 | 'company = 0' | '*'     | futureDate(100) | currentDate() | name |


    Examples: introduced
      | ID                        | DESCRIPTION                  | COMPANY | DISCONTINUED   | INTRODUCED    | NAME |
      | api-test-negative-009-001 | 'introduced = 1'             | 21      | futureDate(100) | 1             | name |
      | api-test-negative-009-002 | 'introduced = 1'             | 24      | futureDate(100) | '0000-00-00'  | name |
        # here we have error because currently 'introduced date' in the past is accepted however the 'introduced date' in the past can`t be accepted
      | api-test-negative-009-003 | 'introduced in the past'     | 15      | futureDate(100) | pastDate(100) | name |
        # here we have error because currently 'introduced date' with minus is accepted however the 'introduced date' with minus can`t be accepted
      | api-test-negative-009-004 | 'introduced date with minus' | 18      | futureDate(100) | '-2019-07-23' | name |

    Examples: name
      | ID                        | DESCRIPTION                  | COMPANY | DISCONTINUED   | INTRODUCED    | NAME                     |
      | api-test-negative-010-001 | 'name contains only space'   | 13      | futureDate(100) | currentDate() | '  '                     |
      | api-test-negative-010-002 | 'name = '=abc''              | 13      | futureDate(100) | currentDate() | '=abc'                   |
      #here we have error because currently the name which contains only symbols is accepted but the name which contains only symbols can`t be accepted
      | api-test-negative-010-003 | 'name contains only symbols' | 23      | futureDate(100) | currentDate() | randomSpecialSymbols(15) |


  Scenario Outline: <ID> - Page number test - <DESCRIPTION> - expected status 400

  Here we validate the ability of application to go to the non-existing page or page without any computers.
  We validate only the response status, a front-end validation errors in reading a computer info process we validate using selenium.

    * path 'computers'
    * param p = <PAGE_NUMBER>
    * configure headers = sessionHeaders
    When request
    And method get
    Then status 400

    Examples:
      | ID                        | DESCRIPTION                   | PAGE_NUMBER         |
      | api-test-negative-011-001 | 'page number is too long'     | '60000000000000000' |
      | api-test-negative-011-002 | 'page number is charakter'    | 'b'                 |
      | api-test-negative-011-003 | 'page number is symbol'       | '*'                 |
      | api-test-negative-011-004 | 'page number is boolean'      | 'true'              |
        #here we have error because there are no valiadtions on params, if the page number is less than 0 we get first page however we have to get status 400
      | api-test-negative-011-005 | 'not exited page number - -1' | '-1'                |
         #here we have error because there are no valiadtions on params, if page doesn`t contain any computers howecer we have to get status 400
      | api-test-negative-011-006 | 'page without any computers'  | '60000'             |
