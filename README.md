# Information Retrieval Integrated System ( I. R. I. S. )
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Is a LAN Based document retrieval and information system for the **Department of Science and Technology Provincial Science Technology Center of Bulacan**. It aims to provide easy retrieval of the documents frequently used in the organization's operation by providing a database of digitalized copy of the documents.


### Application Features


1. SETUP / GIA Projects Record Tracking.
2. Equipment Qoutations Tracking.
3. Training and Seminars.
4. Local Phone Directory.
5. Science and Technology Scholarship Records Keeping.
6. Good Manufacturing Process Certificate Generation.
7. Shared Documents.


### Preview


**Home**


[![Home Preview](https://raw.githubusercontent.com/melvinperello/javafx-dost3-bulacan-iris/master/readme-preview/home.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-dost3-bulacan-iris/master/readme-preview/home.PNG)


**About**


[![About Preview](https://raw.githubusercontent.com/melvinperello/javafx-dost3-bulacan-iris/master/readme-preview/about.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-dost3-bulacan-iris/master/readme-preview/about.PNG)


### Redundant Application Information Distribution (R.A.I.D)


*Redundant Application Information Distribution (RAID)* is an algorithm developed to ensure the safety and integrity of files in the system. The algorithm was designed to utilize all the active clients in the local network allowing live copy and distribution of the server files which allows the system to have a fault tolerant backup of vital information that are redundantly stored to each client. This will ensure safety of the data across the network in the event of a system failure.


One client will act as a server for other clients, if the server fails other active clients can act as a the new server since all the data are captured and replicated by the RAID algorithm.


### How to use ?

**Requires Polaris Java Library**


```bat
git clone https://github.com/melvinperello/polaris-java-library.git
cd polaris-java-library
mvn clean install
```


**Build and Run**
```bat
git clone https://github.com/melvinperello/javafx-dost3-bulacan-iris.git
cd javafx-dost3-bulacan-iris
mvn clean install

cd target
java -jar javafx-dost3-bulacan-iris-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```




Cheers,


Melvin