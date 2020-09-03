@regression

Feature: Return a location and weather

  Scenario Outline: When woeid is passed, the correct location is returned
    Given a valid woeid <woeid> is retrieved
    When the API returns successfully
    Then the woeid location is <location>

    Examples:
    | woeid | location     |
    | 44418 | London       |
    | 44418 | NotLondon    |