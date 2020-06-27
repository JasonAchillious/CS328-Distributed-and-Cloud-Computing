# CS328-Distributed-and-Cloud-Computing

This repository is built for the codes and resources of the SUStech-CS328 course.

## Remote file IO
The program builds a simple ﬁle server using the Java RMI framework. 

More speciﬁcally, the ﬁle server implements the following functionalities: 

+ read 
+ create 
+ edit 
+ delete 
+ copy 
+ move 
+ rename. 

Also, ﬁle status supports the info such as size, last modify time, and last access time. The program is developed as a client-server application. The ﬁles are stored as actual ﬁles on the local ﬁlesystem (In this case, store all ﬁles under the same directory of the server .class ﬁle) on the server-side.

## Lite RMI framework
The program implements a funny-functional, simplified version of the Java RMI framework.
There are three main parts for implementing your own RMI framework based on the existing code: 

1. The first part is designing and implementing communication protocols for stub/skeleton communication using
TCP socket. In this part, the program implements a multi-threaded skeleton class to handle incoming requests. 

2. The second part is implementing the generation of the stub, which provides transparency when clients
are doing remote invocation. This part includes creating proxies and appropriate invocation handlers for
proxies.

3. The third part is implementing registry, which also utilizes the infrastructures for remote method
invocation, but may require special processing when invoking certain methods.

## LiteMQ
The project implements a simpliﬁed, Kafka-like distributed messaging system called LiteMQ. A list of functionalities you need to implement is as follows:

 + Publish/Subscribe Pattern, that includes: creating new topics, publishing message on a speciﬁc topic, receiving message from a topic 
 
 + Consumer/Producer API. You need to provide a set of interfaces in the from of Java classes, so that user could directly use your API to do messaging.  
 
 + A central conﬁguration/coordination service, named LiteZK. This service should run standalone.  
  
 + Partitioning for topic: Each topic could be partitioned into several partitions, and each partition could be at the same or diﬀerent node(s).  

 + Fault tolerance for nodes: In case of a node failure, the system should be able to recover its execution process using replicas distributed among other nodes.

 + Leader election for nodes/partitions. Use LiteZK to elect controller of messaging system cluster and elect leaders of partitions using controller. 
  
 + Distributed LiteZK: Make LiteZK a distributed application, and implement fault tolerance and leader election (any algorithm you like, such as ring algorithm or the algorithm implemented in Apache Zookeeper)  + Exactly-once semantics in LiteMQ. This requires the implementation of at least once semantics and idempotent operation. 
  
 +  Batch operation and transactions in LiteMQ. This requires the implementation of transaction controller election and two-step commit algorithm.