package com.vsarzhynskyi.shop.items.demo.functionaltests;

import com.playtika.test.mariadb.EmbeddedMariaDBBootstrapConfiguration;
import com.playtika.test.mariadb.EmbeddedMariaDBDependenciesAutoConfiguration;
import liquibase.Liquibase;
import liquibase.changelog.RanChangeSet;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static java.lang.String.format;
import static java.sql.DriverManager.getConnection;

@SpringBootTest(
        classes = {
                EmbeddedMariaDBDependenciesAutoConfiguration.class,
                EmbeddedMariaDBBootstrapConfiguration.class,
                LiquibaseAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class
        },
        properties = {
                "spring.liquibase.change-log=db-migration/master.yml",
                "spring.liquibase.contexts=test"
        }
)
@RunWith(SpringRunner.class)
@Slf4j
public class DbMigrationTest {

    @Value("${spring.liquibase.change-log}")
    private String changeLog;
    @Value("${spring.liquibase.contexts}")
    private String liquibaseContext;
    @Value("${embedded.mariadb.host}")
    private String host;
    @Value("${embedded.mariadb.port}")
    private String port;
    @Value("${embedded.mariadb.schema}")
    private String schema;
    @Value("${embedded.mariadb.user}")
    private String user;
    @Value("${embedded.mariadb.password}")
    private String password;

    @Test
    public void shouldUpdateAndRollbackLiquibaseScripts() throws LiquibaseException, SQLException {
        var liquibase = getLiquibase();

        liquibase.update(liquibaseContext);

        var ranChangeLogList = liquibase.getDatabase().getRanChangeSetList().stream()
                .map(RanChangeSet::getChangeLog)
                .toList();

        int numberOfRequiredChangesToRollback = ranChangeLogList.size();
        log.info("Rollback [{}] changes", numberOfRequiredChangesToRollback);
        liquibase.rollback(numberOfRequiredChangesToRollback, liquibaseContext);

        liquibase.update(liquibaseContext);
    }

    private Liquibase getLiquibase() throws SQLException, LiquibaseException {
        var connection = new JdbcConnection(getConnection(format("jdbc:mariadb://%s:%s/%s", host, port, schema), user, password));
        return new Liquibase(changeLog, new ClassLoaderResourceAccessor(), DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection));
    }

}
