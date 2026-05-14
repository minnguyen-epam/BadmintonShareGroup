package com.app.badmintonsharegroup.config

import com.app.badmintonsharegroup.domain.entity.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect

@Configuration
@EnableR2dbcAuditing
class R2dbcConfig {

    @Bean
    fun r2dbcCustomConversions(): R2dbcCustomConversions = R2dbcCustomConversions.of(
        PostgresDialect.INSTANCE,
        RoleWritingConverter(),
        RoleReadingConverter(),
    )
}

@WritingConverter
class RoleWritingConverter : Converter<Role, String> {
    override fun convert(source: Role): String = source.name
}

@ReadingConverter
class RoleReadingConverter : Converter<String, Role> {
    override fun convert(source: String): Role = Role.valueOf(source)
}
