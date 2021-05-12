# DDLite

Example project demonstrating architecture patterns. App is a DoorDash lite app displaying restaurant information.

## Arch Details  

* Project makes use of Clean Architecture + Model View Intent (MVI) patterns for presentation separation. 
* Kotlin coroutines is used for background/async work, specifically Flow and StateFlow functions. 
* Retrofit is used to perform network operations. 
* Glide is used to render and load images. 
* A manual dependency injection implementation is used to provide dependencies. 
* Parcelize plugin is used to simplify object marshalling.


## App Features

App renders the restaurant list based on the parameters specified by the reference doc. The detail screen simply renders a subset of information about the restaurant.
