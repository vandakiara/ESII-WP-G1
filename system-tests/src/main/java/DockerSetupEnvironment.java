import org.testcontainers.containers.DockerComposeContainer;

public class DockerSetupEnvironment extends DockerComposeContainer {

    public DockerSetupEnvironment() {
        super();
        super.withPull(false)
             .withLocalCompose(true);

    }


}
