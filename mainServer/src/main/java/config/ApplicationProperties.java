package config;

public class ApplicationProperties {
    /**
     * Get RabbitMq host from env variable if exists, else return a default value
     *
     * @return
     */
    public static String getRabbitMqHost() {
//        return System.getenv("RABBITMQ_HOST") == null ? "localhost" : System.getenv("RABBITMQ_HOST");
        return "localhost";
    }

    /**
     * Get Postgres host from env variable if exists, else return a default value
     *
     * @return
     */
    public static String getPostgresHost() {
        return System.getenv("POSTGRES_HOST") == null ? "localhost" : System.getenv("POSTGRES_HOST");
    }

    /**
     * Get Mongo host from env variable if exists, else return a default value
     *
     * @return
     */
    public static String getMongoHost() {
        return System.getenv("MONGO_HOST") == null ? "localhost" : System.getenv("MONGO_HOST");
    }

}
