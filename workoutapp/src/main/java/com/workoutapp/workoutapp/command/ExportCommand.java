package com.workoutapp.workoutapp.command;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command Pattern (Behavioural) — Command interface.
 *
 * Encapsulates an export request as an object, decoupling the invoker
 * (ProgressController) from the concrete export logic.
 */
public interface ExportCommand {
    void execute(HttpServletResponse response) throws IOException;
}
