package com.github.ianprime0509.jsonresume.jackson;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import java.util.function.Supplier;

class BuilderValueInstantiator<T> extends ValueInstantiator {
  private final Supplier<? extends T> supplier;

  private BuilderValueInstantiator(final Supplier<? extends T> supplier) {
    this.supplier = requireNonNull(supplier, "supplier");
  }

  public static <T> BuilderValueInstantiator<T> using(final Supplier<? extends T> supplier) {
    return new BuilderValueInstantiator<>(supplier);
  }

  @Override
  public boolean canCreateUsingDefault() {
    return true;
  }

  @Override
  public T createUsingDefault(final DeserializationContext context) {
    return supplier.get();
  }
}
