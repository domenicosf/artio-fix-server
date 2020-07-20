package br.com.dsf.artio.server;

import uk.co.real_logic.artio.decoder.AbstractLogonDecoder;
import uk.co.real_logic.artio.validation.AuthenticationStrategy;
import uk.co.real_logic.artio.validation.MessageValidationStrategy;

public class CustomisedAuthentication implements AuthenticationStrategy {
    private final Credentials credentials = new Credentials("domenico", "domenico");

    public CustomisedAuthentication(){

    }

    public CustomisedAuthentication(final MessageValidationStrategy delegate){

    }

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
}
