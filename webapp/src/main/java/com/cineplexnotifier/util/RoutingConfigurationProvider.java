package com.cineplexnotifier.util;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;

@RewriteConfiguration
public class RoutingConfigurationProvider extends HttpConfigurationProvider {
  @Override
  public int priority() {
    return 10;
  }

  @Override
  public Configuration getConfiguration(final ServletContext context) {
    return ConfigurationBuilder.begin().addRule()
        .when(Direction.isInbound().and(Path.matches("/movies/{page}")))
        .perform(Forward.to("/index.html")).addRule()
        .when(Direction.isInbound().and(Path.matches("/privacypolicy")))
        .perform(Forward.to("/index.html"));
  }

}
