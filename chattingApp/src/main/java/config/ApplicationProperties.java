package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    public static String getProperty(String propertyName) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = ApplicationProperties.class.getResourceAsStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return prop.getProperty(propertyName);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

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
//        return System.getenv("MONGO_HOST") == null ? "localhost" : System.getenv("MONGO_HOST");
        return "localhost";
    }

}
