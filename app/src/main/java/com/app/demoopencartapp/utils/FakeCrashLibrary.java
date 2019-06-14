package com.app.demoopencartapp.utils;

/** Not a real crash reporting library! */
public final class FakeCrashLibrary {
  public static void log( String tag, String message) {

  }

  public static void logWarning(Throwable t) {
    // TODO report non-fatal warning.
  }

  public static void logError(Throwable t) {
    // TODO report non-fatal error.
  }

  private FakeCrashLibrary() {
    throw new AssertionError("No instances.");
  }
}