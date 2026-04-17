# Code Citations

## License: unknown
https://github.com/TeamProject0416/SkyCodeProject/tree/b6d32170ef4c8c786c7e735ba7375f3931dbfddb/skycode/src/main/java/teamproject/skycode/review/GlobalExceptionHandler.java

```
.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
```

