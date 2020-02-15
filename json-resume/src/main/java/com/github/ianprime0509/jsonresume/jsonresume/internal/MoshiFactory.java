package com.github.ianprime0509.jsonresume.jsonresume.internal;

import com.github.ianprime0509.jsonresume.jsonresume.json.JsonResumeJsonAdapterFactory;
import com.squareup.moshi.Moshi;

public final class MoshiFactory {
  private static final Moshi moshi =
      new Moshi.Builder().add(JsonResumeJsonAdapterFactory.getInstance()).build();

  private MoshiFactory() {}

  public static Moshi getMoshi() {
    return moshi;
  }
}
