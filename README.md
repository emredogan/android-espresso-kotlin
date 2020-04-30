# Android Espresso
Example of the test framework Android Espresso, using Junit4

Instrumentation tests using the JUnit 5 are still on experimental stage, so JUnit4 is used in this project. Also note that since JUnit 5 is built with Java 8, it is required to use minSDK 26 in the project.

The project has a recycler view which you can add some destinations. UI tests confirms that the relevant ui elements exists together with correct values and the right object is created in the database.

There are two main tests in the project, one is created through the Espresso recorder and the other one through manual code. As you can see Espresso Test Recorder creates a much larger/hard to read code and also it is pretty slow as in April 2020.

Demonstration of the UI test can be seen below:


<img src=android_espresso_recording.gif width="300">
