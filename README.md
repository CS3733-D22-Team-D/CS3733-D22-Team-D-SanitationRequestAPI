
# CS-3733 Team D Sanitation Request API
## API Details:
This is the source code for our service request API. Once packaged as a jar, this API allows for users to 
create sanitation requests. If there are any issues or bugs please email swkarkache@wpi.edu or message
`Samuel Karkache` on Slack.
## How to Run 
To run the API, use the method name `run` inside
the `StartAPI` class. In this method you must specify the following:
 
* @param `xCoord` starting x coordinate of the program window
* @param `yCoord` starting y coordinate of the program window
* @param `windowWidth` width of the program window
* @param `windowLength` height of the program window
* @param `cssPath` path to CSS styling file
* @param `destLocationID` locationObj where the request is done
* @throws `ServiceException` if there are any error during instantiation

Running this method will cause the FXML page to pop-up in a new window above your program.
## Interacting with the Database
Using the Facade design pattern, this API abstracts database management to three different classes:

* `EmployeeAPI` Allows for employeeObjs to be added, removed, or updated in the database
* `LocationAPI` Allows for locationObjs to be added or removed
* `SanitationReqAPI` Can be used to get all requests or delete a specific request

These classes allow for easy interaction with our database setup. Contact us with any questions. Most methods include JavaDocs in the source code. 


