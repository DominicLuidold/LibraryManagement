package at.fhv.teamg.librarymanagement.server.rest;

import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {
    @Override
    public Publisher<AuthenticationResponse> authenticate(
        @Nullable HttpRequest<?> httpRequest,
        AuthenticationRequest<?, ?> authenticationRequest
    ) {
        return Flowable.create(emitter -> {
            var loginResult =
                new UserService()
                    .authenticateUser(new LoginDto.LoginDtoBuilder()
                        .withUsername(authenticationRequest.getIdentity().toString())
                        .withPassword(authenticationRequest.getSecret().toString())
                        .build());
            if (loginResult.getType().equals(MessageDto.MessageType.SUCCESS)) {
                var roles = new ArrayList<String>();
                roles.add(loginResult.getResult().getUserRoleName().name());
                var attributes = new HashMap<String, Object>();
                attributes.put("isExternalLibrary", loginResult.getResult().isExternalLibrary());
                emitter.onNext(
                    new UserDetails(loginResult.getResult().getUsername(), roles, attributes));
                emitter.onComplete();
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }, BackpressureStrategy.ERROR);
    }
}
