package br.com.dsf.artio.server;

import io.aeron.logbuffer.ControlledFragmentHandler.Action;
import org.agrona.DirectBuffer;
import uk.co.real_logic.artio.builder.Printer;
import uk.co.real_logic.artio.decoder.PrinterImpl;
import uk.co.real_logic.artio.library.OnMessageInfo;
import uk.co.real_logic.artio.library.SessionHandler;
import uk.co.real_logic.artio.messages.DisconnectReason;
import uk.co.real_logic.artio.session.Session;
import uk.co.real_logic.artio.util.AsciiBuffer;
import uk.co.real_logic.artio.util.MutableAsciiBuffer;

import static io.aeron.logbuffer.ControlledFragmentHandler.Action.*;
import static io.aeron.logbuffer.ControlledFragmentHandler.Action.ABORT;
import static io.aeron.logbuffer.ControlledFragmentHandler.Action.CONTINUE;

public class CustomisedSessionHandler implements SessionHandler {

    private final AsciiBuffer string = new MutableAsciiBuffer();
    private final Printer printer = new PrinterImpl();
    private final Credentials credentials = new Credentials("domenico", "domenico");

    public CustomisedSessionHandler(final Session session)
    {
    }

    @Override
    public Action onMessage(DirectBuffer buffer,
                            int offset,
                            int length,
                            int libraryId,
                            Session session,
                            int sequenceIndex,
                            long messageType,
                            long timestampInNs,
                            long position,
                            OnMessageInfo messageInfo) {

        if(session != null){
            if(session.username().equals(credentials.getUsername())){
                if(session.password().equals(credentials.getPassword())){
                    string.wrap(buffer);
                    System.out.printf("%d -> %s%n", session.id(), printer.toString(string, offset, length, messageType));

                    return CONTINUE;
                }else{
                    return ABORT;
                }
            }else{
                return ABORT;
            }
        }

        return ABORT;
    }

    @Override
    public void onTimeout(int libraryId, Session session) {

    }

    @Override
    public void onSlowStatus(int libraryId, Session session, boolean hasBecomeSlow) {

    }

    @Override
    public Action onDisconnect(int libraryId, Session session, DisconnectReason reason) {
        System.out.printf("%d Disconnected: %s%n", session.id(), reason);
        return CONTINUE;
    }

    @Override
    public void onSessionStart(Session session) {

    }
}
