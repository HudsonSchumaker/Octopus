package br.com.schumaker.force.app.view;

import br.com.schumaker.force.framework.annotations.controller.Controller;
import br.com.schumaker.force.framework.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.annotations.controller.Post;
import br.com.schumaker.force.framework.security.JwtManager;
import br.com.schumaker.force.framework.web.view.ResponseView;

/**
 * The TokenController class.
 * It is responsible for controlling the token operations.
 *
 * @see PathVariable
 * @see JwtManager
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Controller("/token")
public class TokenController {

    @Post("/generate/{subject}")
    public ResponseView<String> create(@PathVariable("subject") String subject) {
        return ResponseView.created()
                .body(JwtManager.generateToken(subject))
                .build();
    }
}
