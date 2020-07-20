#Artio Server Example Project

This project will present a customized version of artio server where the user credentials will be necessary to logon.

The credentials were used in memory.

The client project should connect to this project using the FIX protocol and sending an authentication logon message.

When the client project doesn't need stay connected with this project It should send a disconnection message.

This project should be run after server initialization. As this project is a Spring Boot application and shoulb be build and run with the following:

- mvn clean package
- java -jar ./target/artio-server-1.0-SNAPSHOT-jar-with-dependencies.jar

###Sample Server Customized Authentication method

```
   @Override
    public boolean authenticate(AbstractLogonDecoder logon) {
        if(logon != null){
            if(logon.usernameAsString().equals(credentials.getUsername())){
                if(logon.passwordAsString().equals(credentials.getPassword())){
                    System.out.printf("Successful user authentication%n");
                    return true;
                }else{
                    System.out.printf("Some failure happens under user authentication%n");
                    return false;
                }
            }else{
                System.out.printf("Some failure happens under user authentication%n");
                return false;
            }
        }
        System.out.printf("Some failure happens under user authentication%n");
        return false;
    }
```

**See the client project [Fix Client](https://github.com/domenicosf/fix-client)**