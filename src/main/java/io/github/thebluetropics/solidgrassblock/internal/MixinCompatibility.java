package io.github.thebluetropics.solidgrassblock.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class MixinCompatibility {
  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.METHOD)
  public @interface Low {
    /* ... */
  }

  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.METHOD)
  public @interface High {
    /* ... */
  }
}
