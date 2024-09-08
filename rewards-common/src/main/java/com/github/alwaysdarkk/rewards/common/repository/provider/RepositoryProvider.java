package com.github.alwaysdarkk.rewards.common.repository.provider;

import com.github.alwaysdarkk.rewards.common.configuration.ConfigValue;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.SQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

@Data(staticConstructor = "of")
public class RepositoryProvider {

    private final File file;

    public SQLConnector setup() {
        final String sqlType = ConfigValue.get(ConfigValue::databaseType);
        switch (sqlType) {
            case "mysql": {
                final ConfigurationSection mysqlSection = ConfigValue.get(ConfigValue::mysqlSection);
                return mysqlDatabaseType(mysqlSection).connect();
            }

            case "sqlite": {
                final ConfigurationSection sqliteSection = ConfigValue.get(ConfigValue::sqliteSection);
                return sqliteDatabaseType(sqliteSection).connect();
            }

            default: {
                return null;
            }
        }
    }

    private SQLDatabaseType sqliteDatabaseType(ConfigurationSection configurationSection) {
        return SQLiteDatabaseType.builder()
                .file(new File(file, configurationSection.getString("file")))
                .build();
    }

    private SQLDatabaseType mysqlDatabaseType(ConfigurationSection configurationSection) {
        return MySQLDatabaseType.builder()
                .address(configurationSection.getString("address"))
                .username(configurationSection.getString("username"))
                .password(configurationSection.getString("password"))
                .database(configurationSection.getString("database"))
                .build();
    }
}