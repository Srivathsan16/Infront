#Solution 
This api is designed to serve the GET request for Instrument to fetch the short positions
1.We have isin as path variable
2.We have filters based on the fromdate and toDate on the event's date which can be provided as query param on the request
GET Request sample > http://localhost:8088/instruments/BMG9156K1018?fromDate=2022-01-22&toDate=2022-04-04

##Future Improvement
This application is designed with the KISS principle(Keep it simple and stupid) so that everyone can understand the application well.
Further improvements include performance testing and finding the bottlenecks while the number of request is high.
Based on the concurrency on the application or if there is high traffic on the GET method then proposing the following,

1. Scalability : Application can be scaled based on the number of request or based on the CPU and Memory usage using docker and Kubernetes as tools
2. Async : As mentioned kept the application simple.Even though I am using Connection Pooling , we can make the application async in case we need to serve more requests.Spring Webflux can also be used