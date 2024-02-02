package jwt;

import io.micronaut.http.annotation.*;

@Controller("/jwt")
public class JwtController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}