# MonitoringInfrastructure
monitoring infrastructure based on kafka msgs sent between services. This is a MVP for next projects

Step 1: Indices View
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/indices.png?raw=true)


Step 2: Example index from_a, means it's data collected from Kafka topic A
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/from_a.png?raw=true)


Step 3: Another example for data, this time, index from_d represents the last one in sequence
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/from_d.png?raw=true)


Step 4: Choosing UUID from index from_d
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/chosen_uuid_from_d.png?raw=true)

Step 5: Passing the UUID to REST service which runs query. Response is sorted by process names
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/response_sorted_by_services.png?raw=true)


Step 6: Another look at the same response, we can see send time is increasing from first message to the last one
![alt text](https://github.com/barakgonen/MonitoringInfrastructure/blob/main/screenshots/response_highlighted_by_send_time.png?raw=true)