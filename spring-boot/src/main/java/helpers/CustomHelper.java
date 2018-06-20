package helpers;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class CustomHelper {
    CharSequence foo() {
        return "bar";
    }
}