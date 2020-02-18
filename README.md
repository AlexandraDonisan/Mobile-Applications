# Travel Itinerary App

Want to visit a new country or region but don't know what exactly would be of your interest?
The Travel Itinerary App will be exactly what you were looking for.
 
Each user can manage his itineraries and those who had fascinating travel 
experiences can share them with the others.

The app will be based on a database storing travel journeys, walks or experiences. 
Based on the city you are interested in visiting, the app will help you find worth 
visiting sightseeings together with their *opening hours*, *entrance fees* and *ratings* 
given by other travelers or simply manage your own experiences. Based on the:
- length of the journey(1-2 hours, 2 days, etc.)
- objectives(tourism, birthday, romantic evening)
- transportation (done by foot, by car, by bus) 
- budget (up to $50, $50-100, $200-500) 

you can filter through the options. 

# projectIdea
Describe your project idea from the point of view of a client.
The client is not having any technical skills but has a very good idea of why he needs a mobile application for his users.
The details should cover the following requirements:
- The application should offer CRUD operations on the business objects. The main screen should be a list of the business objects and it should allow at least the following operations:
   - Create - create new elements that will be added to the list.
   - Read - present the elements in a list.
   - Update - allow the user to update an element found in the list.
   - Delete - delete existing elements from the list.
- The application should persist the content locally, to be able to allow offline access. And while online it should synchronize the content with a remote server (implemented also by you, in the language of your choice, no serverless solutions are allowed yet).
- The communication with the server should be managed using REST services, and it should have counterpart operations for each of the operations mentioned above.
- While offline the application will perform the following actions:
   - On update and delete - a message will be displayed, that the application is offline and the operation is not available.
   - On read - the content from the local database will be displayed.
   - On create - the input will be persisted in the local database and when the application will detect that the device is able to connect to the server will push all the created entries, while the device was offline.
