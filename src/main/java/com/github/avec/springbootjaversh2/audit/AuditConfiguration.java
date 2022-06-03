package com.github.avec.springbootjaversh2.audit;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfiguration {

    @Bean
    public Javers javers() {
        return JaversBuilder.javers().build();
    }
}
