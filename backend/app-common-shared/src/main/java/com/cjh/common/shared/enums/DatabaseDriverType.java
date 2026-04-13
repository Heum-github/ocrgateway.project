package com.cjh.common.shared.enums;

public enum DatabaseDriverType {

    H2("org.h2.Driver"),
    MYSQL("com.mysql.cj.jdbc.Driver"),
    MARIADB("org.mariadb.jdbc.Driver"),
    ORACLE("oracle.jdbc.OracleDriver"),
    POSTGRESQL("org.postgresql.Driver");

    private final String driverClassName;

    DatabaseDriverType(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public static DatabaseDriverType from(String driverClassName) {
        
        if (driverClassName == null) {
            throw new IllegalArgumentException("Driver class name cannot be null");
        }

        String upperDriverClassName = driverClassName.toLowerCase();

        for (DatabaseDriverType driver : DatabaseDriverType.values()) {
            if (driver.getDriverClassName().toLowerCase().equals(upperDriverClassName)) {
                return driver;
            }
        }

        throw new IllegalArgumentException("Unsupported driver: " + driverClassName);
    }
}