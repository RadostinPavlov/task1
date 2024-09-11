# Create a Java (Spring Boot) solution to contain an HTTP endpoint that:

1. Accepts a GET request with four optional query parameters to filter products or highlight some words (separated by commas in the query parameter) in their description:
                Example: /filter?MinPrice=10&MaxPrice=89&Highlight=red,blue&Size=Medium'
2. Reads the list of all products from the URL (think of this as the database):
https://run.mocky.io/v3/7af94347-277c-4d9e-8c4a-02f2c8573871
3. Design the endpoint response so that it contains (in JSON format):

                a. All products if the request has no parameters
                b. A filtered subset of products if the request has filter parameters
                c. A filter object to contain:
                                i. The minimum and the maximum price of all products in the source URL
                                ii. An array of strings to contain all sizes of all products in the source URL
                                iii. An string array of size ten to contain most common words in the product descriptions, excluding the most common five
                d. Add HTML tags to returned product descriptions in order to highlight the words provided in the highlight parameter.
                Example: “These trousers make a perfect pair with <em>green</em> or <em>blue</em> shirts.
### What we will look for:
                - Clean, readable, easy-to-understand code (sepearted functionality for filters WHERE applicable)
                - Performance, scalability, and security (not to federate properties directly to the "DB" (SQL injection secure))
                - Unit tests (sepearted functionality for filters WHERE applicable / DB layer seperate)
                - Dependency injection 
                - Appropriate logging including the full mocky.io response (but sensible not to include unneeded data)
                - Documentation for the users of your API (secure for administrators only) 
                - Configuration for EXT dependencies
                - Using template Design Pattern (REST call to mocky.io)
                - Use three tier architecture (web layer (controller), services/application, db/layer)
