package br.com.schumaker.octopus.app.exception;

import br.com.schumaker.octopus.framework.annotations.exception.ExceptionHandler;
import br.com.schumaker.octopus.framework.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.exception.ErrorView;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;

/**
 * The AppExceptionHandler class.
 * It is responsible for handling the application exceptions.
 *
 * @see ExceptionHandler
 * @see GlobalExceptionHandler
 * @see ErrorView
 * @see IOException
 * @see ResponseView
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@GlobalExceptionHandler
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseView<String> methodHandlerException(Exception e) {
        return ResponseView.conflict().body(e.getMessage()).build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseView<ErrorView> methodHandlerIOException(IOException e) {
        return ResponseView.badRequest().body(new ErrorView(e.getMessage(), "#8x0008")).build();
    }
}
