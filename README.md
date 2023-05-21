# ZETTA

1. Acceptance criteria implementation
Using Java and Selenium/Webdriver, implement the following tests on the Chrome browser.
• Steps:
* Enter amazon.com and check the homepage
* Search by word “laptop”
* Add the non-discounted products in stock on the first page of the search results to the cart
* Go to cart and check if the products is the right

-----------------------------------------------------------------------------------------

2. Simple Site Crawl
Write a crawler that opens up the “Shop By Department” dropdown menu on the amazon website,
obtains a list of all department links and visits them to make sure that there are no dead links.
Your crawler should keep a list of visited links in a text file in the form (link, page title, status) , where
status can be “OK” or “Dead link”. After finishing, the crawler should name the file
<timestamp>_results.txt.
-----------------------------------------------------------------------------------------

3. API Test
https://jsonplaceholder.typicode.com/posts (GET) is a mock API endpoint which simulates the
retrievals of blog posts. Implement the following test scenarios:
Scenario: Counting posts for user <user> When I get a list of blog posts using the API endpoint Then
user <user> should have <numposts> posts.
The scenario should execute with the following values of (<user>,<numposts>): (5,10), (7,10), (9,10)
Scenario: Unique ID per post When I get a list of blog posts using the API endpoint Then each blog
post should have a unique ID
------------------------------------------------------------------------------------------
In this case, I used Postman through the browser to do the tests
------------------------------------------------------------------------------------------
API Test folder contains two ways to share the collection:
1. Json file
2. Collection access Key txt file