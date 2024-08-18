package br.com.schumaker.octopus.app.exception;

import br.com.schumaker.octopus.framework.annotations.exception.ExceptionHandler;
import br.com.schumaker.octopus.framework.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.exception.ErrorView;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;

@GlobalExceptionHandler
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseView<String> handle(Exception e) {
       return ResponseView.of("Exception", 300);
    }

    @ExceptionHandler(IOException.class)
    public ResponseView<ErrorView> handle2(IOException e) {
        return ResponseView.of(new ErrorView(e.getMessage(), 400), 400);
    }
}
