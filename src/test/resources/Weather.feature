@regression

Feature: Return a location and weather

  Scenario Outline: When woeid is passed, the correct location is returned
    Given a woeid <woeid> is retrieved
    When the API returns successfully
    Then the woeid location is <location>

    Examples:
    | woeid | location     |
    | 44418 | London       |


  Scenario Outline: When an invalid woeid is passed, then not found is returned
    Given a woeid <woeid> is retrieved
    When the API returns Not Found
    Then the response contains "Not found."

    Examples:
      | woeid  |
      | 12345  |
      | 777767776  |
#      | dbvdbf |

  Scenario: Testing DI
    Given demo use of spring for DI