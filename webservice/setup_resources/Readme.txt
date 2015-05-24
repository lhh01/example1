
##################################
#########Development Environment
1. App Server: Glassfish 3.1.1 (Java EE 6)
2. IDE: NetBeans 8.02
3. Build Tool: Maven 3
4. JDK 6
5. DB: SQL Server 2008
6. OS: Windows 7
7. Other tools: SOAPUI 5.1.3, QBrowser

##################################


################## Business Rules Assumption   #####################################################

1. IntegerRequest and Gcd are two entities which are one to one relationship

2. IntegerRequest uses integer1 and integer2 as business key




#######################################################################################################################################


#########          Run Instructions  ##############################################################

1. Create two databases in the SQL (1. gcd  2. gcd_test) & run createTables.sql under setup_resources folder to create tables in both DB

2. Copy jtds-1.2.8.jar to {your glassfish root}\domains\{your domain}\lib\ext

3. Go to glassfish Admin Console
	a. create JDBC resource for production DB gcd
	a. create JMS resource for testing environment
	
	OR  
	
   put following into "resources" element in the domain.xml
   <jdbc-connection-pool driver-classname="net.sourceforge.jtds.jdbc.Driver" datasource-classname="" res-type="java.sql.Driver" description="" name="jtds">
      <property name="URL" value="jdbc:jtds:sqlserver://localhost:1433/gcd"></property>
      <property name="user" value="sa"></property>
      <property name="password" value="123"></property>
    </jdbc-connection-pool>
    <jdbc-resource pool-name="jtds" description="" jndi-name="jdbc/jtds"></jdbc-resource>
    
    <connector-connection-pool description="" name="jms/TestConnectionFactory" resource-adapter-name="jmsra" connection-definition-name="javax.jms.QueueConnectionFactory" transaction-support="">
      <property name="AddressList" value="localhost:7676"></property>
    </connector-connection-pool>
    <connector-resource pool-name="jms/TestConnectionFactory" jndi-name="jms/TestConnectionFactory"></connector-resource>
    <admin-object-resource res-adapter="jmsra" res-type="javax.jms.Queue" description="" jndi-name="jms/TestQueue">
      <property description="null" name="Name" value="TestQueue"></property>
    </admin-object-resource>
	
	
4. Change env.S1AS_HOME property to your local glassfish installation in the webservice-ejb\pom.xml

5. Run Maven build in the IDE or command: mvn clean install -DskipTests

5. Deploy webservice-ear-1.0-SNAPSHOT.ear in the glassfish Admin Console

6. REST URL http://localhost:8080/gcd/rest/resources/integers   (Host name and port number may be changed at your local environment)
  
   GET for listing all integers
   POST for pushing integers, content-type:application/x-www-form-urlencoded

7. SOAP URL http://localhost:8080/gcd/GcdSoapService?Tester      (Host name and port number may be changed at your local environment)


#########################################################################################################################################




################################# Load Test Run Instruction

1. Open SOAPUI, Load project files REST-gcd-soapui-project.xml & SOAP-gcd-soapui-project.xml under setup_resources folder( File >Import Projects)

2. Run the load tests under Load Test nodes for REST & SOAP test cases

3. Each load test uses 20 threads within 10 seconds to send requests to server
   E.G. during my test, 605 push integer requests were sent to server by 20 threads within 10 seconds, 
        605 requests were saved in DB and 605 messages were sent to message queue successfully

