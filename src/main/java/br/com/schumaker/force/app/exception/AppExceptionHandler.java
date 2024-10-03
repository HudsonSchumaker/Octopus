package br.com.schumaker.force.app.exception;

import br.com.schumaker.force.framework.annotations.exception.ExceptionHandler;
import br.com.schumaker.force.framework.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.force.framework.exception.ErrorView;
import br.com.schumaker.force.framework.web.view.ResponseView;

import java.io.IOException;

/**
 * The AppExceptionHandler class.
 * It is responsible for handling the application exceptions.
 *
 * @see ExceptionHandler
 * @see GlobalExceptionHandler
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@GlobalExceptionHandler
public class AppExceptionHandler {

    // This method will handle all exceptions
    @ExceptionHandler
    public ResponseView<String> methodHandlerException(Exception e) {
        return ResponseView.conflict().body(e.getMessage()).build();
    }

    // This method will handle IOException
    @ExceptionHandler(IOException.class)
    public ResponseView<ErrorView> methodHandlerIOException(IOException e) {
        return ResponseView.badRequest().body(new ErrorView(e.getMessage(), "#8x0008")).build();
    }
}
