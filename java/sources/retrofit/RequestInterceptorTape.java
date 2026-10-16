package retrofit;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class RequestInterceptorTape implements RequestInterceptor.RequestFacade, RequestInterceptor {
    private final List<CommandWithParams> tape = new ArrayList();

    private enum Command {
        ADD_HEADER { // from class: retrofit.RequestInterceptorTape.Command.1
            @Override // retrofit.RequestInterceptorTape.Command
            public void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2) {
                requestFacade.addHeader(str, str2);
            }
        },
        ADD_PATH_PARAM { // from class: retrofit.RequestInterceptorTape.Command.2
            @Override // retrofit.RequestInterceptorTape.Command
            public void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2) {
                requestFacade.addPathParam(str, str2);
            }
        },
        ADD_ENCODED_PATH_PARAM { // from class: retrofit.RequestInterceptorTape.Command.3
            @Override // retrofit.RequestInterceptorTape.Command
            public void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2) {
                requestFacade.addEncodedPathParam(str, str2);
            }
        },
        ADD_QUERY_PARAM { // from class: retrofit.RequestInterceptorTape.Command.4
            @Override // retrofit.RequestInterceptorTape.Command
            public void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2) {
                requestFacade.addQueryParam(str, str2);
            }
        },
        ADD_ENCODED_QUERY_PARAM { // from class: retrofit.RequestInterceptorTape.Command.5
            @Override // retrofit.RequestInterceptorTape.Command
            public void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2) {
                requestFacade.addEncodedQueryParam(str, str2);
            }
        };

        abstract void intercept(RequestInterceptor.RequestFacade requestFacade, String str, String str2);
    }

    RequestInterceptorTape() {
    }

    @Override // retrofit.RequestInterceptor.RequestFacade
    public void addHeader(String str, String str2) {
        this.tape.add(new CommandWithParams(Command.ADD_HEADER, str, str2));
    }

    @Override // retrofit.RequestInterceptor.RequestFacade
    public void addPathParam(String str, String str2) {
        this.tape.add(new CommandWithParams(Command.ADD_PATH_PARAM, str, str2));
    }

    @Override // retrofit.RequestInterceptor.RequestFacade
    public void addEncodedPathParam(String str, String str2) {
        this.tape.add(new CommandWithParams(Command.ADD_ENCODED_PATH_PARAM, str, str2));
    }

    @Override // retrofit.RequestInterceptor.RequestFacade
    public void addQueryParam(String str, String str2) {
        this.tape.add(new CommandWithParams(Command.ADD_QUERY_PARAM, str, str2));
    }

    @Override // retrofit.RequestInterceptor.RequestFacade
    public void addEncodedQueryParam(String str, String str2) {
        this.tape.add(new CommandWithParams(Command.ADD_ENCODED_QUERY_PARAM, str, str2));
    }

    @Override // retrofit.RequestInterceptor
    public void intercept(RequestInterceptor.RequestFacade requestFacade) {
        for (CommandWithParams commandWithParams : this.tape) {
            commandWithParams.command.intercept(requestFacade, commandWithParams.name, commandWithParams.value);
        }
    }

    private static final class CommandWithParams {
        final Command command;
        final String name;
        final String value;

        CommandWithParams(Command command, String str, String str2) {
            this.command = command;
            this.name = str;
            this.value = str2;
        }
    }
}
