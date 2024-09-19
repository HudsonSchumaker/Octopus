package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.framework.annotations.controller.Controller;
import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.security.JwtManager;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

@Controller("/token")
public class TokenController {

    @Post("/create/{subject}")
    public ResponseView<String> create(@PathVariable("subject") String subject) {
        return ResponseView.created()
                .body(JwtManager.generateToken(subject))
                .build();
    }
}
