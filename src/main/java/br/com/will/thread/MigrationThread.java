package br.com.will.thread;

import java.util.concurrent.Callable;

import br.com.will.migration.FlyWayMigration;
import jakarta.enterprise.inject.spi.CDI;

public class MigrationThread implements Callable<Boolean> {

    @Override
    public Boolean call() {

        FlyWayMigration flyWayMigration = CDI.current().select(FlyWayMigration.class).get();

        flyWayMigration.checkMigration();

        return Boolean.TRUE;
    }

}
