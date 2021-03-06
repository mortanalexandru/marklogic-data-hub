package com.marklogic.hub.deploy.commands;

import com.marklogic.appdeployer.AppConfig;
import com.marklogic.appdeployer.command.CommandContext;
import com.marklogic.appdeployer.command.schemas.LoadSchemasCommand;

/**
 * DHF extension that provides support for writing schemas from any path to any database. ml-gradle may soon support
 * such a design in the future, but in the meantime DHF needs this so that schemas from different paths can be written
 * to the staging schemas database or the final schemas database.
 */
public class LoadHubSchemasCommand extends LoadSchemasCommand {

    private String schemasPath;
    private String schemasDatabase;

    /**
     * @param schemasPath     if not null, then this will be set on the AppConfig before the superclass's execute method is invoked
     * @param schemasDatabase if not null, then this will be set on the AppConfig before the superclass's execute method is invoked
     */
    public LoadHubSchemasCommand(String schemasPath, String schemasDatabase) {
        this.schemasPath = schemasPath;
        this.schemasDatabase = schemasDatabase;
    }

    @Override
    public void execute(CommandContext context) {
        AppConfig appConfig = context.getAppConfig();
        final String currentSchemasPath = appConfig.getSchemasPath();
        final String currentSchemasDatabase = appConfig.getSchemasDatabaseName();
        try {
            if (schemasPath != null) {
                appConfig.setSchemasPath(schemasPath);
            }
            if (schemasDatabase != null) {
                appConfig.setSchemasDatabaseName(schemasDatabase);
            }
            super.execute(context);
        } finally {
            appConfig.setSchemasPath(currentSchemasPath);
            appConfig.setSchemasDatabaseName(currentSchemasDatabase);
        }
    }
}
