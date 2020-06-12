import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.util.List;

/**
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class DockerSetupEnvironment extends DockerComposeContainer {

    public DockerSetupEnvironment(final List<File> dockerComposeFiles) {
        super(dockerComposeFiles);
        super.withPull(false)
             .withLocalCompose(true);

    }


}
