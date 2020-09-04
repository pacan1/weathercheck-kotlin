Feature('');

Scenario('I can get to see African Countires on the weather API', (I) => {
    I.amOnPage('https://www.metaweather.com/api/');
    I.click('Home');
    I.see('Dublin');
    I.click('Africa');
    I.see('Democratic Republic of the Congo')
});


Scenario('I can select API', (I) => {
    I.amOnPage('https://www.metaweather.com/api/');
    I.click('API');
    I.see('Drop me an email');
    I.click('Drop me an email');
});


Scenario('I can search', (I) => {
    I.amOnPage('https://www.metaweather.com/search/?q=Mayo');
    I.type('Mayo');
    I.click('Search');
    I.see('Sorry, no locations found')
});