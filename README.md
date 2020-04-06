# CS328-Distributed-and-Cloud-Computing

This repository is built for the codes and resources of the SUStech-CS328 course.

## Assignment 1
In this assignment, the program builds a simple ﬁle server using Java RMI framework. 

More speciﬁcally, the ﬁle server implements the following functionalities: 

+ read 
+ create 
+ edit 
+ delete 
+ copy 
+ move 
+ rename. 

Also, ﬁle status support the info such as size, last modify time and last access time. The program is developed as a client server application. The ﬁles are stored as actual ﬁles on local ﬁlesystem (In this case, store all ﬁles under the same directory of the server .class ﬁle) on the server side.

## Assignment 2
In this assignment, this program implements a funny-functional, simplified version of Java RMI framework.
There are three main parts for implementing your own RMI framework based on the existing code: 

1. The first part is designing and implementing communication protocols for stub/skeleton communication using
TCP socket. In this part the program implements a multi-threaded skeleton class to handle incoming
requests. 

2. The second part is implementing the generation of stub, which provides transparency when clients
are doing remote invocation. This part includes creating proxies and appropriate invocation handlers for
proxies.

3. The third part is implementing registry, which also utilizes the infrastructures for remote method
invocation, but may require special processing when invoking certain methods.
