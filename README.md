## MoneyTree Lite 
This is a lite version of MoneyTree app letting users to check the acounts balance and transaction.

#### Architecture
For this project MVVM is used, right now MVVM is most popular way of implementing apps so most poeple either have hands-on experience or some familarity with this Architecture which make this architecture easy to maintain and develop but also make assessing the candidate -> me easier.

#### Components
I divided the app into View, ViewModel and Repository:
* Repository is responsible for providing data and manipulating the user state through API call or database updates:
	* Retrieving/Updating the data
	* Converting the DTOs to Data model
	* Handling possible failures related to data operations
* ViewModel is responsible for handling screen/UI state and presenting the data:
	* Manage screen state like loading, error, success, etc.
	* Present data to UI in a way that no futher interpretation is need for View to show the UI 
* View is purely responsible for showing UI by using a model specificly tailored for the View:

#### Testing
As for testing everything except the View is supposed to be covered by unit tests, since UI is just passively showing data presented by ViewModel no test is necessary, we are testing to make sure:
* Repository
	* Data is retrieved/updated correctly
	* Exception or Errors are handled properly
	* Data mutation/mapping is done correctly
* ViewModel
	* Screen State is presented properly
	* Data presentation is done correctly

#### Future challenges
* Decide on how to communicate between different screens:
	* Fragment Result Listener
	* ViewModel sharing
	* Reactive Respository
* Decide on how to maintain ViewState:
	* Retained ViewModel
	* Use Repository to keep track of request state
* Decide on caching the data:
	* Http Cache
	* Repository cache
	* Local storage -> Database first apps to handle offline users?
* Error handling:
	* Wrapping responses -> E.g. Result
	* Throwing exceptions
	* Default values -> Not a good idea
