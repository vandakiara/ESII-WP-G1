

public class TestEnvironment {

    /**
     * Instantiation of the Docker Compose Environment using Test Containers.
     */
    public static DockerSetupEnvironment environment = new DockerSetupEnvironment(TestProperties.getDockerComposeFiles());


    static {
        // This static block will be executed only once, the first time you make an object of that class or the first
        // time you access a static member of that class (even if you never make an object of that class).
        // By doing this we will be able to launch the docker environment and instantiate the objects we need.
        try {
            environment.start();


        } catch (final Throwable t) {
            throw new RuntimeException("There was an error while starting the environment!", t);
        }


    }
}
